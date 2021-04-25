package com.glaikunt.framework.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Sort;
import com.glaikunt.framework.Display;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Screen;
import com.glaikunt.framework.application.cache.FontCache;
import com.glaikunt.framework.application.cache.TiledCache;
import com.glaikunt.framework.credits.CreditScreen;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.game.LevelComponent;
import com.glaikunt.framework.esc.component.player.GhostPlayerComponent;
import com.glaikunt.framework.esc.system.AnimationSystem;
import com.glaikunt.framework.esc.system.AttackSystem;
import com.glaikunt.framework.esc.system.PlayerActionsSystem;
import com.glaikunt.framework.esc.system.PlayerMovementSystem;
import com.glaikunt.framework.game.collision.CollisionActor;
import com.glaikunt.framework.game.enemy.DemonActor;
import com.glaikunt.framework.game.player.OldPlayerActor;
import com.glaikunt.framework.game.player.PlayerActor;
import com.glaikunt.framework.ui.OverlayActor;
import com.glaikunt.framework.splash.SplashScreen;

import java.util.Iterator;

public class GameScreen extends Screen {

    private TiledMapRenderer mapRenderer;

    private LevelComponent level;
    private PlayerActor player;

    private BitmapFont currentLevelFont;
    private GlyphLayout layout;

    private boolean foundPlayerType = false;

    public GameScreen(ApplicationResources applicationResources) {
        super(applicationResources);

        this.mapRenderer = new OrthogonalTiledMapRenderer(applicationResources.getCacheRetriever().getTiledMapCache(TiledCache.MAP));
    }

    @Override
    public void show() {

        this.level = getApplicationResources().getGlobalEntity().getComponent(LevelComponent.class);
        DemonActor demon = new DemonActor(getApplicationResources());
        getFront().addActor(demon);
        this.player = new PlayerActor(getApplicationResources(), demon);
        getFront().addActor(player);

        for (Iterator<GhostPlayerComponent> i = level.getGhostPlayers().iterator(); i.hasNext(); ) {
            GhostPlayerComponent ghostPlayerComponent = i.next();
            if (ghostPlayerComponent.getHealth().getDeltaHealth() <= 0) {
                i.remove();
            } else {
                getFront().addActor(new OldPlayerActor(getApplicationResources(), ghostPlayerComponent.getPos(), ghostPlayerComponent.getWeapon(), demon, ghostPlayerComponent.getHealth()));
            }

        }

        getUX().addActor(new OverlayActor(getApplicationResources()));

        TiledMap map = getApplicationResources().getCacheRetriever().getTiledMapCache(TiledCache.MAP);
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
        for (int y = collisionLayer.getHeight(); y >= 0; y--) {
            float yPos = (y * (int) collisionLayer.getTileHeight());
            for (int x = 0; x < collisionLayer.getWidth(); x++) {
                float xPos = (x * (int) collisionLayer.getTileWidth());
                TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
                if (cell == null) continue;

                Vector2 pos = new Vector2(xPos, yPos);
                getFront().addActor(new CollisionActor(getApplicationResources(), pos.x, pos.y, 16, 16));
            }
        }

        this.currentLevelFont = getApplicationResources().getCacheRetriever().getFontCache(FontCache.BIG_FONT);
        this.layout = new GlyphLayout(currentLevelFont, "Stage " + level.getStage(), Color.WHITE, 1, Align.left, false);

        getEngine().addSystem(new PlayerActionsSystem(getApplicationResources()));
        getEngine().addSystem(new PlayerMovementSystem(getApplicationResources()));
        getEngine().addSystem(new AttackSystem(getApplicationResources()));
        getEngine().addSystem(new AnimationSystem(getEngine()));

        getApplicationResources().getAudioManager().loopGameMusic();
    }

    @Override
    public void hide() {
        super.hide();

        this.level.setLevelComplete(false);
        this.level.setLevelStarted(false);

        if (!level.isGameOver()) {
            getApplicationResources().getAudioManager().stopGameMusic();
        }
    }

    @Override
    public void update(float delta) {

        getApplicationResources().getFrontStageMousePosition().set(getFront().getCamera().unproject(getApplicationResources().getFrontStageMousePosition().set(Gdx.input.getX(), Gdx.input.getY(), 0)));
        mapRenderer.setView((OrthographicCamera) getFront().getCamera());

        Sort.instance().sort(getFront().getActors(), new ActorComparator());

        getBackground().act();
        getFront().act();
        getUX().act();

        if (level.isLevelComplete()) {

            GhostPlayerComponent ghostPlayer = new GhostPlayerComponent();

            ghostPlayer.setPos(new PositionComponent(player.getX(), player.getY()));
            ghostPlayer.setWeapon(player.getWeapon().getWeaponType());
            ghostPlayer.setHealth(player.getHealth());

            level.getGhostPlayers().add(ghostPlayer);

            getDisplay().setScreen(new SplashScreen(getApplicationResources()));
        }

        foundPlayerType = false;
        for (Actor actor : getFront().getActors()) {

            if (actor instanceof OldPlayerActor || actor instanceof PlayerActor) {
                foundPlayerType = true;
            }
        }

        if (level.isGameOver()) {
            level.getGameOverTimer().tick(delta);

            if (level.getGameOverTimer().isTimerEventReady()) {
                getDisplay().setScreen(new CreditScreen(getApplicationResources()));
            }
        }

        if (!foundPlayerType) {
            level.setGameOver(true);
        }
    }

    @Override
    public void render2D(float delta) {

        Gdx.gl.glClearColor(0, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getBackground().draw();
        mapRenderer.render();
        getFront().draw();
        if (!level.isGameOver()) {
            getUX().getBatch().begin();
            currentLevelFont.draw(getUX().getBatch(), layout, (Display.WORLD_WIDTH / 2) - (layout.width / 2), (Display.WORLD_HEIGHT) - (layout.height * 3) - 10);
            getUX().getBatch().end();
        }
        getUX().draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    private class ActorComparator implements java.util.Comparator<Actor> {
        @Override
        public int compare(Actor actor1, Actor actor2) {
            if (actor1 instanceof Container) {
                return 1;
            }

            if (actor1.getY() < (actor2.getY())) {
                return 1;
            } else if (actor1.getY() > actor2.getY()) {
                return -1;
            }
            return 0;
        }
    }
}

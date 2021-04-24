package com.glaikunt.framework.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Sort;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Screen;
import com.glaikunt.framework.application.cache.TiledCache;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.game.LevelComponent;
import com.glaikunt.framework.esc.component.player.GhostPlayerComponent;
import com.glaikunt.framework.esc.system.AttackSystem;
import com.glaikunt.framework.esc.system.PlayerActionsSystem;
import com.glaikunt.framework.esc.system.PlayerMovementSystem;
import com.glaikunt.framework.game.enemy.DemonActor;
import com.glaikunt.framework.game.player.OldPlayerActor;
import com.glaikunt.framework.game.player.PlayerActor;
import com.glaikunt.framework.splash.SplashScreen;

public class GameScreen extends Screen {

    private TiledMapRenderer mapRenderer;

    private LevelComponent level;
    private PlayerActor player;

    public GameScreen(ApplicationResources applicationResources) {
        super(applicationResources);

        this.mapRenderer = new OrthogonalTiledMapRenderer(applicationResources.getCacheRetriever().getTiledMapCache(TiledCache.MAP));
    }

    @Override
    public void show() {


        this.level = getApplicationResources().getGlobalEntity().getComponent(LevelComponent.class);
        this.player = new PlayerActor(getApplicationResources());
        getFront().addActor(player);
        getFront().addActor(new DemonActor(getApplicationResources()));

        for (GhostPlayerComponent ghostPlayerComponent : level.getGhostPlayers()) {
            getFront().addActor(new OldPlayerActor(getApplicationResources(), ghostPlayerComponent.getPos(), ghostPlayerComponent.getWeapon()));
        }

        getEngine().addSystem(new PlayerActionsSystem(getApplicationResources()));
        getEngine().addSystem(new PlayerMovementSystem(getApplicationResources()));
        getEngine().addSystem(new AttackSystem(getApplicationResources()));
    }

    @Override
    public void hide() {
        super.hide();

        this.level.setLevelComplete(false);
        this.level.setLevelStarted(false);
    }

    @Override
    public void update(float delta) {

        mapRenderer.setView((OrthographicCamera) getFront().getCamera());

        Sort.instance().sort(getFront().getActors(), new ActorComparator());

        getBackground().act();
        getFront().act();
        getUX().act();

        if (level.isLevelComplete()) {

            GhostPlayerComponent ghostPlayer = new GhostPlayerComponent();

            ghostPlayer.setPos(new PositionComponent(player.getX(), player.getY()));
            ghostPlayer.setWeapon(player.getWeapon().getWeaponType());

            level.getGhostPlayers().add(ghostPlayer);

            getDisplay().setScreen(new SplashScreen(getApplicationResources()));
        }
    }

    @Override
    public void render2D(float delta) {

        Gdx.gl.glClearColor(0, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getBackground().draw();
        mapRenderer.render();
        getFront().draw();
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
            if (actor1.getY() < (actor2.getY())) {
                return 1;
            } else if (actor1.getY() > actor2.getY()) {
                return -1;
            }
            return 0;
        }
    }
}

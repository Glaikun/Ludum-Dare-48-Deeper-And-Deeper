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
import com.glaikunt.framework.esc.system.PlayerMovementSystem;
import com.glaikunt.framework.game.enemy.DemonActor;
import com.glaikunt.framework.game.player.PlayerActor;

public class GameScreen extends Screen {

    private TiledMapRenderer mapRenderer;

    public GameScreen(ApplicationResources applicationResources) {
        super(applicationResources);

        this.mapRenderer = new OrthogonalTiledMapRenderer(applicationResources.getCacheRetriever().getTiledMapCache(TiledCache.MAP));
    }

    @Override
    public void show() {

        getFront().addActor(new PlayerActor(getApplicationResources()));
        getFront().addActor(new DemonActor(getApplicationResources()));

        getApplicationResources().getEngine().addSystem(new PlayerMovementSystem(getEngine()));
    }

    @Override
    public void update(float delta) {

        mapRenderer.setView((OrthographicCamera) getFront().getCamera());

        Sort.instance().sort(getFront().getActors(), new ActorComparator());


        getBackground().act();
        getFront().act();
        getUX().act();

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

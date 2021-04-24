package com.glaikunt.framework.application;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.glaikunt.framework.Display;
import com.glaikunt.framework.application.cache.CacheRetriever;
import com.glaikunt.framework.debug.DebugLabels;

import java.util.Arrays;
import java.util.List;

import static com.glaikunt.framework.Display.WORLD_HEIGHT;
import static com.glaikunt.framework.Display.WORLD_WIDTH;

public abstract class Screen implements com.badlogic.gdx.Screen {

    private ApplicationResources applicationResources;
    private Stage front, background, ux;

    private final GLProfiler glProfiler;
    private final DebugLabels debugLabels;

    protected Screen(ApplicationResources applicationResources) {
        this.applicationResources = applicationResources;
        this.front = new Stage(new ScalingViewport(Scaling.stretch, WORLD_WIDTH, WORLD_HEIGHT));
        ((OrthographicCamera) this.front.getCamera()).setToOrtho(false);
        this.front.setDebugAll(true);
        this.background = new Stage(new ScalingViewport(Scaling.stretch, WORLD_WIDTH, WORLD_HEIGHT));
        ((OrthographicCamera) this.background.getCamera()).setToOrtho(false);
        this.ux = new Stage(new ScalingViewport(Scaling.stretch, WORLD_WIDTH, WORLD_HEIGHT));
        ((OrthographicCamera) this.ux.getCamera()).setToOrtho(false);
        this.ux.setDebugAll(false);

        if (Gdx.app.getLogLevel() != Logger.NONE) {
            this.debugLabels = new DebugLabels();
            this.glProfiler = new GLProfiler(Gdx.graphics);
            this.glProfiler.enable();

            getUX().addActor(debugLabels.getDebugPlayerLabel());
            getUX().addActor(debugLabels.getDebugGCLabel());
            getUX().addActor(debugLabels.getDebugProfilerLabel());
            getUX().addActor(debugLabels.getVersionLabel());
        } else {
            this.debugLabels = null;
            this.glProfiler = null;
        }
    }

    @Override
    public void resize(int width, int height) {
        getFront().getViewport().update(width, height, true);
        getBackground().getViewport().update(width, height, true);
        getUX().getViewport().update(width, height, true);
        getFront().getCamera().update();
        getBackground().getCamera().update();
        getUX().getCamera().update();

        Gdx.app.log(logDEBUG(), "Width: " + Gdx.graphics.getWidth() );
        Gdx.app.log(logDEBUG(), "Height: " + Gdx.graphics.getHeight() );
        Gdx.app.log(logDEBUG(), "worldWidth: " + WORLD_WIDTH );
        Gdx.app.log(logDEBUG(), "worldHeight: " + WORLD_HEIGHT );
    }

    @Override
    public void render(float delta) {
        if (!getDisplay().isPaused()) {
            update(delta);
        }
        render2D(delta);

        if (Gdx.app.getLogLevel() != Logger.NONE) {
            debugLabels.update(glProfiler);
            glProfiler.reset();
        }
    }

    public abstract void update(float delta);

    public abstract void render2D(float delta);

    protected String logDEBUG() {
        return "DEBUG";
    }

    @Override
    public void hide() {

        getEngine().removeAllEntities();
        for (Actor actor : getFront().getActors()) {
            actor.clear();
        }
        getFront().clear();
        for (Actor actor : getBackground().getActors()) {
            actor.clear();
        }
        getBackground().clear();
        for (Actor actor : getUX().getActors()) {
            actor.clear();
        }
        getUX().clear();
    }

    @Override
    public void dispose() {

        getFront().dispose();
        getBackground().dispose();
        getUX().dispose();
        getEngine().removeAllEntities();
    }

    protected ApplicationResources getApplicationResources() {
        return applicationResources;
    }

    protected Engine getEngine() {
        return getApplicationResources().getEngine();
    }

    protected CacheRetriever getCacheRetriever() {
        return getApplicationResources().getCacheRetriever();
    }

    protected Display getDisplay() {
        return applicationResources.getDisplay();
    }

    public Stage getFront() {
        return front;
    }

    public Stage getBackground() {
        return background;
    }

    public Stage getUX() {
        return ux;
    }

    public List<OrthographicCamera> getCameras() {
        return Arrays.asList((OrthographicCamera) front.getCamera(), (OrthographicCamera) background.getCamera());
    }

    public List<Stage> getStages() {
        return Arrays.asList(front, background, ux);
    }
}

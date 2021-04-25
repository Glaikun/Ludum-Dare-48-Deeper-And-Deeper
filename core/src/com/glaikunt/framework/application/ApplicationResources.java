package com.glaikunt.framework.application;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.glaikunt.framework.Display;
import com.glaikunt.framework.application.cache.CacheRetriever;

public class ApplicationResources {

    private final EngineLogger engine = new EngineLogger();
    private final Entity immutableGameEntity = new Entity();
    private final Entity globalEntity = new Entity();
    private final CacheRetriever cacheRetriever = new CacheRetriever();
    private final Vector3 frontStageMousePosition = new Vector3();
    private final Vector3 uxStageMousePosition = new Vector3();
    private final Preferences preferences;
    private final AudioManager audioManager;
    private final Display display;

    public ApplicationResources(Display display) {
        this.display = display;
        this.audioManager = new AudioManager();
        this.preferences = Gdx.app.getPreferences("glaikuntDatabase");
    }

    public EngineLogger getEngine() {
        return engine;
    }

    public Entity getGlobalEntity() {
        return globalEntity;
    }

    public Entity getImmutableGameEntity() {
        return immutableGameEntity;
    }

    public CacheRetriever getCacheRetriever() {
        return cacheRetriever;
    }

    public Vector3 getFrontStageMousePosition() {
        return frontStageMousePosition;
    }

    public Vector3 getUxStageMousePosition() {
        return uxStageMousePosition;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public Display getDisplay() {
        return display;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void dispose() {
        cacheRetriever.dispose();
    }

    public Texture getTexture(String cacheName) {
        return getCacheRetriever().geTextureCache(cacheName);
    }
}

package com.glaikunt.framework.application.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class CacheRetriever {

    private boolean loaded = false;

    private AssetManager assetManager;
    private TextureCache textureCache;
    private SoundCache soundCache;
    private TiledCache tiledCache;
    private FontCache fontCache;

    public CacheRetriever() {

        this.assetManager = new AssetManager();
        this.textureCache = new TextureCache();
        this.soundCache = new SoundCache();
        this.tiledCache = new TiledCache();
        this.fontCache = new FontCache();
    }

    public void loadCache() {
        if (loaded) throw new IllegalArgumentException("Cache has already been retrieved");

        getTextureCache().loadCache(getAssetManager());
        getSoundCache().loadCache(getAssetManager());
        getTiledCache().loadCache(getAssetManager());
        getFontCache().loadCache(getAssetManager());
        loaded = true;
    }

    public boolean isCacheLoaded() {

        return (getTextureCache().isLoaded(getAssetManager()) || getTextureCache().getTextureMap().isEmpty()) &&
                (getTiledCache().isLoaded(getAssetManager()) || getTiledCache().getTiledMap().isEmpty()) &&
                (getSoundCache().isLoaded(getAssetManager()) || getSoundCache().getSounds().isEmpty()) &&
                (getFontCache().isLoaded(getAssetManager()) || getFontCache().getFonts().isEmpty());
    }

    public boolean update() {
        return getAssetManager().update();
    }

    public float progress() {
        return getAssetManager().getProgress();
    }

    public Texture geTextureCache(String key) {
        return getTextureCache().getTextureCache(key);
    }

    public TiledMap getTiledMapCache(String key) {
        return getTiledCache().getTiledMapCache(key);
    }

    public BitmapFont getFontCache(String key) {
        return getFontCache().getFontCache(key);
    }

    private TextureCache getTextureCache() {
        return textureCache;
    }

    public SoundCache getSoundCache() {
        return soundCache;
    }

    public TiledCache getTiledCache() {
        return tiledCache;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public FontCache getFontCache() {
        return fontCache;
    }

    public void dispose() {
        assetManager.dispose();
    }
}

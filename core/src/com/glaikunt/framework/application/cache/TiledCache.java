package com.glaikunt.framework.application.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.HashMap;
import java.util.Map;

public class TiledCache implements Cache {

    public static final String MAP = "tile/map.tmx";

    private Map<String, TiledMap> tiledMap = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMinFilter = Texture.TextureFilter.Linear;
        params.textureMagFilter = Texture.TextureFilter.Nearest;

        TmxMapLoader loader = new TmxMapLoader(new InternalFileHandleResolver());
        assetManager.setLoader(TiledMap.class, loader);

        assetManager.load(MAP, TiledMap.class, params);
        getTiledMap().put(MAP, null);
    }

    @Override
    public boolean isLoaded(AssetManager assetManager) {
        if (tiledMap.isEmpty()) return false;

        for (String key : tiledMap.keySet()) {
            if (!assetManager.isLoaded(key)) {
                return false;
            }
        }

        if (!isLoaded()) {
            for (String key : tiledMap.keySet()) {
                getTiledMap().put(key, (TiledMap) assetManager.get(key));
            }
            setLoaded(true);
        }

        return true;
    }

    public TiledMap getTiledMapCache(String key) {
        return getTiledMap().get(key);
    }

    public Map<String, TiledMap> getTiledMap() {
        return tiledMap;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }
}

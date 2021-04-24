package com.glaikunt.framework.application.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureCache implements Cache {

    private Map<String, Texture> textureMap = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

    }

    @Override
    public boolean isLoaded(AssetManager assetManager) {
        if (textureMap.isEmpty()) return false;

        for (String key : textureMap.keySet()) {
            if (!assetManager.isLoaded(key)) {
                return false;
            }
        }

        if (!isLoaded()) {
            for (String key : textureMap.keySet()) {
                getTextureMap().put(key, (Texture) assetManager.get(key));
            }
            setLoaded(true);
        }

        return true;
    }

    public void add(AssetManager assetManager, String... images) {
        for (String image : images) {
            assetManager.load(image, Texture.class);
            getTextureMap().put(image, null);
        }
    }

    public Texture getTextureCache(String key) {
        return getTextureMap().get(key);
    }

    public Map<String, Texture> getTextureMap() {
        return textureMap;
    }

    private boolean isLoaded() {
        return loaded;
    }

    private void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}

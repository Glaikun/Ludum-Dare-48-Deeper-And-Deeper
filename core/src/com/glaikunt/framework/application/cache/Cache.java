package com.glaikunt.framework.application.cache;

import com.badlogic.gdx.assets.AssetManager;

public interface Cache {

    void loadCache(AssetManager assetManager);
    boolean isLoaded(AssetManager assetManager);
}

package com.glaikunt.framework.application.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureCache implements Cache {

    public static final String PLAYER_IDLE = "sprites/player_idle.png";
    public static final String PLAYER_SITTING = "sprites/player_sitting_down.png";
    public static final String PLAYER_MOVEMENT = "sprites/player_movement.png";

    public static final String HEART_ICON = "sprites/heart_icon.png";

    public static final String DEMON = "sprites/enemy.png";

    public static final String SPOT = "sprites/spot.png";
    public static final String NEXT_STAGE = "sprites/next_stage.png";

    public static final String ATTACK_ICON = "sprites/sword.png";
    public static final String MAGIC_ICON = "sprites/magic.png";
    public static final String SUPPORT_ICON = "sprites/support.png";

    //  ################### OVERLAY FOLDER ###################

    public static final String OVERLAY_1 = "sprites/video/scene1.png";
    public static final String OVERLAY_2 = "sprites/video/scene2.png";
    public static final String OVERLAY_3 = "sprites/video/scene3.png";
    public static final String OVERLAY_4 = "sprites/video/scene4.png";
    public static final String OVERLAY_5 = "sprites/video/scene5.png";
    public static final String OVERLAY_6 = "sprites/video/scene6.png";
    public static final String OVERLAY_7 = "sprites/video/scene7.png";
    public static final String OVERLAY_8 = "sprites/video/scene8.png";
    public static final String OVERLAY_9 = "sprites/video/scene9.png";
    public static final String OVERLAY_10 = "sprites/video/scene10.png";

    //  ################### OVERLAY FOLDER ###################

    private Map<String, Texture> textureMap = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

        add(assetManager, PLAYER_IDLE, PLAYER_SITTING, PLAYER_MOVEMENT);
        add(assetManager, DEMON);
        add(assetManager, SPOT);
        add(assetManager, HEART_ICON);
        add(assetManager, NEXT_STAGE);
        add(assetManager, ATTACK_ICON, MAGIC_ICON, SUPPORT_ICON);
        add(assetManager, OVERLAY_1, OVERLAY_2, OVERLAY_3, OVERLAY_4, OVERLAY_5, OVERLAY_6, OVERLAY_7, OVERLAY_8, OVERLAY_9, OVERLAY_10);
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

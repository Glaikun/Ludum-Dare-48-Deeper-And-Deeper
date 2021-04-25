package com.glaikunt.framework.application.cache;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;
import java.util.Map;

public class FontCache implements Cache {

    public static final String SMALL_FONT = "font/button_font.fnt";
    public static final String BIG_FONT = "font/big_font.fnt";
    public static final String BATTLE_FONT = "font/battle_font.fnt";
    public static final String GAME_FONT = "font/kenny_thick.fnt";

    private Map<String, BitmapFont> fonts = new HashMap<>();
    private boolean loaded = false;

    @Override
    public void loadCache(AssetManager assetManager) {

        add(SMALL_FONT, assetManager);
        add(BIG_FONT, assetManager);
        add(GAME_FONT, assetManager);
        add(BATTLE_FONT, assetManager);
    }

    private void add(String filePath, AssetManager assetManager) {
        assetManager.load(filePath, BitmapFont.class);
        getFonts().put(filePath, null);
    }

    @Override
    public boolean isLoaded(AssetManager assetManager) {
        if (fonts.isEmpty()) return false;

        for (String key : fonts.keySet()) {
            if (!assetManager.isLoaded(key)) {
                return false;
            }
        }

        if (!isLoaded()) {
            for (String key : fonts.keySet()) {
                getFonts().put(key, (BitmapFont) assetManager.get(key));
            }
            setLoaded(true);
        }

        return true;
    }

    public BitmapFont getFontCache(String key) {
        return getFonts().get(key);
    }

    public Map<String, BitmapFont> getFonts() {
        return fonts;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }
}

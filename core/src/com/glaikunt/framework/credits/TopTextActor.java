package com.glaikunt.framework.credits;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.glaikunt.framework.Display;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.cache.FontCache;

public class TopTextActor extends Actor {
    private BitmapFont baseFont;
    private GlyphLayout layout;
    private Vector2 pos;

    public TopTextActor(ApplicationResources applicationResources, String txt) {

        this.baseFont = applicationResources.getCacheRetriever().getFontCache(FontCache.GAME_FONT);
        this.layout = new GlyphLayout();
        this.layout.setText(baseFont, txt, new Color(1f, 1f, 1f, 1), 0, Align.left, false);
        this.pos = new Vector2(((Display.WORLD_WIDTH / 2f) - (layout.width / 2)), ((Display.WORLD_HEIGHT - 150)  + (layout.height/2)));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        baseFont.draw(batch, layout, pos.x, pos.y);
    }

    public GlyphLayout getLayout() {
        return layout;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }
}

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

public class CreditTextActor extends Actor {

    private BitmapFont baseFont;
    private GlyphLayout layout;
    private float alpha = 0;
    private Vector2 pos;
    private boolean toggle;
    private String txt;

    public CreditTextActor(ApplicationResources applicationResources, String txt) {

        this.baseFont = applicationResources.getCacheRetriever().getFontCache(FontCache.GAME_FONT);
        this.layout = new GlyphLayout();
        this.layout.setText(baseFont, txt, new Color(1f, 1f, 1f, alpha), 0, Align.left, false);
        this.txt = txt;
        this.pos = new Vector2(((Display.WORLD_WIDTH / 2f) - (layout.width / 2)), layout.height);

    }

    @Override
    public void act(float delta) {

        pos.y += 25 * delta;

        if (!toggle && alpha < 1) {
            alpha += .2 * delta;
        } else if (!toggle) {
            toggle = true;
        }

        if (toggle && alpha > 0) {
            alpha -= .2 * delta;
        } else if (toggle) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


        layout.setText(baseFont, txt, new Color(.2f, .1f, .4f, alpha), 0, Align.left, false);
        baseFont.draw(batch, layout, pos.x, pos.y);
    }
}

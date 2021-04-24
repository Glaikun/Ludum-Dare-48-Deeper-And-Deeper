package com.glaikunt.framework.esc.component.text;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public interface TextComponent extends Component {

    boolean isFinished();

    String getDeltaText();

    GlyphLayout getLayout();

    BitmapFont getFont();
}

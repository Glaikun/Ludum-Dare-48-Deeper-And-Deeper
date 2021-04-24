package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class SizeComponent extends Vector2 implements Component {

    public SizeComponent () {
        super();
    }

    /** Constructs a vector with the given components
     * @param x The x-component
     * @param y The y-component */
    public SizeComponent (float x, float y) {
        super(x, y);
    }
}

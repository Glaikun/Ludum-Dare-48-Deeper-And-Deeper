package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.application.Rectangle;

public class CollisionComponent implements Component {

    private Rectangle bound;

    public Rectangle getBound() {
        return bound;
    }

    public void setBound(Rectangle bound) {
        this.bound = bound;
    }
}

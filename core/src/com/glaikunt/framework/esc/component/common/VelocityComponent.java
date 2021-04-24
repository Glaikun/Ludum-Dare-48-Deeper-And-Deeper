package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent extends Vector2 implements Component {

    private float speed;
    private boolean changeDirection;

    public VelocityComponent () {
        super();
    }

    /** Constructs a vector with the given components
     * @param x The x-component
     * @param y The y-component */
    public VelocityComponent (float x, float y) {
        super(x, y);
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isChangeDirection() {
        return changeDirection;
    }

    public void setChangeDirection(boolean changeDirection) {
        this.changeDirection = changeDirection;
    }
}

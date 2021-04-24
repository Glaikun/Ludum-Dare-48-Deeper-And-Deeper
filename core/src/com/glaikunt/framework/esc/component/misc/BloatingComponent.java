package com.glaikunt.framework.esc.component.misc;

import com.badlogic.ashley.core.Component;

public class BloatingComponent implements Component {

    private boolean toggle;
    private float bloating, maxBloating, speed = 1f;

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public float getBloating() {
        return bloating;
    }

    public void setBloating(float bloating) {
        this.bloating = bloating;
    }

    public float getMaxBloating() {
        return maxBloating;
    }

    public void setMaxBloating(float maxBloating) {
        this.maxBloating = maxBloating;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}

package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {

    private float health, deltaHealth, lerpWidth;

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
        this.deltaHealth = health;
        this.lerpWidth = health;
    }

    public float getDeltaHealth() {
        return deltaHealth;
    }

    public void setDeltaHealth(float deltaHealth) {
        this.deltaHealth = deltaHealth;
    }

    public void setLerpWidth(float lerpWidth) {
        this.lerpWidth = lerpWidth;
    }

    public float getLerpWidth() {
        return lerpWidth;
    }
}

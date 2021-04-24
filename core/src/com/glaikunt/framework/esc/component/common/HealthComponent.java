package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {

    private float health;

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }
}

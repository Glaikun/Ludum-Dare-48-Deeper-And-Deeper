package com.glaikunt.framework.esc.component.common;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.application.TickTimer;

public class RotationComponent implements Component {

    private TickTimer rotationTimer;
    private boolean toggle;
    private float baseRotation, rotation, rotationSpeed;

    public void setRotationTimer(TickTimer rotationTimer) {
        this.rotationTimer = rotationTimer;
    }

    public TickTimer getRotationTimer() {
        return rotationTimer;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public float getBaseRotation() {
        return baseRotation;
    }

    public void setBaseRotation(float baseRotation) {
        this.baseRotation = baseRotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }
}

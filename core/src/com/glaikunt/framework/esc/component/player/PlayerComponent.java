package com.glaikunt.framework.esc.component.player;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {

    private boolean movingLeft = false;
    private boolean moving = false;
    private boolean sitting = true;

    public boolean isSitting() {
        return sitting;
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}

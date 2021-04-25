package com.glaikunt.framework.esc.component.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.application.Rectangle;

import java.util.LinkedList;
import java.util.List;

public class ValidateAttackComponent implements Component {

    private boolean inRange = false;
    private boolean availableSpaceToAttack = false;

    private List<Rectangle> availableSpace = new LinkedList<>();

    private Vector2 currentPos, targetPos;

    public boolean isInRange() {
        return inRange;
    }

    public void setInRange(boolean inRange) {
        this.inRange = inRange;
    }

    public boolean isAvailableSpaceToAttack() {
        return availableSpaceToAttack;
    }

    public void setAvailableSpaceToAttack(boolean availableSpaceToAttack) {
        this.availableSpaceToAttack = availableSpaceToAttack;
    }

    public List<Rectangle> getAvailableSpace() {
        return availableSpace;
    }

    public void setAvailableSpace(List<Rectangle> availableSpace) {
        this.availableSpace = availableSpace;
    }

    public Vector2 getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(Vector2 currentPos) {
        this.currentPos = currentPos;
    }

    public Vector2 getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(Vector2 targetPos) {
        this.targetPos = targetPos;
    }
}

package com.glaikunt.framework.esc.component.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.application.Rectangle;

import java.util.LinkedList;
import java.util.List;

public class ValidateAttackComponent implements Component {

    private boolean inRange = false;

    private List<Rectangle> availableSpace = new LinkedList<>();

    private Vector2 currentPos = new Vector2(), targetPos = new Vector2();

    private Rectangle supportArea;

    public boolean isInRange() {
        return inRange;
    }

    public void setInRange(boolean inRange) {
        this.inRange = inRange;
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

    public Rectangle getSupportArea() {
        return supportArea;
    }

    public void setSupportArea(Rectangle supportArea) {
        this.supportArea = supportArea;
    }
}

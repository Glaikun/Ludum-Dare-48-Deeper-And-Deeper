package com.glaikunt.framework.esc.component.path;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class NodeComponent implements Component, Comparable<NodeComponent> {

    private Vector2 pos;
    private float width, height;
    private int g_score = Integer.MAX_VALUE, f_score = Integer.MAX_VALUE;
    private boolean goalTarget = false, disabled = false, deltaGoal = false;
    private int token = 0;
    private boolean directionalEnd = false;

    public NodeComponent(float x, float y, float width, float height) {
        this.pos = new Vector2();
        this.pos.x = x;
        this.pos.y = y;
        this.width = width;
        this.height = height;
    }

    private void addToken() {
        token++;
    }

    private void removeToken() {
        token--;
    }

    public int getToken() {
        return token;
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public Vector2 getPos() {
        return pos;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getG_score() {
        return g_score;
    }

    public void setG_score(int g_score) {
        this.g_score = g_score;
    }

    public Integer getF_score() {
        return f_score;
    }

    public void setF_score(int f_score) {
        this.f_score = f_score;
    }

    public void setGoalTarget(boolean goalTarget) {
        if (goalTarget) {
            addToken();
        } else {
            removeToken();
        }

        if (getToken() > 0 && !goalTarget) {
            //DO NOT SET TO FALSE WHEN COUNTER GREATER THAN ZERO
        } else {
            this.goalTarget = goalTarget;
        }

        if (getToken() < 0) {
            throw new IllegalArgumentException("This should never be lower than one, something went wrong.");
        }
    }

    public boolean isGoalTarget() {
        return goalTarget;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public int compareTo(NodeComponent o) {
        return getF_score().compareTo(o.getF_score());
    }

    public void setDirectionalEnd(boolean directionalEnd) {
        this.directionalEnd = directionalEnd;
    }

    public boolean isDirectionalEnd() {
        return directionalEnd;
    }
}

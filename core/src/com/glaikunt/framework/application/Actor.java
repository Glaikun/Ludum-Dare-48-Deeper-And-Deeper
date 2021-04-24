package com.glaikunt.framework.application;

import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;

public class Actor extends com.badlogic.gdx.scenes.scene2d.Actor {

    private final ApplicationResources applicationResources;

    protected PositionComponent pos;
    protected SizeComponent size;

    public Actor(ApplicationResources applicationResources) {
        this.applicationResources = applicationResources;
    }

    public ApplicationResources getApplicationResources() {
        return applicationResources;
    }

    @Override
    public float getWidth() {
        return size.x;
    }

    @Override
    public float getHeight() {
        return size.y;
    }

    @Override
    public float getX() {
        return pos.x;
    }

    @Override
    public float getY() {
        return pos.y;
    }
}

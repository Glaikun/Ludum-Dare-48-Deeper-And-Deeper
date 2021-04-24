package com.glaikunt.framework.esc.component.animation;

import com.badlogic.ashley.core.Component;

import java.util.ArrayList;
import java.util.List;

public class AnimationsComponent implements Component {

    private List<AnimationComponent> animations = new ArrayList<>();

    public void add(AnimationComponent animation) {
        this.animations.add(animation);
    }

    public List<AnimationComponent> getAnimations() {
        return animations;
    }
}

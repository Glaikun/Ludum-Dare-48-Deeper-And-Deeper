package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.animation.AnimationsComponent;

public class AnimationSystem extends EntitySystem {

    private ImmutableArray<Entity> animationEntities;
    private ImmutableArray<Entity> animationsEntities;

    private ComponentMapper<AnimationComponent> ac = ComponentMapper.getFor(AnimationComponent.class);
    private ComponentMapper<AnimationsComponent> asc = ComponentMapper.getFor(AnimationsComponent.class);

    public AnimationSystem(Engine engine) {
        animationEntities = engine.getEntitiesFor(Family.all(AnimationComponent.class).get());
        animationsEntities = engine.getEntitiesFor(Family.all(AnimationsComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < animationEntities.size(); ++i) {

            Entity entity = animationEntities.get(i);
            AnimationComponent a = ac.get(entity);
            if (!a.isPlaying()) continue;
            a.setStateTime(a.getStateTime() + deltaTime);
            a.setCurrentFrame(a.getCurrentAnimation().getKeyFrame(a.getStateTime()));
        }

        for (int i = 0; i < animationsEntities.size(); ++i) {

            Entity entity = animationsEntities.get(i);
            AnimationsComponent as = asc.get(entity);
            for (AnimationComponent a : as.getAnimations()) {
                if (!a.isPlaying()) continue;
                a.setStateTime(a.getStateTime() + deltaTime);
                a.setCurrentFrame(a.getCurrentAnimation().getKeyFrame(a.getStateTime()));
            }
        }
    }
}

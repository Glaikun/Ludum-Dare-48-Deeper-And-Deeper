package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.misc.FadeComponent;

public class FadeSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<FadeComponent> fcm = ComponentMapper.getFor(FadeComponent.class);

    public FadeSystem(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(FadeComponent.class).get());
    }

    @Override
    public void update(float delta) {
        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            FadeComponent fc = fcm.get(entity);

            for (FadeComponent.Fade a : fc.getFades()) {

                if (a.isFadeIn() && !a.isFadeOut()) {

                    if (a.getFade() < a.getMaxFade()) {
                        a.setFade(a.getFade() + (.5f * delta));
                        if (a.getFade() >= a.getMaxFade()) {
                            a.setFade(a.getMaxFade());
                        }
                    } else if (a.getFade() != a.getMaxFade()) {
                        a.setFade(a.getMaxFade());
                    }

                } else if (!a.isFadeIn() && a.isFadeOut()) {

                    if (a.getFade() > 0) {
                        a.setFade(a.getFade() - (.5f * delta));
                        if (a.getFade() < 0) {
                            a.setFade(0);
                        }
                    } else if (a.getFade() != 0) {
                        a.setFade(0);
                    }
                }
            }

        }
    }
}

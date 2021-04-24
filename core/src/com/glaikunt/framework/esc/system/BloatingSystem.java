package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.misc.BloatingComponent;

public class BloatingSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<BloatingComponent> bcm = ComponentMapper.getFor(BloatingComponent.class);

    public BloatingSystem(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(BloatingComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {

        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            BloatingComponent bc = bcm.get(entity);

            if (!bc.isToggle()) {

                if (bc.getBloating() < bc.getMaxBloating()) {
                    bc.setBloating(bc.getBloating() + (bc.getSpeed() * deltaTime));
                } else if (bc.getBloating() >= bc.getMaxBloating()) {
                    bc.setBloating(bc.getMaxBloating());
                    bc.setToggle(true);
                }
            } else {

                if (bc.getBloating() > 0) {
                    bc.setBloating(bc.getBloating() - (bc.getSpeed() * deltaTime));
                } else if (bc.getBloating() <= 0) {
                    bc.setBloating(0);
                    bc.setToggle(false);
                }
            }
        }
    }
}

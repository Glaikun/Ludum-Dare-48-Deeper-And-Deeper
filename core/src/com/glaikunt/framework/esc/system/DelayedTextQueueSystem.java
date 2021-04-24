package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.text.DelayedTextComponent;
import com.glaikunt.framework.esc.component.text.TextComponent;
import com.glaikunt.framework.esc.component.text.TextQueueComponent;

public class DelayedTextQueueSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<TextQueueComponent> tqcm = ComponentMapper.getFor(TextQueueComponent.class);
    private ComponentMapper<DelayedTextComponent> dtcm = ComponentMapper.getFor(DelayedTextComponent.class);

    public DelayedTextQueueSystem(Engine engine) {
        this.entities = engine.getEntitiesFor(Family.all(TextQueueComponent.class, DelayedTextComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {

        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            TextQueueComponent queue = tqcm.get(entity);
            DelayedTextComponent text = dtcm.get(entity);

            if (queue.getQueue().isEmpty()) continue;

            if (text == null) {
                TextComponent poll = queue.getQueue().poll();
                entity.add(poll);
                continue;
            }

            if (text.isFinished()) {

                queue.getSwapDelay().tick(deltaTime);
                if (queue.getSwapDelay().isTimerEventReady()) {
                    entity.add(queue.getQueue().poll());
                }
            }
        }
    }
}

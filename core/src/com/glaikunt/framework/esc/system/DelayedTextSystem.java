package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.glaikunt.framework.esc.component.text.DelayedTextComponent;

public class DelayedTextSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<DelayedTextComponent> tcm = ComponentMapper.getFor(DelayedTextComponent.class);

    public DelayedTextSystem(Engine engine) {
        this.entities = engine.getEntitiesFor(Family.all(DelayedTextComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {

        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            DelayedTextComponent text = tcm.get(entity);

            text.getDelay().tick(deltaTime);

            if (!text.getText().equals(text.getDeltaText()) && text.getDelay().isTimerEventReady()) {

                text.setDeltaText(text.getDeltaText() + text.getText().charAt(text.getDeltaText().length()));
                text.getLayout().setText(text.getFont(), text.getDeltaText(), text.getColour(), text.getTargetWidth(), text.getAlign(), text.isWrap());
            }

            if (!text.isFinished() && text.getText().equals(text.getDeltaText())) {
                text.setFinished(true);
            }
        }
    }
}

package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.player.PlayerComponent;

public class PlayerMovementSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<PlayerComponent> playerCM = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<PositionComponent> posCM = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<SizeComponent> sizeCM = ComponentMapper.getFor(SizeComponent.class);
    private ComponentMapper<VelocityComponent> VelCM = ComponentMapper.getFor(VelocityComponent.class);

    public PlayerMovementSystem(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, PositionComponent.class, SizeComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void update(float delta) {

        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            PlayerComponent player = playerCM.get(entity);
            PositionComponent pos = posCM.get(entity);
            VelocityComponent vel = VelCM.get(entity);
            SizeComponent size = sizeCM.get(entity);

            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

                pos.x -= vel.x * delta;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

                pos.x += vel.x * delta;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {

                pos.y += vel.y * delta;
            } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

                pos.y -= vel.y * delta;
            }
        }
    }
}

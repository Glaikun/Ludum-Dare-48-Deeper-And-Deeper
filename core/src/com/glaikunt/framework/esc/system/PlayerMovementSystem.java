package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Rectangle;
import com.glaikunt.framework.esc.component.common.CollisionComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.game.LevelComponent;
import com.glaikunt.framework.esc.component.player.PlayerComponent;

public class PlayerMovementSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> collisionEntities;

    private LevelComponent level;

    private Rectangle playerRect, collisionRect;

    private ComponentMapper<PlayerComponent> playerCM = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<PositionComponent> posCM = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<SizeComponent> sizeCM = ComponentMapper.getFor(SizeComponent.class);
    private ComponentMapper<VelocityComponent> velCM = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<CollisionComponent> colCM = ComponentMapper.getFor(CollisionComponent.class);

    public PlayerMovementSystem(ApplicationResources applicationResources) {
        this.entities = applicationResources.getEngine().getEntitiesFor(Family.all(
                PlayerComponent.class,
                PositionComponent.class,
                SizeComponent.class,
                VelocityComponent.class).get());

        this.collisionEntities = applicationResources.getEngine().getEntitiesFor(Family
                .all(PositionComponent.class, SizeComponent.class, CollisionComponent.class)
                .get()
        );

        this.level = applicationResources.getGlobalEntity().getComponent(LevelComponent.class);
        this.playerRect = new Rectangle();
        this.collisionRect = new Rectangle();
    }

    @Override
    public void update(float delta) {

        if (level.isLevelStarted() || level.isLevelComplete()) {
            return;
        }

        for (int pi = 0; pi < entities.size(); ++pi) {

            Entity playerEntity = entities.get(pi);
            PlayerComponent player = playerCM.get(playerEntity);
            PositionComponent pos = posCM.get(playerEntity);
            VelocityComponent vel = velCM.get(playerEntity);
            SizeComponent size = sizeCM.get(playerEntity);

            player.setMoving(false);

            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

                setPlayerRect(delta, pos.x - (vel.x * delta), pos.y, vel, size);
                boolean collided = false;
                for (int ci = 0; ci < collisionEntities.size(); ++ci) {

                    Entity collisionEntity = collisionEntities.get(ci);
                    PositionComponent colPos = posCM.get(collisionEntity);
                    SizeComponent colSize = sizeCM.get(collisionEntity);
                    CollisionComponent collision = colCM.get(collisionEntity);

                    if (doesContainPlayer(collision.getBound())) {
                        collided = true;
                        break;
                    }
                }

                if (!collided) {
                    pos.x -= vel.x * delta;

                    if (player.isMovingLeft()) {
                        player.setMovingLeft(false);
                    }
                    player.setMoving(true);
                }

            } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                setPlayerRect(delta, pos.x + (vel.x * delta), pos.y, vel, size);

                boolean collided = false;
                for (int ci = 0; ci < collisionEntities.size(); ++ci) {

                    Entity collisionEntity = collisionEntities.get(ci);
                    PositionComponent colPos = posCM.get(collisionEntity);
                    SizeComponent colSize = sizeCM.get(collisionEntity);
                    CollisionComponent collision = colCM.get(collisionEntity);

                    if (doesContainPlayer(collision.getBound())) {
                        collided = true;
                        break;
                    }
                }

                if (!collided) {
                    pos.x += vel.x * delta;

                    if (!player.isMovingLeft()) {
                        player.setMovingLeft(true);
                    }
                    player.setMoving(true);
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                setPlayerRect(delta, pos.x, pos.y + (vel.y * delta), vel, size);

                boolean collided = false;
                for (int ci = 0; ci < collisionEntities.size(); ++ci) {

                    Entity collisionEntity = collisionEntities.get(ci);
                    PositionComponent colPos = posCM.get(collisionEntity);
                    SizeComponent colSize = sizeCM.get(collisionEntity);
                    CollisionComponent collision = colCM.get(collisionEntity);

                    if (doesContainPlayer(collision.getBound())) {
                        collided = true;
                        break;
                    }
                }

                if (!collided) {
                    pos.y += vel.y * delta;

                    player.setMoving(true);
                }

            } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                setPlayerRect(delta, pos.x, pos.y - (vel.y * delta), vel, size);
                boolean collided = false;
                for (int ci = 0; ci < collisionEntities.size(); ++ci) {

                    Entity collisionEntity = collisionEntities.get(ci);
                    PositionComponent colPos = posCM.get(collisionEntity);
                    SizeComponent colSize = sizeCM.get(collisionEntity);
                    CollisionComponent collision = colCM.get(collisionEntity);

                    if (doesContainPlayer(collision.getBound())) {
                        collided = true;
                        break;
                    }
                }

                if (!collided) {
                    pos.y -= vel.y * delta;

                    player.setMoving(true);
                }
            }
        }
    }

    private boolean doesContainPlayer(Rectangle rectangle) {
        return rectangle.intersects(playerRect);
    }

    private void setPlayerRect(float delta, float deltaX, float deltaY, VelocityComponent vel, SizeComponent size) {
        playerRect.set(deltaX + ((size.x/2)/2), deltaY, size.x/2, size.y/2);
    }
}

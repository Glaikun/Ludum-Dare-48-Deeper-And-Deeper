package com.glaikunt.framework.game.collision;


import com.badlogic.ashley.core.Entity;
import com.glaikunt.framework.application.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Rectangle;
import com.glaikunt.framework.esc.component.common.CollisionComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;

public class CollisionActor extends Actor {

    public CollisionActor(ApplicationResources applicationResources, float xPos, float yPos, float width, float height) {
        super(applicationResources);

        this.pos = new PositionComponent(xPos, yPos);
        this.size = new SizeComponent(width, height);

        CollisionComponent collision = new CollisionComponent();
        Rectangle rectangle = new Rectangle();
        rectangle.set(pos.x, pos.y, size.x, size.y);
        collision.setBound(rectangle);

        Entity entity = new Entity();
        entity.add(pos);
        entity.add(size);
        entity.add(collision);

        applicationResources.getEngine().addEntity(entity);
    }
}

package com.glaikunt.framework.game.attack;

import com.badlogic.gdx.graphics.Color;
import com.glaikunt.framework.application.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.TickTimer;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.pixels.ExplodePixelActor;

public class FrostActor extends Actor {

    private PositionComponent targetPos;

    private TickTimer spawnTimer;

    public FrostActor(ApplicationResources applicationResources, float xPos, float yPos, PositionComponent targetPos) {
        super(applicationResources);

        this.spawnTimer = new TickTimer(.005f);

        this.pos = new PositionComponent(xPos, yPos);
        this.targetPos = targetPos;
    }

    @Override
    public void act(float delta) {

        spawnTimer.tick(delta);

        updateMovement(delta);
        float xDiff = (pos.x - targetPos.x);
        float yDiff = (pos.y - targetPos.y);

        if (spawnTimer.isTimerEventReady()) {
            getStage().addActor(new ExplodePixelActor(getX(), getY(), .8f, .8f, Color.BLUE, 1));
            getStage().addActor(new ExplodePixelActor(getX(), getY(), .8f, .8f, Color.CYAN, 1));
        }

        if (Math.abs(xDiff) < 8.5f && Math.abs(yDiff) < 8.5f) {

            getStage().addActor(new ExplodePixelActor(getX(), getY(), 1f, 1f, Color.BLUE, 5, .5f));
            getStage().addActor(new ExplodePixelActor(getX(), getY(), 1f, 1f, Color.CYAN, 10, .5f));
            remove();
        }


        super.act(delta);
    }

    private void updateMovement(float deltaTime) {

//        pos.x = (MathUtils.lerp(pos.x, targetPos.x, 4f * deltaTime));
//        pos.y = (MathUtils.lerp(pos.y, targetPos.y, 4f * deltaTime));

        float startX = pos.x;
        float startY = pos.y;

        float endX = targetPos.x + (8);
        float endY = targetPos.y + (8);

        // Normalise Vector Using Pythagorean theorem Trigonometry
        // Finding The Hypotenuse
        // Power Of EndX-StartX is Opposite
        // Power Of EndY-StartY is Adjacent
        // Opposite + Adjacent is Hypotenuse
        float opposite = endX - startX;
        float adjacent = endY - startY;

        float distance = (float) Math.sqrt(Math.pow(opposite, 2) + Math.pow(adjacent, 2));
        float directionX = opposite / distance;
        float directionY = adjacent / distance;

        float speed = 500;
        pos.x += directionX * speed * deltaTime;
        pos.y += directionY * speed * deltaTime;
    }

    @Override
    public float getX() {
        return pos.x;
    }

    @Override
    public float getY() {
        return pos.y;
    }

    @Override
    public float getWidth() {
        return 16;
    }

    @Override
    public float getHeight() {
        return 16;
    }
}

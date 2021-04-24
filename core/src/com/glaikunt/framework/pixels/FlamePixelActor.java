package com.glaikunt.framework.pixels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FlamePixelActor extends Actor {

    private float[] xPos, yPos, xPosVol, yPosVol, angle, energy;
    private float width = 2, height = 2;
    private Color[] colour;

    public FlamePixelActor(float xPos, float yPos, float targetXPos, float targetYPos, float width, float height, Color color, int amount) {
        this(xPos, yPos, targetXPos, targetYPos, width, height, color, amount, .4f);
    }

    public FlamePixelActor(float xPos, float yPos, float targetXPos, float targetYPos, float width, float height, Color color, int amount, float speed) {

        this.xPos = new float[amount];
        this.yPos = new float[amount];
        this.xPosVol = new float[amount];
        this.yPosVol = new float[amount];
        this.angle = new float[amount];
        this.energy = new float[amount];
        this.colour = new Color[amount];
        this.width = width;
        this.height = height;

        for (int i = 0; i < this.xPos.length; i++) {
            this.xPos[i] = xPos;
            this.yPos[i] = yPos;
            this.xPosVol[i] = MathUtils.random() * speed;
            this.yPosVol[i] = MathUtils.random() * speed;

            energy[i] = MathUtils.random();
            colour[i] = new Color(color.r, color.g, color.b, energy[i]);

            float xDiff = -(xPos - targetXPos);
            float yDiff = -(yPos - targetYPos);

            this.angle[i] = (float) ((Math.atan2(yDiff, xDiff)));
            if (angle[i] == 0) {
                angle[i] = .1f;
            }
        }
    }

    @Override
    public void act(float delta) {

        boolean destroy = true;
        for (int i = 0; i < this.xPos.length; i++) {
            energy[i] -= 0.01d;
            xPos[i] += (xPosVol[i] * MathUtils.cos(angle[i])) ;
            yPos[i] += (yPosVol[i] * MathUtils.sin(angle[i])) ;
            if (energy[i] > 0) {
                destroy = false;
            }
        }

        if (destroy) {
            remove();
        }
    }

    @Override
    public void drawDebug(ShapeRenderer renderer) {

        for (int i = 0; i < this.xPos.length; i++) {
            colour[i].a = energy[i] <= 0 ? 0 : energy[i];
            renderer.setColor(colour[i]);
            renderer.rect(xPos[i], yPos[i], width/2, height/2, width, height, 1, 1, angle[i]);
        }
    }
}

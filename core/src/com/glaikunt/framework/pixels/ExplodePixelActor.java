package com.glaikunt.framework.pixels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

public class ExplodePixelActor extends Actor {

    private float[] xPos, yPos, xPosVol, yPosVol, angle, energy;
    private float width, height;
    private Color[] colour;

    public ExplodePixelActor(float xPos, float yPos, Color colour, int amount) {
        this(xPos, yPos, 2, 2, colour, amount, .2f);
    }

    public ExplodePixelActor(float xPos, float yPos, float width, float height, Color colour, int amount) {
        this(xPos, yPos, width, height, colour, amount, .2f);
    }

    public ExplodePixelActor(float xPos, float yPos, float width, float height, Color colour, int amount, float vel) {

        this.width = width;
        this.height = height;
        this.xPos = new float[amount];
        this.yPos = new float[amount];
        this.xPosVol = new float[amount];
        this.yPosVol = new float[amount];
        this.angle = new float[amount];
        this.energy = new float[amount];
        this.colour = new Color[amount];

        for (int i = 0; i < this.xPos.length; i++) {
            this.xPos[i] = xPos;
            this.yPos[i] = yPos;
            // * .9 makes a really cool effect
            this.xPosVol[i] = MathUtils.random() * vel;
            this.yPosVol[i] = MathUtils.random() * vel;
            energy[i] = MathUtils.random();

            this.colour[i] = new Color(1.0f, 1.0f, .0f, energy[i]);
            this.colour[i].b = colour.b;
            this.colour[i].r = colour.r;
            this.colour[i].g = colour.g;

            Random r = new Random();
            angle[i] = r.nextInt(6) + 1;
        }
    }

    @Override
    public void act(float delta) {

        boolean destroy = true;
        for (int i = 0; i < this.xPos.length; i++) {
            energy[i] -= 0.01d;
            xPos[i] += xPosVol[i] * MathUtils.cos(angle[i]);
            yPos[i] += yPosVol[i] * MathUtils.sin(angle[i]);
            if (energy[i] > 0) {
                destroy = false;
            }
        }

        if (destroy) {
            remove();
        }
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        for (int i = 0; i < this.xPos.length; i++) {
            colour[i].a = energy[i] <= 0 ? 0 : energy[i];
            shapes.setColor(colour[i]);
            shapes.rect(xPos[i], yPos[i], width/2, height/2, width, height, 1, 1, angle[i]);
        }
    }
}

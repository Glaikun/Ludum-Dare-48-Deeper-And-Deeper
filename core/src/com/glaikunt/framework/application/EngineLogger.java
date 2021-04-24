package com.glaikunt.framework.application;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;

public class EngineLogger extends Engine {

    private TickTimer logTImer = new TickTimer(.5f);

    @Override
    public void update(float deltaTime) {

        float startTime = System.currentTimeMillis();
        super.update(deltaTime);
        float endTime = System.currentTimeMillis();

        logTImer.tick(deltaTime);

        if (logTImer.isTimerEventReady() || (endTime - startTime) > 50) {
            Gdx.app.log("DEBUG", "Total Update time [" + (endTime - startTime) + "ms]");
        }
    }
}

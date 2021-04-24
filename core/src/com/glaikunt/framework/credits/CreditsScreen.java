package com.glaikunt.framework.credits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Screen;

public class CreditsScreen extends Screen {

    public CreditsScreen(ApplicationResources applicationResources) {
        super(applicationResources);
    }

    @Override
    public void update(float delta) {

        getBackground().act();
        getFront().act();
        getUX().act();
    }

    @Override
    public void render2D(float delta) {

        Gdx.gl.glClearColor(0, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getBackground().draw();
        getFront().draw();
        getUX().draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}

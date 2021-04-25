package com.glaikunt.framework.splash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Screen;
import com.glaikunt.framework.esc.system.AnimationSystem;
import com.glaikunt.framework.esc.system.BloatingSystem;
import com.glaikunt.framework.ui.OverlayActor;

public class SplashScreen extends Screen {

    public SplashScreen(ApplicationResources applicationResources) {
        super(applicationResources);
    }

    @Override
    public void show() {

        getFront().addActor(new ChooseWeaponActor(getApplicationResources()));
        getUX().addActor(new OverlayActor(getApplicationResources()));

        getEngine().addSystem(new AnimationSystem(getEngine()));
        getEngine().addSystem(new BloatingSystem(getEngine()));
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void update(float delta) {

        getApplicationResources().getFrontStageMousePosition().set(getFront().getCamera().unproject(getApplicationResources().getFrontStageMousePosition().set(Gdx.input.getX(), Gdx.input.getY(), 0)));

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
    public void pause() {

    }

    @Override
    public void resume() {

    }
}

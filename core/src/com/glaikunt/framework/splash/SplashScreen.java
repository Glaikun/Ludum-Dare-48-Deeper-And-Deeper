package com.glaikunt.framework.splash;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Screen;
import com.glaikunt.framework.application.TickTimer;
import com.glaikunt.framework.application.cache.TextureCache;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.system.AnimationSystem;
import com.glaikunt.framework.game.GameScreen;

public class SplashScreen extends Screen {

    private AnimationComponent animation;
    private TickTimer nextLevel = new TickTimer(6);

    public SplashScreen(ApplicationResources applicationResources) {
        super(applicationResources);
    }

    @Override
    public void show() {

        this.animation = new AnimationComponent(getApplicationResources().getCacheRetriever().geTextureCache(TextureCache.NEXT_STAGE), 25, 1);
        this.animation.setPlayMode(Animation.PlayMode.NORMAL);
        this.animation.setFramerate(.2f);

        Entity entity = new Entity();
        entity.add(animation);
        getEngine().addEntity(entity);

        getEngine().addSystem(new AnimationSystem(getEngine()));
    }

    @Override
    public void update(float delta) {

        if (animation.isAnimationFinished()) {

            nextLevel.tick(delta);
            if (nextLevel.isTimerEventReady()) {
                getDisplay().setScreen(new GameScreen(getApplicationResources()));
            }
        }
    }

    @Override
    public void render2D(float delta) {

        getFront().getBatch().begin();

        getFront().getBatch().draw(animation.getCurrentFrame(), 0, 0);

        getFront().getBatch().end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}

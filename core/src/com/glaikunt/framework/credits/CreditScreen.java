package com.glaikunt.framework.credits;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.Display;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Screen;
import com.glaikunt.framework.application.TickTimer;
import com.glaikunt.framework.application.cache.TextureCache;
import com.glaikunt.framework.esc.component.game.LevelComponent;
import com.glaikunt.framework.esc.component.misc.BloatingComponent;
import com.glaikunt.framework.esc.system.BloatingSystem;

import java.util.ArrayList;
import java.util.List;

public class CreditScreen extends Screen {

    private TickTimer textTimer;
    private List<CreditTextActor> credits = new ArrayList<>();
    private Entity endScreenEntity;
    private BloatingComponent bloating;
    private Vector2 size;
    private Texture heart;
    private float alpha = 0;

    private TickTimer endTimer = new TickTimer(12);

    public CreditScreen(ApplicationResources applicationResources) {
        super(applicationResources);
    }

    @Override
    public void show() {

        this.endScreenEntity = new Entity();
        this.bloating = new BloatingComponent();
        this.bloating.setMaxBloating(5);
        this.bloating.setSpeed(10f);
        this.endScreenEntity.add(bloating);
        getApplicationResources().getEngine().addEntity(endScreenEntity);
        getApplicationResources().getEngine().addSystem(new BloatingSystem(getEngine()));

        this.heart = getApplicationResources().getCacheRetriever().geTextureCache(TextureCache.HEART_ICON);
        this.size = new Vector2(heart.getWidth() * 8, heart.getHeight() * 8);

        this.textTimer = new TickTimer(3);
        this.textTimer.setTick(textTimer.getTargetTime());

        credits.add(new CreditTextActor(getApplicationResources(), "Game Over"));
        credits.add(new CreditTextActor(getApplicationResources(), "You Got To Stage"));
        credits.add(new CreditTextActor(getApplicationResources(), getApplicationResources().getGlobalEntity().getComponent(LevelComponent.class).getStage() + ""));

        credits.add(new CreditTextActor(getApplicationResources(), "    "));

        credits.add(new CreditTextActor(getApplicationResources(), "Created By"));
        credits.add(new CreditTextActor(getApplicationResources(), "Glaikunt"));

        String message = "Thanks For Playing!  \n\n";
        getFront().addActor(new TopTextActor(getApplicationResources(), message));
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void update(float delta) {

        textTimer.tick(delta);
        getBackground().act();
        getFront().act();
        getUX().act();

        if (textTimer.isTimerEventReady() && !credits.isEmpty()) {
            getFront().addActor(credits.get(0));
            credits.remove(0);
        }
    }

    @Override
    public void render2D(float delta) {
        Gdx.gl.glClearColor(0, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getFront().draw();

        if (credits.isEmpty()) {

            if (alpha < 1) {
                alpha += .2f * delta;
            } else if (alpha > 1) {
                alpha = 1;
            }

            getFront().getBatch().begin();
            getFront().getBatch().setColor(1, 1, 1, alpha);
            getFront().getBatch().draw(heart, ((Display.WORLD_WIDTH / 2) - (size.x / 2f)) - (bloating.getBloating() / 2), ((Display.WORLD_HEIGHT / 2) - (size.y / 2f)) - (bloating.getBloating() / 2), size.x + (bloating.getBloating() / 2), size.y + (bloating.getBloating() / 2));
            getFront().getBatch().setColor(1, 1, 1, 1);
            getFront().getBatch().end();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}

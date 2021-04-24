package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.IntMap;
import com.glaikunt.framework.Display;
import com.glaikunt.framework.application.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.cache.FontCache;
import com.glaikunt.framework.application.cache.TextureCache;
import com.glaikunt.framework.esc.component.common.HealthComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.demon.DemonComponent;

import java.util.Iterator;

public class DemonActor extends Actor {

    private Texture demonTexture, healthBarTexture;
    private DemonComponent demon;
    private HealthComponent health;

    private float healthDeltaWidth = 0;
    private float healthMaxWidth;

    private Vector2 healthBarPos, healthBarSize, borderSize;

    private Color tintOver;

    private IntMap<Container> entries = new IntMap<>();
    private BitmapFont hitFont;

    public DemonActor(ApplicationResources applicationResources) {
        super(applicationResources);

        this.demonTexture = applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER);
        this.healthBarTexture = applicationResources.getCacheRetriever().geTextureCache(TextureCache.SPOT);

        this.size = new SizeComponent(demonTexture.getWidth() * 4, demonTexture.getHeight() * 4);
        this.pos = new PositionComponent((Display.WORLD_WIDTH / 2) - (getWidth() / 2), (Display.WORLD_HEIGHT / 2) - (getHeight()));

        this.health = new HealthComponent();
        this.health.setHealth(5);

        this.demon = new DemonComponent();

        //HEALTH BAR
        this.healthMaxWidth = Display.WORLD_WIDTH / 1.5f;
        this.healthDeltaWidth = (health.getDeltaHealth() / health.getHealth()) * healthMaxWidth;
        this.healthBarSize = new Vector2(10, 10);
        this.borderSize = new Vector2(2.5f, 2.5f);
        this.healthBarPos = new Vector2((Display.WORLD_WIDTH / 2f) - (healthMaxWidth / 2), Display.WORLD_HEIGHT - (healthBarSize.y * 2) - 15);
        this.tintOver = new Color(1, 1, 1f, .25f);
        //HEALTH BAR

        this.hitFont = applicationResources.getCacheRetriever().getFontCache(FontCache.BATTLE_FONT);
        this.hitFont.getRegion().flip(true, false);

        Entity entity = new Entity();

        entity.add(pos);
        entity.add(size);
        entity.add(health);
        entity.add(demon);

        getApplicationResources().getEngine().addEntity(entity);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.setColor(1, 0, 0, 1f);
        batch.draw(demonTexture, getX(), getY(), getWidth(), getHeight());
        batch.setColor(1, 1, 1, 1f);

        batch.setColor(Color.RED);
        batch.draw(healthBarTexture, healthBarPos.x, healthBarPos.y, healthDeltaWidth, healthBarSize.y * 2);

        batch.setColor(Color.FOREST);
        batch.draw(healthBarTexture, healthBarPos.x, healthBarPos.y - borderSize.y, healthMaxWidth, borderSize.y);
        batch.draw(healthBarTexture, healthBarPos.x, healthBarPos.y + (healthBarSize.y * 2), healthMaxWidth, borderSize.y);
        batch.draw(healthBarTexture, healthBarPos.x - borderSize.x, healthBarPos.y, borderSize.x, (healthBarSize.y * 2));
        batch.draw(healthBarTexture, healthBarPos.x + healthMaxWidth, healthBarPos.y, borderSize.x, (healthBarSize.y * 2));

        batch.setColor(tintOver);
        batch.draw(healthBarTexture, healthBarPos.x, healthBarPos.y + healthBarSize.y, healthMaxWidth, healthBarSize.y);

        batch.setColor(1, 1, 1f, 1);
    }

    @Override
    public void act(float delta) {

        for (Iterator<IntMap.Entry<Container>> i = entries.entries().iterator(); i.hasNext(); ) {
            IntMap.Entry<Container> next = i.next();
            Container label = next.value;

            if (label.getColor().a <= 0) {
                label.remove();
                i.remove();
            }
        }

        if (healthDeltaWidth != (health.getDeltaHealth() / health.getHealth()) * healthMaxWidth) {

            healthDeltaWidth = (health.getDeltaHealth() / health.getHealth()) * healthMaxWidth;
        }

        if (getStage() != null) {

            for (Iterator<Integer> i = demon.getHitDmg().iterator(); i.hasNext(); ) {



                Label label = new Label(i.next() + "", new Label.LabelStyle(hitFont, Color.WHITE));
                Container<Label> container = new Container<>(label);
                container.setPosition(getX() + ( getWidth()/2), getY() + getHeight() + (label.getHeight()/2));
                container.addAction(Actions.fadeOut(1f));
                float x = container.getX() + MathUtils.random(-50, 50);

                if (container.getX()-x > 0) {
                    container.addAction(Actions.rotateBy(MathUtils.random(0, 45), 1));
                } else {
                    container.addAction(Actions.rotateBy(MathUtils.random(-45, 0), 1));
                }

                container.addAction(Actions.moveTo(x, container.getY() + MathUtils.random(0, 25), 1));

                container.setTransform(true);
                getStage().addActor(container);

                entries.put(entries.size, container);
                i.remove();
            }
        }
    }
}

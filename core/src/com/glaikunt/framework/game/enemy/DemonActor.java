package com.glaikunt.framework.game.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.glaikunt.framework.Display;
import com.glaikunt.framework.application.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.cache.TextureCache;
import com.glaikunt.framework.esc.component.common.HealthComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.demon.DemonComponent;

public class DemonActor extends Actor {

    private Texture demonTexture;
    private HealthComponent health;

    public DemonActor(ApplicationResources applicationResources) {
        super(applicationResources);

        this.demonTexture = applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER);

        this.size = new SizeComponent(demonTexture.getWidth() * 4, demonTexture.getHeight() * 4);
        this.pos = new PositionComponent((Display.WORLD_WIDTH/2) - (getWidth()/2), (Display.WORLD_HEIGHT/2) - (getHeight()));

        this.health = new HealthComponent();

        Entity entity = new Entity();

        entity.add(pos);
        entity.add(size);
        entity.add(health);
        entity.add(new DemonComponent());

        getApplicationResources().getEngine().addEntity(entity);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.setColor(1, 0, 0, 1f);
        batch.draw(demonTexture, getX(), getY(), getWidth(), getHeight());
        batch.setColor(1, 1, 1, 1f);
    }
}

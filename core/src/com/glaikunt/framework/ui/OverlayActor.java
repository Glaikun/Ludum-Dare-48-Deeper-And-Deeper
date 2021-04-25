package com.glaikunt.framework.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.IntMap;
import com.glaikunt.framework.Display;
import com.glaikunt.framework.application.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.cache.TextureCache;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;

public class OverlayActor extends Actor {

    private AnimationComponent overlay;

    public OverlayActor(ApplicationResources applicationResources) {
        super(applicationResources);

        int i = 0;
        IntMap<Texture> overlayMap = new IntMap<>();
        overlayMap.put(i, applicationResources.getTexture(TextureCache.OVERLAY_1));
        i++;
        overlayMap.put(i, applicationResources.getTexture(TextureCache.OVERLAY_2));
        i++;
        overlayMap.put(i, applicationResources.getTexture(TextureCache.OVERLAY_3));
        i++;
        overlayMap.put(i, applicationResources.getTexture(TextureCache.OVERLAY_4));
        i++;
        overlayMap.put(i, applicationResources.getTexture(TextureCache.OVERLAY_5));
        i++;
        overlayMap.put(i, applicationResources.getTexture(TextureCache.OVERLAY_6));
        i++;
        overlayMap.put(i, applicationResources.getTexture(TextureCache.OVERLAY_7));
        i++;
        overlayMap.put(i, applicationResources.getTexture(TextureCache.OVERLAY_8));
        i++;
        overlayMap.put(i, applicationResources.getTexture(TextureCache.OVERLAY_9));
        i++;
        overlayMap.put(i, applicationResources.getTexture(TextureCache.OVERLAY_10));

        this.overlay = new AnimationComponent(overlayMap);
        this.overlay.setPlayMode(Animation.PlayMode.LOOP);

        Entity entity = new Entity();

        entity.add(overlay);
        applicationResources.getEngine().addEntity(entity);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.setColor(1, 1, 1, .3f);
        batch.draw(overlay.getCurrentFrame(), 0, 0, Display.WORLD_WIDTH, Display.WORLD_HEIGHT);
        batch.setColor(1, 1, 1, 1);
    }
}

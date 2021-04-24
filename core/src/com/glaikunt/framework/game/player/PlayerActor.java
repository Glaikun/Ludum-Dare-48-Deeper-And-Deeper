package com.glaikunt.framework.game.player;

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
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.player.PlayerComponent;
import com.glaikunt.framework.esc.component.player.WeaponComponent;
import com.glaikunt.framework.game.weapon.WeaponType;

public class PlayerActor extends Actor {

    private Texture playerTexture;

    public PlayerActor(ApplicationResources applicationResources) {
        super(applicationResources);

        this.playerTexture = applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER);

        this.size = new SizeComponent(playerTexture.getWidth() * 2, playerTexture.getHeight() * 2);
        this.pos = new PositionComponent((Display.WORLD_WIDTH/2) - (getWidth()*3), (Display.WORLD_HEIGHT/2) - (getHeight()/2));

        WeaponComponent weapon = new WeaponComponent();
        weapon.setWeaponType(WeaponType.MELEE);

        Entity entity = new Entity();

        entity.add(pos);
        entity.add(size);
        entity.add(new HealthComponent());
        entity.add(new VelocityComponent(100, 100));
        entity.add(new PlayerComponent());
        entity.add(weapon);
        getApplicationResources().getEngine().addEntity(entity);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(playerTexture, getX(), getY(), getWidth(), getHeight());
    }
}

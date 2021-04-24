package com.glaikunt.framework.game.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.application.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.cache.TextureCache;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.player.AttackComponent;
import com.glaikunt.framework.esc.component.player.WeaponComponent;
import com.glaikunt.framework.game.weapon.WeaponType;

public class OldPlayerActor extends Actor {

    private Texture playerTexture;

    private WeaponComponent weapon;

    public OldPlayerActor(ApplicationResources applicationResources, Vector2 pos, WeaponType weapon) {
        super(applicationResources);

        this.playerTexture = applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER);

        this.size = new SizeComponent(playerTexture.getWidth() * 2, playerTexture.getHeight() * 2);
        this.pos = new PositionComponent(pos);
        this.weapon = new WeaponComponent();
        this.weapon.setWeaponType(weapon);

        Entity entity = new Entity();

        entity.add(this.pos);
        entity.add(this.size);
        entity.add(new AttackComponent());
        entity.add(this.weapon);

        getApplicationResources().getEngine().addEntity(entity);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(playerTexture, getX(), getY(), getWidth(), getHeight());
    }

}
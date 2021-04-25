package com.glaikunt.framework.game.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.glaikunt.framework.application.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Rectangle;
import com.glaikunt.framework.application.TickTimer;
import com.glaikunt.framework.application.cache.TextureCache;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.common.CollisionComponent;
import com.glaikunt.framework.esc.component.common.HealthComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.player.AttackComponent;
import com.glaikunt.framework.esc.component.player.GhostPlayerComponent;
import com.glaikunt.framework.esc.component.player.ValidateAttackComponent;
import com.glaikunt.framework.esc.component.player.WeaponComponent;
import com.glaikunt.framework.game.enemy.DemonActor;
import com.glaikunt.framework.game.weapon.WeaponType;

public class OldPlayerActor extends Actor {

    private Texture healthTexture;
    private TextureRegion playerTexture;

    private WeaponComponent weapon;
    private HealthComponent health;

    private float healthDeltaWidth;
    private float healthMaxWidth;

    private CollisionComponent collision;

    public OldPlayerActor(ApplicationResources applicationResources, Vector2 pos, WeaponType weapon, DemonActor demonActor) {
        super(applicationResources);

        this.playerTexture = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER_IDLE), 3, 1).getCurrentAnimation().getKeyFrames()[0];

        this.size = new SizeComponent(playerTexture.getRegionWidth() * 2, playerTexture.getRegionHeight() * 2);
        this.pos = new PositionComponent(pos);
        this.weapon = new WeaponComponent();
        this.weapon.setWeaponType(weapon);

        this.healthTexture = applicationResources.getCacheRetriever().geTextureCache(TextureCache.SPOT);

        this.health = new HealthComponent();
        this.health.setHealth(5);

        this.healthMaxWidth = size.x;
        this.healthDeltaWidth = (health.getDeltaHealth() / health.getHealth()) * healthMaxWidth;

        this.collision = new CollisionComponent();
        Rectangle bound = new Rectangle();
        bound.set(pos.x + ((size.x/2)/2), pos.y, size.x/2, size.y/2);
        collision.setBound(bound);


        ValidateAttackComponent validateAttackComponent = new ValidateAttackComponent();
        Rectangle hostPos = new Rectangle();
        int collisionSpace = 15;
        hostPos.set(demonActor.getX() - collisionSpace, demonActor.getY(), collisionSpace, demonActor.getHeight());
        validateAttackComponent.setInRange(hostPos.contains(getX() + (getWidth() / 2), getY()));

        Entity entity = new Entity();

        entity.add(this.pos);
        entity.add(this.size);
        entity.add(validateAttackComponent);
        entity.add(this.health);
        AttackComponent attack = new AttackComponent();
        attack.setAttackSpeed(new TickTimer(weapon.getAttackSpeed()));
        attack.setDmg(weapon.getDamage());
        entity.add(attack);
        entity.add(new GhostPlayerComponent());
        entity.add(collision);
        entity.add(this.weapon);

        getApplicationResources().getEngine().addEntity(entity);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.setColor(1, 1, 1, .8f);
        batch.draw(playerTexture, getX(), getY(), getWidth(), getHeight());
        batch.setColor(1, 1, 1, 1f);

        batch.setColor(Color.RED);
        batch.draw(healthTexture, getX(), getY() + getHeight() + healthTexture.getHeight() + 5, healthDeltaWidth, 5);
        batch.setColor(1, 1, 1, 1f);
    }

    @Override
    public void act(float delta) {

        collision.getBound().set(pos.x + ((size.x/2)/2), pos.y, size.x/2, size.y/2);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        if (Gdx.app.getLogLevel() == Logger.NONE) {
            return;
        }

        shapes.rect(collision.getBound().getX(), collision.getBound().getY(), collision.getBound().getWidth(), collision.getBound().getHeight());
    }
}

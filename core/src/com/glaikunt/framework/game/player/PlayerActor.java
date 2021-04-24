package com.glaikunt.framework.game.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.glaikunt.framework.Display;
import com.glaikunt.framework.application.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.TickTimer;
import com.glaikunt.framework.application.cache.TextureCache;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.animation.AnimationsComponent;
import com.glaikunt.framework.esc.component.common.HealthComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.player.AttackComponent;
import com.glaikunt.framework.esc.component.player.PlayerComponent;
import com.glaikunt.framework.esc.component.player.WeaponComponent;
import com.glaikunt.framework.game.weapon.WeaponType;

public class PlayerActor extends Actor {

    private AnimationComponent playerIdle, playerSitting, playerRightMovement, playerLeftMovement;
    private Texture playerTexture, healthTexture;

    private PlayerComponent player;
    private HealthComponent health;
    private WeaponComponent weapon;

    private float healthDeltaWidth;
    private float healthMaxWidth;

    public PlayerActor(ApplicationResources applicationResources) {
        super(applicationResources);

        this.playerIdle = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER_IDLE), 3, 1);
        this.playerIdle.setFramerate(.2f);

        this.playerSitting = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER_SITTING), 3, 1);
        this.playerSitting.setFramerate(.35f);
        this.playerSitting.setPlayMode(Animation.PlayMode.LOOP);

        this.playerRightMovement = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER_MOVEMENT), 3, 1);
        this.playerRightMovement.setFramerate(.15f);
        this.playerRightMovement.setPlayMode(Animation.PlayMode.LOOP);

        this.playerLeftMovement = new AnimationComponent(applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER_MOVEMENT), 3, 1);
        this.playerLeftMovement.setFramerate(.15f);
        this.playerLeftMovement.setPlayMode(Animation.PlayMode.LOOP);

        for (TextureRegion region : playerLeftMovement.getCurrentAnimation().getKeyFrames()) {
            region.flip(true, false);
        }

        for (TextureRegion region : playerSitting.getCurrentAnimation().getKeyFrames()) {
            region.flip(true, false);
        }

        this.playerTexture = applicationResources.getCacheRetriever().geTextureCache(TextureCache.PLAYER_IDLE);
        this.healthTexture = applicationResources.getCacheRetriever().geTextureCache(TextureCache.SPOT);

        this.size = new SizeComponent(playerIdle.getCurrentFrame().getRegionWidth()  * 2, playerIdle.getCurrentFrame().getRegionHeight() * 2);
        this.pos = new PositionComponent((Display.WORLD_WIDTH/2) - (getWidth()*2.5f), (Display.WORLD_HEIGHT/2) + (getHeight()/2) + 2.5f);

        this.weapon = new WeaponComponent();
        this.weapon.setWeaponType(WeaponType.MELEE);

        this.health = new HealthComponent();
        this.health.setHealth(5);

        this.healthMaxWidth = size.x;
        this.healthDeltaWidth = (health.getDeltaHealth() / health.getHealth()) * healthMaxWidth;

        this.player = new PlayerComponent();

        Entity entity = new Entity();

        AnimationsComponent animationsComponent = new AnimationsComponent();
        animationsComponent.add(playerIdle);
        animationsComponent.add(playerLeftMovement);
        animationsComponent.add(playerRightMovement);
        animationsComponent.add(playerSitting);

        entity.add(animationsComponent);
        entity.add(pos);
        entity.add(size);
        entity.add(health);
        entity.add(new VelocityComponent(100, 100));
        entity.add(player);
        AttackComponent attack = new AttackComponent();
        attack.setAttackSpeed(new TickTimer(weapon.getWeaponType().getAttackSpeed().getTargetTime()));
        attack.setDmg(weapon.getWeaponType().getDamage());
        entity.add(attack);
        entity.add(weapon);
        getApplicationResources().getEngine().addEntity(entity);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (player.isSitting()) {

            batch.draw(playerSitting.getCurrentFrame(), getX(), getY(), getWidth(), getHeight());
            if (player.isMoving()) {
                player.setSitting(false);
            }
        } else if (player.isMoving()) {

            if (!player.isMovingLeft()) {
                batch.draw(playerLeftMovement.getCurrentFrame(), getX(), getY(), getWidth(), getHeight());
            } else {
                batch.draw(playerRightMovement.getCurrentFrame(), getX(), getY(), getWidth(), getHeight());
            }
        } else {

            batch.draw(playerIdle.getCurrentFrame(), getX(), getY(), getWidth(), getHeight());
        }



        batch.setColor(Color.RED);
//        batch.draw(healthTexture, getX(), getY() + getHeight() + healthTexture.getHeight() + 5, healthDeltaWidth, 5);
        batch.setColor(1, 1, 1, 1f);
    }

    @Override
    public void act(float delta) {

        if (healthDeltaWidth != (health.getDeltaHealth() / health.getHealth()) * healthMaxWidth) {
            healthDeltaWidth = (health.getDeltaHealth() / health.getHealth()) * healthMaxWidth;
        }
    }

    public WeaponComponent getWeapon() {
        return weapon;
    }
}

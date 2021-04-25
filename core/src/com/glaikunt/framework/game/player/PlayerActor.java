package com.glaikunt.framework.game.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.glaikunt.framework.Display;
import com.glaikunt.framework.application.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Rectangle;
import com.glaikunt.framework.application.TickTimer;
import com.glaikunt.framework.application.cache.FontCache;
import com.glaikunt.framework.application.cache.TextureCache;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.animation.AnimationsComponent;
import com.glaikunt.framework.esc.component.common.HealthComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.common.VelocityComponent;
import com.glaikunt.framework.esc.component.game.LevelComponent;
import com.glaikunt.framework.esc.component.player.AttackComponent;
import com.glaikunt.framework.esc.component.player.PlayerComponent;
import com.glaikunt.framework.esc.component.player.ValidateAttackComponent;
import com.glaikunt.framework.esc.component.player.WeaponComponent;
import com.glaikunt.framework.game.enemy.DemonActor;
import com.glaikunt.framework.game.weapon.WeaponType;

public class PlayerActor extends Actor {

    private AnimationComponent playerIdle, playerSitting, playerRightMovement, playerLeftMovement;
    private Texture healthTexture;
    private TextureRegion sword, yourAWizardHarry, support;

    private PlayerComponent player;
    private HealthComponent health;
    private WeaponComponent weapon;
    private ValidateAttackComponent validateAttack;

    private SizeComponent itemSize;

    private LevelComponent level;

    private float healthDeltaWidth;
    private float healthMaxWidth;

    private BitmapFont notInRangeFont;
    private GlyphLayout notInRangeLayout, pressSpaceToStartLayout;

    private Polygon line;

    private DemonActor demon;

    public PlayerActor(ApplicationResources applicationResources, DemonActor demon) {
        super(applicationResources);

        this.demon = demon;

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

        this.healthTexture = applicationResources.getCacheRetriever().geTextureCache(TextureCache.SPOT);

        this.size = new SizeComponent(playerIdle.getCurrentFrame().getRegionWidth()  * 2, playerIdle.getCurrentFrame().getRegionHeight() * 2);
        this.pos = new PositionComponent((Display.WORLD_WIDTH/2) - (getWidth()*2.5f), (Display.WORLD_HEIGHT/2) + (getHeight()/2) + 2.5f);

        this.weapon = new WeaponComponent();
        this.weapon.setWeaponType(getApplicationResources().getGlobalEntity().getComponent(LevelComponent.class).getWeaponType());

        this.health = new HealthComponent();
        this.health.setHealth(5);

        this.healthMaxWidth = size.x;
        this.healthDeltaWidth = (health.getDeltaHealth() / health.getHealth()) * healthMaxWidth;

        this.player = new PlayerComponent();

        this.validateAttack = new ValidateAttackComponent();

        this.notInRangeFont = applicationResources.getCacheRetriever().getFontCache(FontCache.BIG_FONT);
        this.notInRangeLayout = new GlyphLayout(notInRangeFont, "Not In Range", Color.WHITE, 1, Align.left, false);
        this.pressSpaceToStartLayout = new GlyphLayout(notInRangeFont, "Press Space To Start", Color.WHITE, 1, Align.left, false);

        this.level = applicationResources.getGlobalEntity().getComponent(LevelComponent.class);

        this.sword = new TextureRegion(applicationResources.getTexture(TextureCache.ATTACK_ICON));
        this.yourAWizardHarry = new TextureRegion(applicationResources.getTexture(TextureCache.MAGIC_ICON));
        this.support = new TextureRegion(applicationResources.getTexture(TextureCache.SUPPORT_ICON));
        if (weapon.getWeaponType().equals(WeaponType.MELEE)) {
            itemSize = new SizeComponent(sword.getRegionWidth()*1.5f, sword.getRegionHeight()*2);
        } else  if (weapon.getWeaponType().equals(WeaponType.RANGED)) {
            itemSize = new SizeComponent(sword.getRegionWidth()*2f, sword.getRegionHeight()*2);
        }

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
        entity.add(validateAttack);
        entity.add(new VelocityComponent(100, 100));
        entity.add(player);
        AttackComponent attack = new AttackComponent();
        attack.setAttackSpeed(new TickTimer(weapon.getWeaponType().getAttackSpeed()));
        attack.setDmg(weapon.getWeaponType().getDamage());
        entity.add(attack);
        entity.add(weapon);
        getApplicationResources().getEngine().addEntity(entity);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (!player.isSitting() && weapon.getWeaponType().equals(WeaponType.MELEE)) {

            if (player.isMovingLeft()) {
                if (sword.isFlipX()) {
                    sword.flip(true, false);
                }
                batch.draw(sword, getX() + itemSize.x - 10, getY() + (itemSize.y / 1.5f), itemSize.x, itemSize.y);
            } else {
                if (!sword.isFlipX()) {
                    sword.flip(true, false);
                }
                batch.draw(sword, getX() - 10, getY() + (itemSize.y / 1.5f), itemSize.x, itemSize.y);
            }
        } else if (weapon.getWeaponType().equals(WeaponType.RANGED)) {

            batch.draw(yourAWizardHarry, getX() + (getWidth()/2) - (itemSize.x/2), getY() + getHeight(), itemSize.x, itemSize.y);
        }

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
        batch.draw(healthTexture, getX(), getY() + getHeight() + healthTexture.getHeight() + 5, healthDeltaWidth, 5);
        batch.setColor(1, 1, 1, 1f);

        if (!level.isLevelStarted() ) {

            if (!validateAttack.isInRange()) {
                notInRangeFont.draw(batch, notInRangeLayout, (Display.WORLD_WIDTH / 2) - (notInRangeLayout.width / 2), (Display.WORLD_HEIGHT) - (notInRangeLayout.height) - 10);
            } else {
                notInRangeFont.draw(batch, pressSpaceToStartLayout, (Display.WORLD_WIDTH / 2) - (pressSpaceToStartLayout.width / 2), (Display.WORLD_HEIGHT) - (pressSpaceToStartLayout.height) - 10);
            }
        }
    }

    @Override
    public void act(float delta) {

        if (healthDeltaWidth != (health.getDeltaHealth() / health.getHealth()) * healthMaxWidth) {
            healthDeltaWidth = (health.getDeltaHealth() / health.getHealth()) * healthMaxWidth;
        }
    }

    @Override
    protected void drawDebugBounds(ShapeRenderer shapes) {


    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        if (weapon.getWeaponType().equals(WeaponType.RANGED) && !level.isLevelStarted()) {
            shapes.line(getX() + (getWidth() / 2), getY() + (getHeight() / 2), getApplicationResources().getFrontStageMousePosition().x, getApplicationResources().getFrontStageMousePosition().y);

            validateAttack.getCurrentPos().set(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
            validateAttack.getTargetPos().set(getApplicationResources().getFrontStageMousePosition().x, getApplicationResources().getFrontStageMousePosition().y);
        }
        if (Gdx.app.getLogLevel() == Logger.NONE) {
            return;
        }

        for (Rectangle rectangle : validateAttack.getAvailableSpace()) {
            shapes.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        shapes.rect(getX() + ((getWidth()/2)/2), getY(), getWidth()/2, getHeight()/2);
    }

    public WeaponComponent getWeapon() {
        return weapon;
    }

    public ValidateAttackComponent getValidateAttack() {
        return validateAttack;
    }
}

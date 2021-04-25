package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.common.HealthComponent;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.demon.DemonComponent;
import com.glaikunt.framework.esc.component.game.LevelComponent;
import com.glaikunt.framework.esc.component.player.AttackComponent;
import com.glaikunt.framework.esc.component.player.ValidateAttackComponent;
import com.glaikunt.framework.esc.component.player.WeaponComponent;
import com.glaikunt.framework.game.weapon.WeaponType;

public class AttackSystem extends EntitySystem {

    private ImmutableArray<Entity> playerEntities, allWeapons;
    private Entity enemyEntity;

    private LevelComponent level;

    private ComponentMapper<AttackComponent> attackCM = ComponentMapper.getFor(AttackComponent.class);
    private ComponentMapper<WeaponComponent> weaponCM = ComponentMapper.getFor(WeaponComponent.class);
    private ComponentMapper<PositionComponent> posCM = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<ValidateAttackComponent> validateCM = ComponentMapper.getFor(ValidateAttackComponent.class);

    private ComponentMapper<DemonComponent> demonCM = ComponentMapper.getFor(DemonComponent.class);

    private ComponentMapper<HealthComponent> healthCM = ComponentMapper.getFor(HealthComponent.class);

    public AttackSystem(ApplicationResources applicationResources) {

        this.playerEntities = applicationResources.getEngine().getEntitiesFor(Family.all(
                WeaponComponent.class,
                HealthComponent.class,
                ValidateAttackComponent.class,
                AttackComponent.class
        ).get());

        this.allWeapons = applicationResources.getEngine().getEntitiesFor(Family.all(
                WeaponComponent.class,
                ValidateAttackComponent.class,
                PositionComponent.class
        ).get());

        this.enemyEntity = applicationResources.getEngine().getEntitiesFor(Family.all(
                DemonComponent.class,
                AttackComponent.class,
                HealthComponent.class
        ).get())
                .get(0);
        this.level = applicationResources.getGlobalEntity().getComponent(LevelComponent.class);
    }

    @Override
    public void update(float delta) {

        DemonComponent demon = demonCM.get(enemyEntity);
        HealthComponent demonHealth = healthCM.get(enemyEntity);
        AttackComponent demonAttack = attackCM.get(enemyEntity);

        if (demonAttack.getAttackSpeed().isTimerPassedTarget()) {
            demonAttack.getAttackSpeed().resetTick();
        }

        if (!MathUtils.isEqual(demonHealth.getDeltaHealth(), demonHealth.getLerpWidth(), .01f)) {
            demonHealth.setDeltaHealth(MathUtils.lerp(demonHealth.getDeltaHealth(), demonHealth.getLerpWidth(), 2.5f * delta));
        } else {
            demonHealth.setDeltaHealth(demonHealth.getLerpWidth());
        }

        if (!level.isLevelStarted() || level.isLevelComplete()) {
            return;
        }

        if (demonHealth.getLerpWidth() <= 0 && MathUtils.isEqual(demonHealth.getLerpWidth(), demonHealth.getDeltaHealth(), 1)) {
            level.setLevelComplete(true);
            return;
        }

        demonAttack.getAttackSpeed().tick(delta);
        for (int pe = 0; pe < playerEntities.size(); ++pe) {

            Entity playerEntity = playerEntities.get(pe);
            WeaponComponent playerWeapon = weaponCM.get(playerEntity);
            HealthComponent playerHealth = healthCM.get(playerEntity);
            ValidateAttackComponent playerVal = validateCM.get(playerEntity);
            AttackComponent playerAttack = attackCM.get(playerEntity);
            PositionComponent playerPos = posCM.get(playerEntity);

            if (playerAttack.isDead()) {
                continue;
            }

            playerAttack.getAttackSpeed().tick(delta);

            if (!playerWeapon.getWeaponType().equals(WeaponType.SUPPORT) && playerVal.isInRange() && demonHealth.getLerpWidth() > 0 && playerAttack.getAttackSpeed().isTimerEventReady()) {
                float extraDmg = 0;
                for (int vai = 0; vai < allWeapons.size(); ++vai) {

                    Entity deltaEntity = allWeapons.get(vai);
                    WeaponComponent deltaWeapon = weaponCM.get(deltaEntity);
                    ValidateAttackComponent deltaValidateAttack = validateCM.get(deltaEntity);

                    if (deltaWeapon.getWeaponType().equals(WeaponType.SUPPORT) && deltaValidateAttack.getSupportArea().contains(playerPos.x, playerPos.y)) {
                        extraDmg = deltaWeapon.getWeaponType().getDamage();
                        break;
                    }
                }

                float dmg = playerWeapon.getWeaponType().getDamage() + extraDmg;
                demonHealth.setLerpWidth(demonHealth.getLerpWidth() - dmg);
                if (demonHealth.getLerpWidth() < 0) {
                    demonHealth.setLerpWidth(0);
                }
                playerAttack.setJustAttacked(true);
                demon.getHitDmg().add((int) (dmg * 10));
            }

            if (playerHealth.getLerpWidth() > 0 && demonAttack.getAttackSpeed().isTimerPassedTarget()) {
                playerHealth.setLerpWidth(playerHealth.getLerpWidth() - demonAttack.getDmg());
                if (playerHealth.getLerpWidth() < 0) {
                    playerHealth.setLerpWidth(0);
                }
            }

            if (!MathUtils.isEqual(playerHealth.getLerpWidth(), playerHealth.getDeltaHealth(), .01f)) {
                playerHealth.setDeltaHealth(MathUtils.lerp(playerHealth.getDeltaHealth(), playerHealth.getLerpWidth(), 2.5f * delta));
            } else if (playerHealth.getLerpWidth() != playerHealth.getDeltaHealth()) {
                playerHealth.setDeltaHealth(playerHealth.getLerpWidth());
            }
        }
    }
}

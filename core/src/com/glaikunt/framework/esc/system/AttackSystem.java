package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.common.HealthComponent;
import com.glaikunt.framework.esc.component.demon.DemonComponent;
import com.glaikunt.framework.esc.component.game.LevelComponent;
import com.glaikunt.framework.esc.component.player.AttackComponent;
import com.glaikunt.framework.esc.component.player.ValidateAttackComponent;
import com.glaikunt.framework.esc.component.player.WeaponComponent;

public class AttackSystem extends EntitySystem {

    private ImmutableArray<Entity> playerEntities, enemyEntities;

    private LevelComponent level;

    private ComponentMapper<AttackComponent> attackCM = ComponentMapper.getFor(AttackComponent.class);
    private ComponentMapper<WeaponComponent> weaponCM = ComponentMapper.getFor(WeaponComponent.class);
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
        this.enemyEntities = applicationResources.getEngine().getEntitiesFor(Family.all(
                DemonComponent.class,
                AttackComponent.class,
                HealthComponent.class
        ).get());
        this.level = applicationResources.getGlobalEntity().getComponent(LevelComponent.class);
    }

    @Override
    public void update(float delta) {

        if (!level.isLevelStarted() && !level.isLevelComplete()) {
            return;
        }

        for (int pe = 0; pe < playerEntities.size(); ++pe) {

            Entity playerEntity = playerEntities.get(pe);
            WeaponComponent playerWeapon = weaponCM.get(playerEntity);
            HealthComponent playerHealth = healthCM.get(playerEntity);
            ValidateAttackComponent playerVal = validateCM.get(playerEntity);
            AttackComponent playerAttack = attackCM.get(playerEntity);

            playerAttack.getAttackSpeed().tick(delta);

            for (int ee = 0; ee < enemyEntities.size(); ++ee) {

                Entity enemyEntity = enemyEntities.get(ee);
                DemonComponent demon = demonCM.get(enemyEntity);
                HealthComponent demonHealth = healthCM.get(enemyEntity);
                AttackComponent demonAttack = attackCM.get(enemyEntity);

                demonAttack.getAttackSpeed().tick(delta);

                if (playerVal.isInRange() && demonHealth.getLerpWidth() > 0 && playerAttack.getAttackSpeed().isTimerEventReady()) {
                    demonHealth.setLerpWidth(demonHealth.getLerpWidth() - playerWeapon.getWeaponType().getDamage());
                    demon.getHitDmg().add((int) (playerWeapon.getWeaponType().getDamage() * 10));
                }

                if (playerVal.isInRange() && !MathUtils.isEqual(demonHealth.getLerpWidth(), demonHealth.getDeltaHealth(), .5f)) {
                    demonHealth.setDeltaHealth(MathUtils.lerp(demonHealth.getDeltaHealth(), demonHealth.getLerpWidth(), 5 * delta));
                }else if (demonHealth.getLerpWidth() != demonHealth.getDeltaHealth()) {
                    demonHealth.setDeltaHealth(demonHealth.getLerpWidth());
                }

                if (playerHealth.getLerpWidth() > 0 && demonAttack.getAttackSpeed().isTimerEventReady()) {
                    playerHealth.setLerpWidth(playerHealth.getLerpWidth() - demonAttack.getDmg());
                }

                if (!MathUtils.isEqual(playerHealth.getLerpWidth(), playerHealth.getDeltaHealth(), .5f)) {
                    playerHealth.setDeltaHealth(MathUtils.lerp(playerHealth.getDeltaHealth(), playerHealth.getLerpWidth(), 5 * delta));
                } else if (playerHealth.getLerpWidth() != playerHealth.getDeltaHealth()) {
                    playerHealth.setDeltaHealth(playerHealth.getLerpWidth());
                }

                if (demonHealth.getLerpWidth() <= 0 && MathUtils.isEqual(demonHealth.getLerpWidth(), demonHealth.getDeltaHealth(), 1) ) {
                    level.setLevelComplete(true);
                    return;
                }
            }
        }
    }
}

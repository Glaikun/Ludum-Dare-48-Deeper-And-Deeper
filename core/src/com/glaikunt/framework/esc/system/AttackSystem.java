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
import com.glaikunt.framework.esc.component.player.PlayerComponent;
import com.glaikunt.framework.esc.component.player.WeaponComponent;

public class AttackSystem extends EntitySystem {

    private ImmutableArray<Entity> playerEntities, enemyEntities;

    private LevelComponent level;

    private ComponentMapper<PlayerComponent> playerCM = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<WeaponComponent> weaponCM = ComponentMapper.getFor(WeaponComponent.class);

    private ComponentMapper<DemonComponent> demonCM = ComponentMapper.getFor(DemonComponent.class);

    private ComponentMapper<HealthComponent> healthCM = ComponentMapper.getFor(HealthComponent.class);

    public AttackSystem(ApplicationResources applicationResources) {

        this.playerEntities = applicationResources.getEngine().getEntitiesFor(Family.all(
                PlayerComponent.class,
                WeaponComponent.class
        ).get());
        this.enemyEntities = applicationResources.getEngine().getEntitiesFor(Family.all(
                DemonComponent.class,
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
            PlayerComponent player = playerCM.get(playerEntity);
            WeaponComponent weapon = weaponCM.get(playerEntity);

            weapon.getWeaponType().getAttackSpeed().tick(delta);

            for (int ee = 0; ee < enemyEntities.size(); ++ee) {

                Entity enemyEntity = enemyEntities.get(ee);
                DemonComponent demon = demonCM.get(enemyEntity);
                HealthComponent demonHealth = healthCM.get(enemyEntity);

                if (demonHealth.getLerpWidth() > 0 && weapon.getWeaponType().getAttackSpeed().isTimerEventReady()) {
                    demonHealth.setLerpWidth(demonHealth.getLerpWidth() - weapon.getWeaponType().getDamage());
                }

                if (MathUtils.isEqual(demonHealth.getLerpWidth(), demonHealth.getDeltaHealth(), 1)) {
                    demonHealth.setDeltaHealth(MathUtils.lerp(demonHealth.getDeltaHealth(), demonHealth.getLerpWidth(), 5 * delta));
                }

                if (demonHealth.getLerpWidth() <= 0 && MathUtils.isEqual(demonHealth.getLerpWidth(), demonHealth.getDeltaHealth(), 1) ) {
                    level.setLevelComplete(true);
                }
            }
        }
    }
}

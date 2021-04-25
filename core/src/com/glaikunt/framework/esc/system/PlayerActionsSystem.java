package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Rectangle;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.demon.DemonComponent;
import com.glaikunt.framework.esc.component.game.LevelComponent;
import com.glaikunt.framework.esc.component.player.PlayerComponent;
import com.glaikunt.framework.esc.component.player.ValidateAttackComponent;
import com.glaikunt.framework.esc.component.player.WeaponComponent;
import com.glaikunt.framework.game.weapon.WeaponType;

public class PlayerActionsSystem extends EntitySystem {

    private ImmutableArray<Entity> playerEntity;
    private Entity demonEntity;

    private LevelComponent level;
    private Rectangle demonRect;
    private Rectangle leftRect;

    private ComponentMapper<PlayerComponent> playerCM = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<WeaponComponent> weaponCM = ComponentMapper.getFor(WeaponComponent.class);
    private ComponentMapper<ValidateAttackComponent> validateAttackCM = ComponentMapper.getFor(ValidateAttackComponent.class);

    private ComponentMapper<DemonComponent> demonCM = ComponentMapper.getFor(DemonComponent.class);

    private ComponentMapper<PositionComponent> posCM = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<SizeComponent> sizeCM = ComponentMapper.getFor(SizeComponent.class);

    public PlayerActionsSystem(ApplicationResources applicationResources) {
        this.playerEntity = applicationResources.getEngine().getEntitiesFor(Family.all(
                PlayerComponent.class,
                PositionComponent.class,
                SizeComponent.class,
                WeaponComponent.class,
                ValidateAttackComponent.class).get());

        this.demonEntity = applicationResources.getEngine().getEntitiesFor(Family.all(
                DemonComponent.class,
                PositionComponent.class,
                SizeComponent.class).get())
                .get(0);

        this.level = applicationResources.getGlobalEntity().getComponent(LevelComponent.class);

        this.leftRect = new Rectangle();
        this.demonRect = new Rectangle();
    }

    @Override
    public void update(float delta) {


        for (int pi = 0; pi < playerEntity.size(); ++pi) {

            Entity playerEntity = this.playerEntity.get(pi);
            PlayerComponent player = playerCM.get(playerEntity);
            PositionComponent playerPos = posCM.get(playerEntity);
            WeaponComponent playerWeapon = weaponCM.get(playerEntity);
            SizeComponent playerSize = sizeCM.get(playerEntity);
            ValidateAttackComponent playerValidationAttack = validateAttackCM.get(playerEntity);

            PositionComponent demonPos = posCM.get(demonEntity);
            SizeComponent demonSize = sizeCM.get(demonEntity);
            DemonComponent demon = demonCM.get(demonEntity);

            this.demonRect.set(demonPos.x, demonPos.y, demonSize.x, demonSize.y);

            int collisionSpace = 15;
            this.leftRect.set(demonPos.x - collisionSpace, demonPos.y, collisionSpace, demonSize.y);

            if (playerWeapon.getWeaponType().equals(WeaponType.MELEE)) {

                if (leftRect.contains(playerPos.x + (playerSize.x / 2), playerPos.y)) {
                    playerValidationAttack.setInRange(true);
                } else if (playerValidationAttack.isInRange()) {
                    playerValidationAttack.setInRange(false);
                }
            }

            //TODO need to check if nothing is in way of collision
            //TODO if something appears then need to hit that object
            //TODO can only fire when in range

            if (playerWeapon.getWeaponType().equals(WeaponType.RANGED)) {

                if (Intersector.intersectSegmentRectangle(playerValidationAttack.getCurrentPos(), playerValidationAttack.getTargetPos(), demonRect)) {
                    playerValidationAttack.setInRange(true);
                } else if (playerValidationAttack.isInRange()) {
                    playerValidationAttack.setInRange(false);
                }
            }


            if (!level.isLevelStarted() && !level.isLevelComplete() && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                level.setLevelStarted(true);
            }
        }
    }
}

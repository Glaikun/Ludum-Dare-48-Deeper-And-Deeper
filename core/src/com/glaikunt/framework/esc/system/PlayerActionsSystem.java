package com.glaikunt.framework.esc.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.game.LevelComponent;
import com.glaikunt.framework.esc.component.player.PlayerComponent;
import com.glaikunt.framework.esc.component.player.WeaponComponent;

public class PlayerActionsSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private LevelComponent level;

    private ComponentMapper<PlayerComponent> playerCM = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<WeaponComponent> weaponCM = ComponentMapper.getFor(WeaponComponent.class);
    private ComponentMapper<PositionComponent> posCM = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<SizeComponent> sizeCM = ComponentMapper.getFor(SizeComponent.class);

    public PlayerActionsSystem(ApplicationResources applicationResources) {
        this.entities = applicationResources.getEngine().getEntitiesFor(Family.all(
                PlayerComponent.class,
                PositionComponent.class,
                SizeComponent.class,
                WeaponComponent.class).get());
        this.level = applicationResources.getGlobalEntity().getComponent(LevelComponent.class);
    }

    @Override
    public void update(float delta) {

        for (int i = 0; i < entities.size(); ++i) {

            Entity entity = entities.get(i);
            PlayerComponent player = playerCM.get(entity);
            PositionComponent pos = posCM.get(entity);
            WeaponComponent weapon = weaponCM.get(entity);
            SizeComponent size = sizeCM.get(entity);

            if (!level.isLevelStarted() && !level.isLevelComplete() && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

                level.setLevelStarted(true);
            }
        }
    }
}

package com.glaikunt.framework.esc.component.player;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.esc.component.common.PositionComponent;
import com.glaikunt.framework.game.weapon.WeaponType;

public class GhostPlayerComponent implements Component {

    private PositionComponent pos;
    private WeaponType weapon;

    public PositionComponent getPos() {
        return pos;
    }

    public void setPos(PositionComponent pos) {
        this.pos = pos;
    }

    public WeaponType getWeapon() {
        return weapon;
    }

    public void setWeapon(WeaponType weapon) {
        this.weapon = weapon;
    }
}

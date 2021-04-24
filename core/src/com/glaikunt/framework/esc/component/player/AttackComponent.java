package com.glaikunt.framework.esc.component.player;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.application.TickTimer;

public class AttackComponent implements Component {

    private TickTimer attackSpeed;
    private float dmg;

    public TickTimer getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(TickTimer attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getDmg() {
        return dmg;
    }

    public void setDmg(float dmg) {
        this.dmg = dmg;
    }
}

package com.glaikunt.framework.game.weapon;

import com.glaikunt.framework.application.TickTimer;

public enum WeaponType {

    MELEE(.5f, new TickTimer(1f)),
    RANGED(.5f, new TickTimer(1f)),
    SUPPORT(.5f, new TickTimer(1f));

    private float damage;
    private TickTimer attackSpeed;

    WeaponType(float damage, TickTimer attackSpeed) {
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    public float getDamage() {
        return damage;
    }

    public TickTimer getAttackSpeed() {
        return attackSpeed;
    }
}

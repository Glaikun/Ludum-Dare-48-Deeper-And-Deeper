package com.glaikunt.framework.esc.component.game;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.application.TickTimer;
import com.glaikunt.framework.esc.component.player.GhostPlayerComponent;
import com.glaikunt.framework.game.weapon.WeaponType;

import java.util.LinkedList;
import java.util.List;

public class LevelComponent implements Component {

    private float stage = 1;

    private WeaponType weaponType = WeaponType.MELEE;

    private boolean levelStarted = false;
    private boolean levelComplete = false;

    private boolean gameOver = false;
    private TickTimer gameOverTimer = new TickTimer(3);

    private List<GhostPlayerComponent> ghostPlayers = new LinkedList<>();

    public boolean isLevelStarted() {
        return levelStarted;
    }

    public void setLevelStarted(boolean levelStarted) {
        this.levelStarted = levelStarted;
    }

    public boolean isLevelComplete() {
        return levelComplete;
    }

    public void setLevelComplete(boolean levelComplete) {
        this.levelComplete = levelComplete;
    }

    public List<GhostPlayerComponent> getGhostPlayers() {
        return ghostPlayers;
    }

    public float increateStage() {
        stage += 1;
        return stage;
    }

    public float getStage() {
        return stage;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public void setGhostPlayers(List<GhostPlayerComponent> ghostPlayers) {
        this.ghostPlayers = ghostPlayers;
    }

    public void setStage(float stage) {
        this.stage = stage;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public TickTimer getGameOverTimer() {
        return gameOverTimer;
    }
}

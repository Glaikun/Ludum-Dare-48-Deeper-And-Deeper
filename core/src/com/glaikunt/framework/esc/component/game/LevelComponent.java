package com.glaikunt.framework.esc.component.game;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.esc.component.player.GhostPlayerComponent;

import java.util.LinkedList;
import java.util.List;

public class LevelComponent implements Component {

    private boolean levelStarted = false;
    private boolean levelComplete = false;

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
}

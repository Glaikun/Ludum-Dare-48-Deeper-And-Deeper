package com.glaikunt.framework.esc.component.path;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.SnapshotArray;

public class PathComponent implements Component {

    private SnapshotArray<NodeComponent> path = new SnapshotArray<>();
    private boolean findPath;
    private Vector2 startPos, goalPos;
    private int xDirection, yDirection;
    private NodeComponent lastNode, goalNode;
    private boolean paused;

    public SnapshotArray<NodeComponent> getPath() {
        return path;
    }

    public void setPath(SnapshotArray<NodeComponent> path) {
        this.path = path;
        if (!path.isEmpty()) {
            this.goalNode = path.get(path.size - 1);
        }
    }

    public void clearPath() {
        if (goalNode != null && goalNode.isGoalTarget()) {
            goalNode.setGoalTarget(false);
        }

        if (!path.isEmpty()) {
            path.clear();
            goalNode = null;
        }
    }

    public boolean isFindPath() {
        return findPath;
    }

    public void setFindPath(boolean findPath) {
        this.findPath = findPath;
    }

    public Vector2 getStartPos() {
        return startPos;
    }

    public void setStartPos(Vector2 startPos) {
        this.startPos = startPos;
    }

    public Vector2 getGoalPos() {
        return goalPos;
    }

    public NodeComponent getLastNode() {
        return lastNode;
    }

    public void setLastNode(NodeComponent lastNode) {
        this.lastNode = lastNode;
    }

    public void setGoalPos(Vector2 goalPos) {
        this.goalPos = goalPos;
    }

    public int getxDirection() {
        return xDirection;
    }

    public void setxDirection(int xDirection) {
        this.xDirection = xDirection;
    }

    public int getyDirection() {
        return yDirection;
    }

    public void setyDirection(int yDirection) {
        this.yDirection = yDirection;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public NodeComponent getGoalNode() {
        return goalNode;
    }

    public int getDY(float fi, float si) {
        int targetY = (int) (fi / 16);
        int currentY = (int) (si / 16);

        return Math.abs(targetY - currentY);
    }

    public int getDX(float fx, float sx) {
        int targetX = (int) (fx / 16);
        int currentX = (int) (sx / 16);

        return Math.abs(targetX - currentX);
    }
}

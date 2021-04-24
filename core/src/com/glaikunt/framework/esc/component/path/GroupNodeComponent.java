package com.glaikunt.framework.esc.component.path;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.framework.application.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class GroupNodeComponent implements Component {

    private Map<Vector2, NodeComponent> nodes = new HashMap<>();

    public NodeComponent getNodeByPosition(float x, float y) {
        return nodes.get(new Vector2(x, y));
    }

    public NodeComponent getRandomNodeAroundRect(Rectangle rect) {
        NodeComponent node = null;
        for (int y = 0; y < rect.getHeight()/16; y++) {
            for (int x = 0; x < rect.getWidth()/16; x++) {

                float bossXPos = MathUtils.round((rect.getX()/16))*16;
                float bossYPos = MathUtils.round((rect.getY()/16))*16;

                if (y == 0 && x == 0) {
                    node = getNodeByPosition((bossXPos + (x * 16)) - 16, (bossYPos + (y * 16)));
                    if (node != null && node.getToken() == 0) {
                        return node;
                    }
                    node = getNodeByPosition((bossXPos + (x * 16)), (bossYPos + (y * 16)) - 16);
                    if (node != null && node.getToken() == 0) {
                        return node;
                    }

                } else if (y == 0 && x == 1) {
                    node = getNodeByPosition((bossXPos + (x * 16)) + 16, (bossYPos + (y * 16)));
                    if (node != null && node.getToken() == 0) {
                        return node;
                    }
                    node = getNodeByPosition((bossXPos + (x * 16)), (bossYPos + (y * 16)) - 16);
                    if (node != null && node.getToken() == 0) {
                        return node;
                    }

                } else if (y == 1 && x == 0) {
                    node = getNodeByPosition((bossXPos + (x * 16)) - 16, (bossYPos + (y * 16)));
                    if (node != null && node.getToken() == 0) {
                        return node;
                    }
                    node = getNodeByPosition((bossXPos + (x * 16)), (bossYPos + (y * 16)) + 16);
                    if (node != null && node.getToken() == 0) {
                        return node;
                    }

                } else if (y == 1 && x == 1) {
                    node = getNodeByPosition((bossXPos + (x * 16)) + 16, (bossYPos + (y * 16)));
                    if (node != null && node.getToken() == 0) {
                        return node;
                    }
                    node = getNodeByPosition((bossXPos + (x * 16)), (bossYPos + (y * 16)) + 16);
                    if (node != null && node.getToken() == 0) {
                        return node;
                    }
                }
            }
        }
        return node;
    }

    public NodeComponent getClosestNextNode(float xPos, float yPos) {
        NodeComponent nodeTarget = null;
        for (NodeComponent node : getNodes().values()) {
            if (node.getToken() != 0) continue;

            if (nodeTarget == null) {
                nodeTarget = node;
            }
            if (getDX(xPos, node.getX())+getDY(yPos, node.getY()) < getDX(xPos, nodeTarget.getX())+getDY(yPos, nodeTarget.getY())) {
                nodeTarget = node;
            }
        }
        return nodeTarget;
    }

    public NodeComponent getClosestNextNodeThatIsEmpty(float xPos, float yPos) {
        NodeComponent nodeTarget = null;
        for (NodeComponent node : getNodes().values()) {
            if (node.getToken() != 0) continue;

            if (nodeTarget == null) {
                nodeTarget = node;
            }
            if (getDX(xPos, node.getX())+getDY(yPos, node.getY()) < getDX(xPos, nodeTarget.getX())+getDY(yPos, nodeTarget.getY())) {
                nodeTarget = node;
            }
        }
        return nodeTarget;
    }

    private int getDY(float fi, float si) {
        int targetY = (int) (fi / 16);
        int currentY = (int) (si / 16);

        return Math.abs(targetY - currentY);
    }

    private int getDX(float fx, float sx) {
        int targetX = (int) (fx / 16);
        int currentX = (int) (sx / 16);

        return Math.abs(targetX - currentX);
    }

    public Map<Vector2, NodeComponent> getNodes() {
        return nodes;
    }
}

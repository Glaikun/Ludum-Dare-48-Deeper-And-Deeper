package com.glaikunt.framework.esc.component.path;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.ArrayList;
import java.util.List;

public class GeneralPathFindingComponent implements PathFindingComponent {

    private GroupNodeComponent nodes;

    public GeneralPathFindingComponent(GroupNodeComponent nodes) {
        this.nodes = nodes;
    }

    public SnapshotArray<NodeComponent> findPath(Vector2 startPos, Vector2 goalPos, int range) {
        NodeComponent startNode, goalNode;
        startNode = nodes.getNodeByPosition(startPos.x, startPos.y);
        if (startNode == null) {
            Gdx.app.error("INFO", "The start positions doesn't match any node ("
                    + "sx : " + startPos.x + " sy : " + startPos.y + " / "
                    + "gx : " + goalPos.x + " gy : " + goalPos.y
                    + ")");
            return new SnapshotArray<>();
        }
        goalNode = nodes.getNodeByPosition(goalPos.x, goalPos.y);
        goalNode = goalNode != null ? goalNode : nodes.getClosestNextNode(goalPos.x, goalPos.y);
        return findPath(startNode, goalNode, range);
    }

    public SnapshotArray<NodeComponent> findPath(NodeComponent startNode, NodeComponent goalNode, int range) {
        ObjectMap<NodeComponent, NodeComponent> cameFrom = new ObjectMap<>();
        SnapshotArray<NodeComponent> candidates = new SnapshotArray<>(true, nodes.getNodes().values().size());
        SnapshotArray<NodeComponent> openNodes = new SnapshotArray<>(true, nodes.getNodes().values().size());
        SnapshotArray<NodeComponent> closedNodes = new SnapshotArray<>(true, nodes.getNodes().values().size());

        if (startNode == null) {
            Gdx.app.error("INFO", "The start positions doesn't match any node");
            return new SnapshotArray<>();
        }

        if (goalNode.isDisabled() || goalNode.equals(startNode)) {
            Gdx.app.log("INFO", "GoalNode is disabled or the same as startNode");
            return new SnapshotArray<>();
        }

        startNode.setG_score(0);
        startNode.setF_score(getDistanceBetween(startNode, goalNode));
        openNodes.add(startNode);

        while (openNodes.size != 0) {
            openNodes.sort();
            NodeComponent current = openNodes.get(0);

            closedNodes.add(current);
            openNodes.removeValue(current, true);

            if (MathUtils.isEqual(current.getX(), goalNode.getX(), 1) && MathUtils.isEqual(current.getY(), goalNode.getY(), 1)) {
                if (startNode.isGoalTarget()) {
                    startNode.setGoalTarget(false);
                }
                goalNode.setGoalTarget(true);
                return reconstructPath(cameFrom, goalNode);
            } else if (current.getToken() == 0 && !current.isGoalTarget() && MathUtils.isEqual(current.getX(), goalNode.getX(), 32) && MathUtils.isEqual(current.getY(), goalNode.getY(), 32)) {
                candidates.add(current);
            }

            for (NodeComponent neighbor : getAdjacentBlocks(current)) {

                if (closedNodes.contains(neighbor, true)) {
                    continue;
                }

                int tentative_g_score = current.getG_score() + getDistanceBetween(current, neighbor);
                if (!openNodes.contains(neighbor, true)) {
                    openNodes.add(neighbor);
                } else if (tentative_g_score >= neighbor.getG_score()) {
                    continue;
                }

                cameFrom.put(neighbor, current);
                neighbor.setG_score(tentative_g_score);
                neighbor.setF_score(neighbor.getG_score() + getDistanceBetween(neighbor, goalNode));
            }
        }

        if (candidates.isEmpty()) {
            return new SnapshotArray<>();
        }

        candidates.sort();
        if (startNode.isGoalTarget()) {
            startNode.setGoalTarget(false);
        }
        NodeComponent candidate = candidates.get(0);
        candidate.setGoalTarget(true);
        return reconstructPath(cameFrom, candidate);
    }

    private SnapshotArray<NodeComponent> reconstructPath(ObjectMap<NodeComponent, NodeComponent> cameFrom, NodeComponent goalNode) {
        SnapshotArray<NodeComponent> totalPath = new SnapshotArray<>(true, cameFrom.size);
        totalPath.add(goalNode);
        while (cameFrom.containsKey(goalNode)) {
            goalNode = cameFrom.get(goalNode);
            totalPath.add(goalNode);
        }
        totalPath.reverse();
        return totalPath;
    }

    private List<NodeComponent> getAdjacentBlocks(NodeComponent current) {
        List<NodeComponent> bs = new ArrayList<>();
        Vector2 vector = new Vector2(0, 0);
        vector.set(current.getX() + current.getWidth(), current.getY());
        addAdjacentBlock(bs, vector);
        vector.set(current.getX() - current.getWidth(), current.getY());
        addAdjacentBlock(bs, vector);
        vector.set(current.getX(), current.getY() + current.getHeight());
        addAdjacentBlock(bs, vector);
        vector.set(current.getX(), current.getY() - current.getHeight());
        addAdjacentBlock(bs, vector);

//        vector.set(current.getX() + current.getWidth(), current.getY() - current.getHeight());
//        addAdjacentBlock(bs, vector);
//        vector.set(current.getX() - current.getWidth(), current.getY() - current.getHeight());
//        addAdjacentBlock(bs, vector);
//        vector.set(current.getX() + current.getWidth(), current.getY() + current.getHeight());
//        addAdjacentBlock(bs, vector);
//        vector.set(current.getX() - current.getWidth(), current.getY() + current.getHeight());
//        addAdjacentBlock(bs, vector);
        return bs;
    }

    private void addAdjacentBlock(List<NodeComponent> bs, Vector2 vector) {
        if (nodes.getNodes().containsKey(vector)) {
            bs.add(nodes.getNodes().get(vector));
        }
    }

    private int getDistanceBetween(NodeComponent current, NodeComponent neighbor) {
        return Math.abs(getDX(current, neighbor)) + Math.abs(getDY(current, neighbor));
    }

    private int getDY(NodeComponent current, NodeComponent neighbor) {
        int targetY = (int) (neighbor.getY() / neighbor.getHeight());
        int currentY = (int) (current.getY() / current.getHeight());

        return targetY - currentY;
    }

    private int getDX(NodeComponent current, NodeComponent neighbor) {
        int targetX = (int) (neighbor.getX() / neighbor.getWidth());
        int currentX = (int) (current.getX() / current.getWidth());

        return targetX - currentX;
    }

    public GroupNodeComponent getNodes() {
        return nodes;
    }
}

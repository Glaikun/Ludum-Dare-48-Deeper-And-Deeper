package com.glaikunt.framework.esc.component.path;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.SnapshotArray;

public interface PathFindingComponent extends Component {

    SnapshotArray<NodeComponent> findPath(Vector2 startPos, Vector2 goalPos, int range);
    SnapshotArray<NodeComponent> findPath(NodeComponent startNode, NodeComponent goalNode, int range);

    GroupNodeComponent getNodes();
}

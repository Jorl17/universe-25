package universe25.GameLogic.Movement.Pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import universe25.World.GridLayers.GridMapLayer;

/**
 * Created by jorl17 on 13/08/15.
 */
public class GridMapLayerIndexedGraph implements IndexedGraph<GridCell> {
    private final GridMapLayer layer;

    public GridMapLayerIndexedGraph(GridMapLayer layer) {
        this.layer = layer;
    }

    @Override
    public int getNodeCount() {
        return layer.getNumCols()*layer.getNumRows();
    }

    @Override
    public Array<Connection<GridCell>> getConnections(GridCell fromNode) {
        return fromNode.getConnections();
    }
}

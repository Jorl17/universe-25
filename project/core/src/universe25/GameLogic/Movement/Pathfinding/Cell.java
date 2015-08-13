package universe25.GameLogic.Movement.Pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import universe25.Worlds.GridLayers.GridMapLayer;

/**
 * Created by jorl17 on 13/08/15.
 */
public class Cell implements IndexedNode<Cell> {
    private Array<Connection<Cell>> connections;
    private GridMapLayer layer;
    private int col, row;
    private int index;

    public Cell(GridMapLayer layer, int col, int row) {
        this.layer = layer;
        this.col = col;
        this.row = row;
        this.index = layer.getIndex(col, row);
        this.connections = null;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Array<Connection<Cell>> getConnections() {
        if ( this.connections == null ) {
            Cell[][] graphCells = layer.getGraphCells();
            this.connections = new Array<>();
            if (getRow() > 0)
                connections.add(new CellConnection(this, graphCells [this.getRow() - 1] [this.getCol()]));
            if (getCol() > 0)
                connections.add(new CellConnection(this, graphCells [this.getRow()] [this.getCol() - 1]));
            if (getCol() < layer.getNumCols() - 1)
                connections.add(new CellConnection(this, graphCells [this.getRow()] [this.getCol() + 1]));
            if (getRow() < layer.getNumRows() - 1)
                connections.add(new CellConnection(this, graphCells [this.getRow() + 1] [this.getCol()]));
        }
        return connections;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public GridMapLayer getLayer() {
        return layer;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof Cell )
            return ((Cell) obj).getCol() == getCol() && ((Cell) obj).getRow() == getRow();
        else
            return super.equals(obj);
    }

    //FIXME more stuff
}

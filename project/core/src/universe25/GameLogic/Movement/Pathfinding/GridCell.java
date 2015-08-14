package universe25.GameLogic.Movement.Pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import universe25.Worlds.GridLayers.GridMapLayer;

/**
 * Created by jorl17 on 13/08/15.
 */
public class GridCell implements IndexedNode<GridCell> {
    private Array<Connection<GridCell>> connections;
    private GridMapLayer layer;
    private int col, row;
    private int index;

    public GridCell(GridMapLayer layer, int col, int row) {
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
    public Array<Connection<GridCell>> getConnections() {
        if ( this.connections == null ) {
            GridCell[][] graphCells = layer.getGraphCells();
            this.connections = new Array<>();
            if (getRow() > 0) {
                GridCell c = graphCells [this.getRow() - 1] [this.getCol()];
                float cost = layer.getMoveCost(c);
                if ( cost > 0 )
                connections.add(new CellConnection(this, c, cost));
            }
            if (getCol() > 0) {
                GridCell c = graphCells [this.getRow()] [this.getCol() - 1];
                float cost = layer.getMoveCost(c);
                if ( cost > 0 )
                    connections.add(new CellConnection(this, c, cost));
            }
            if (getCol() < layer.getNumCols() - 1) {
                GridCell c = graphCells [this.getRow()] [this.getCol() + 1];
                float cost = layer.getMoveCost(c);
                if ( cost > 0 )
                    connections.add(new CellConnection(this, c, cost));
            }
            if (getRow() < layer.getNumRows() - 1) {
                GridCell c = graphCells [this.getRow() + 1] [this.getCol()];
                float cost = layer.getMoveCost(c);
                if ( cost > 0 )
                    connections.add(new CellConnection(this, c, cost));
            }

            float diagonalCostDifference = -(float) (1 - Math.sqrt(2));
            if (getRow() > 0 && getCol() > 0) {
                GridCell c = graphCells [this.getRow() - 1] [this.getCol() -1];
                float cost = layer.getMoveCost(c);
                if ( cost > 0 )
                    connections.add(new CellConnection(this, c, cost + diagonalCostDifference));
            }

            if (getRow() < layer.getNumRows() - 1 && getCol() < layer.getNumCols() - 1) {
                GridCell c = graphCells [this.getRow() + 1] [this.getCol() + 1];
                float cost = layer.getMoveCost(c);
                if ( cost > 0 )
                    connections.add(new CellConnection(this, c, cost + diagonalCostDifference));
            }

            if (getRow() > 0 && getCol() > layer.getNumCols() - 1) {
                GridCell c = graphCells [this.getRow() - 1] [this.getCol() + 1];
                float cost = layer.getMoveCost(c);
                if ( cost > 0 )
                    connections.add(new CellConnection(this, c, cost + diagonalCostDifference));
            }

            if (getRow() < layer.getNumRows() - 1 && getCol() > 0) {
                GridCell c = graphCells [this.getRow() + 1] [this.getCol() - 1];
                float cost = layer.getMoveCost(c);
                if ( cost > 0 )
                    connections.add(new CellConnection(this, c, cost + diagonalCostDifference));
            }

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
        if ( obj instanceof GridCell)
            return ((GridCell) obj).getCol() == getCol() && ((GridCell) obj).getRow() == getRow();
        else
            return super.equals(obj);
    }

    public Vector2 getCentre() {
        return layer.getCellCentre(this);
    }

    public boolean isPointInCell(Vector2 pos) {
        return layer.isPointInCell(pos, this);
    }

    public void invalidateConnections() {
        this.connections = null;
    }

    //FIXME more stuff
}

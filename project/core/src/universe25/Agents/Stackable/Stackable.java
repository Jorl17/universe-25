package universe25.Agents.Stackable;

import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.World.GridLayers.StackablesLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 17/08/15.
 */
public abstract class Stackable {
    private StackablesLayer layer;
    private ArrayList<GridCell> cells;


    public Stackable(StackablesLayer layer, ArrayList<GridCell> cells) {
        this.cells = new ArrayList<>();
        if ( cells != null )
            this.cells.addAll(cells);
        this.layer = layer;
    }

    public void addCell(int col, int row) {
        cells.add(layer.getCellAt(col, row));
    }

    public void addCell(GridCell cell) {
        cells.add(cell);
    }

    public ArrayList<GridCell> getCells() {
        return cells;
    }

    public StackablesLayer getLayer() {
        return layer;
    }

    public void putInLayer(float densityPerCell) {
        for ( GridCell cell : getCells() )
            getLayer().putStackable(cell, this, densityPerCell);
    }

    public abstract void onStackEnded(GridCell cell);
}

package universe25.GameLogic.Movement.MoveSequence;

import com.badlogic.gdx.math.Vector2;
import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.Worlds.GridLayers.GridMapLayer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 11/08/15.
 */
public class GridMoveSequence extends MoveSequence {
    private ArrayList<GridCell> cells; // [0]-> row, [1]-> col
    private GridMapLayer grid;

    public GridMoveSequence() {
        this.cells = new ArrayList<>();
    }

    public GridMoveSequence(GridMapLayer grid) {
        this.cells = new ArrayList<>();
        this.grid = grid;
    }

    public GridMoveSequence(GridMapLayer grid, ArrayList<GridCell> cells ) {
        super();
        this.cells = new ArrayList<>();
        this.cells.addAll(cells);
        this.grid = grid;
        for ( GridCell cell : cells )
            addMove(cell.getCentre());
    }

    public void addMove(GridCell cell) {
        addMove(cell.getCentre());
        this.cells.add(cell);
    }

    public ArrayList<GridCell> getCells() {
        return cells;
    }

    public GridCell getLastCell() {
        if ( cells.isEmpty() ) return null;
        return cells.get(cells.size()-1);
    }

    @Override
    public MoveSequence cpy() {
        return new GridMoveSequence(grid, cells);
    }

    @Override
    public GridMoveSequence reverse() {
        super.reverse();
        Collections.reverse(cells);
        return this;
    }

    public void setGrid(GridMapLayer grid) {
        this.grid = grid;
    }

    protected GridMapLayer getGrid() {
        return grid;
    }

    public boolean containsMove(GridCell agentCell) {
        //FIXME: THIS IS SO GOD DAMNED SLOW FOR SURE
        /*for ( GridCell cell : cells )
            if ( cell.equals(agentCell))
                return true;

        return false;*/

        //FIXME Never forget that this forces us to always fetch cells from the same GridLayerMap!! References!
        return cells.contains(agentCell);
    }
}

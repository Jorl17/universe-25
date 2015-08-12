package universe25.GameLogic.Movement.MoveSequence;

import com.badlogic.gdx.math.Vector2;
import universe25.Worlds.GridLayers.GridMapLayer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 11/08/15.
 */
public class GridMoveSequence extends MoveSequence {
    private ArrayList<int[]> cells; // [0]-> row, [1]-> col
    private GridMapLayer grid;

    public GridMoveSequence() {
        this.cells = new ArrayList<>();
    }

    public GridMoveSequence(GridMapLayer grid) {
        this.cells = new ArrayList<>();
        this.grid = grid;
    }

    public GridMoveSequence(GridMapLayer grid, ArrayList<int[]> cells ) {
        super();
        this.cells = new ArrayList<>();
        this.cells.addAll(cells);
        this.grid = grid;
        for ( int[] cell : cells )
            addMove(grid.getCellCentre(cell[1], cell[0]));
    }

    public void addMove(int[] cell) {
        addMove(grid.getCellCentre(cell[1], cell[0]));
    }

    public ArrayList<int[]> getCells() {
        return cells;
    }

    public int[] getLastCell() {
        if ( cells.isEmpty() ) return null;
        return cells.get(cells.size()-1);
    }

    @Override
    public void reverse() {
        super.reverse();
        Collections.reverse(cells);
    }

    public void setGrid(GridMapLayer grid) {
        this.grid = grid;
    }
}

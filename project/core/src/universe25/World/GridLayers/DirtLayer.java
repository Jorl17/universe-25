package universe25.World.GridLayers;

import com.badlogic.gdx.graphics.Color;
import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.Objects.Dirt;

import java.util.ArrayList;

/**
 * Created by jorl17 on 25/08/15.
 */
public class DirtLayer extends FloatLayer {
    public DirtLayer(float gridWidth, float gridHeight, float cellSize, String name, float maxDirt, Color drawColor) {
        super(gridWidth, gridHeight, cellSize, name, maxDirt, drawColor);
        this.nextCells = this.cells;
        fillWithDirt();
    }

    public DirtLayer(float cellSize, int nRows, int nCols, String name, float maxDirt, Color drawColor) {
        super(cellSize, nRows, nCols, name, maxDirt, drawColor);
        this.nextCells = this.cells;
        fillWithDirt();
    }

    private void fillWithDirt() {
        float halfDirt = getMaxDirt()/2;
        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                cells[i][j] = halfDirt;
    }

    public Dirt takeDirtFromCellAt(int col, int row, float maxToTake) {
        float val = getValueAtCell(col, row);
        float rem = val - maxToTake;

        if ( rem > 0 ) {
            setValueAtCell(col, row, rem);
            return new Dirt(maxToTake);
        } else {
            setValueAtCell(col, row, 0.0f);
            return new Dirt(val);
        }
    }

    public void spreadDirtAtCell(GridCell cell, GridCell spreadSource, float percentToSpread) {
        float valToSpread = getValueAtCell(cell) * percentToSpread;

        ArrayList<GridCell> cells = new ArrayList<>();
        GridCell tmp;
        for ( int i = -1; i <= 1; i++ )
            for ( int j = -1; j <= 1; j++ )
                if ( ((tmp= getCellAt(cell.getCol() + i, cell.getRow() + j)) != null)
                    && !tmp.equals(spreadSource)
                    && !tmp.equals(cell) )
                    cells.add(tmp);

        if ( cells.isEmpty() ) return;

        float amountPerNeighbourCell = valToSpread / cells.size();

        for ( GridCell c : cells )
            increaseValueAtCell(c, amountPerNeighbourCell);

        decreaseValueAtCell(cell, valToSpread);
    }

    public Dirt takeDirtFromCellAt(GridCell cell, float maxToTake) {
        return takeDirtFrom(cell.getCol(), cell.getRow(), maxToTake);
    }

    public Dirt takeDirtFrom(float x, float y, float maxToTake) {
        return takeDirtFromCellAt((int) (y / cellSize), (int) (x / cellSize), maxToTake);
    }

    public float getMaxDirt() { return getMaxValue(); }
}

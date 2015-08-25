package universe25.World.GridLayers;

import com.badlogic.gdx.graphics.Color;
import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.Objects.Dirt;

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

    public Dirt takeDirtFromCellAt(GridCell cell, float maxToTake) {
        return takeDirtFrom(cell.getCol(), cell.getRow(), maxToTake);
    }

    public Dirt takeDirtFrom(float x, float y, float maxToTake) {
        return takeDirtFromCellAt((int) (y / cellSize), (int) (x / cellSize), maxToTake);
    }

    public float getMaxDirt() { return getMaxValue(); }
}

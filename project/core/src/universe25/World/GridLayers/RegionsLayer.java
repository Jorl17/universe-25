package universe25.World.GridLayers;

import com.badlogic.gdx.graphics.Color;
import universe25.Agents.Regions.Region;
import universe25.GameLogic.Movement.Pathfinding.GridCell;

import java.util.ArrayList;

/**
 * Created by jorl17 on 17/08/15.
 */
public class RegionsLayer extends GridMapLayer<Region> {

    public RegionsLayer(float gridWidth, float gridHeight, float cellSize, String name, Color drawColor, boolean drawLayer) {
        super(Region.class, gridWidth, gridHeight, cellSize, name, drawColor, drawLayer);
    }

    public RegionsLayer(float cellSize, int nRows, int nCols, String name) {
        super(Region.class, cellSize, nRows, nCols, name);
    }

    public boolean hasRegion(GridCell cell) {
        Region region = getValueAtCell(cell);
        return region != null;
    }

    public ArrayList<GridCell> getRegionCellsAtCell(int col, int row) {
        return getValueAtCell(col, row).getCells();
    }

    public ArrayList<GridCell> getRegionCellsAtCell(GridCell cell) {
        return getValueAtCell(cell).getCells();
    }

    public ArrayList<GridCell> getRegionCellsAt(float x, float y) {
        return getValueAt(x, y).getCells();
    }

    @Override
    public float getMoveCost(int col, int row) {
        return 1; //FIXME: Will depend on region and agent, in practice
    }
}

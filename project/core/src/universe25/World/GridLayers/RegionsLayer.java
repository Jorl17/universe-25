package universe25.World.GridLayers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import universe25.Agents.NoSpecies;
import universe25.Agents.Regions.Region;
import universe25.GameLogic.Movement.Pathfinding.GridCell;

import java.util.ArrayList;

/**
 * Created by jorl17 on 17/08/15.
 */
public class RegionsLayer extends GridMapLayer<Region> {
    private Region freeRegion;

    public RegionsLayer(float gridWidth, float gridHeight, float cellSize, String name, Color drawColor, boolean drawLayer) {
        super(Region.class, gridWidth, gridHeight, cellSize, name, drawColor, drawLayer);
        this.nextCells = this.cells;
        this.freeRegion = new Region(this, new NoSpecies());
        freeRegion.markAsFreeRegion();
        markAllAsFree();
        getDrawColor().a = 0.25f;
    }

    public RegionsLayer(float cellSize, int nRows, int nCols, String name, Region initialRegion) {
        super(Region.class, cellSize, nRows, nCols, name);
        this.nextCells = this.cells;
        this.freeRegion = initialRegion;
        markAllAsFree();
        getDrawColor().a = 0.25f;
    }

    public void markAllAsFree() {
        freeRegion.markAsFreeRegion();
        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++) {
                //cells[i][j] = freeRegion; <-- This is done in addCell()
                freeRegion.addCell(getCellAt(j, i));
            }
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

    public void putRegionAt(GridCell cell, Region r) {
        setValueAtCell(cell, r);
    }

    public void putRegionAt(int col, int row, Region r) {
        setValueAtCell(col, row, r);
    }

    @Override
    public float getMoveCost(int col, int row) {
        return 1; //FIXME: Will depend on region and agent, in practice
    }

    @Override
    protected void drawCellBody(Batch batch, int col, int row) {
        Region region = getValueAtCell(col, row);
        if ( !region.isEmptyRegion() ) {
            Color c = getDrawColor();

            getShapeRenderer().setColor(c);
            getShapeRenderer().rect(col * cellSize, row * cellSize, cellSize, cellSize);
        }
    }

    @Override
    protected void drawCellGrid(Batch batch, int col, int row) {
        // Draw nothing
    }
}

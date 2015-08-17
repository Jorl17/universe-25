package universe25.Agents.Regions;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.SimplisticAnt.SimplisticAnt;
import universe25.Agents.Species;
import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.World.GridLayers.RegionsLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 17/08/15.
 */
public class Hive<S extends Species> extends Region {
    private int startCol, startRow, width, height;

    public Hive(RegionsLayer regionsLayer, S regionSpecies, int startCol, int startRow, int width, int height) {
        super(regionsLayer, regionSpecies, regionsLayer.getGridCellsRectangle(startCol, startRow, width, height));
        this.startCol = startCol;
        this.startRow = startRow;
        this.width = width;
        this.height = height;
    }

    public Hive(RegionsLayer regionsLayer, S regionSpecies, int width, int height) {
        super(regionsLayer, regionSpecies, null);
        int startCol = (int) (Math.random() * (regionsLayer.getNumCols()-2*width) + width);
        int startRow = (int) (Math.random() * (regionsLayer.getNumRows()-2*height) + height);
        addCells(regionsLayer.getGridCellsRectangle(startCol, startRow, width, height));
        this.startCol = startCol;
        this.startRow = startRow;
        this.width = width;
        this.height = height;
    }

    public Vector2 getHiveCenter() {
        int endCol = startCol + width;
        int endRow = startRow + height;
        return getRegionsLayer().getCellCentre(endCol, endRow).add(getRegionsLayer().getCellCentre(startCol, startRow)).scl(0.5f);
    }
}

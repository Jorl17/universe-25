package universe25.Agents.Regions;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Species;
import universe25.Utils.RandomUtils;
import universe25.World.GridLayers.RegionsLayer;

/**
 * Created by jorl17 on 17/08/15.
 */
public class Hive<S extends Species> extends Region {
    private int startCol, startRow, width, height;
    private FoodStackSubRegion foodStackRegion;

    public Hive(RegionsLayer regionsLayer, S regionSpecies, int startCol, int startRow, int width, int height) {
        super(regionsLayer, regionSpecies, regionsLayer.getGridCellsRectangle(startCol, startRow, width, height));
        this.startCol = startCol;
        this.startRow = startRow;
        this.width = width;
        this.height = height;
        this.foodStackRegion = new FoodStackSubRegion(this);
        foodStackRegion.randomlyExpand();
    }

    public Hive(RegionsLayer regionsLayer, S regionSpecies, int width, int height) {
        super(regionsLayer, regionSpecies, null);
        int startCol = RandomUtils.rand(width, regionsLayer.getNumCols() - width);
        int startRow = RandomUtils.rand(height, regionsLayer.getNumRows() - height);
        addCells(regionsLayer.getGridCellsRectangle(startCol, startRow, width, height));
        this.startCol = startCol;
        this.startRow = startRow;
        this.width = width;
        this.height = height;
        this.foodStackRegion = new FoodStackSubRegion(this);
        foodStackRegion.randomlyExpand();
    }

    public Vector2 getCenter() {
        int endCol = startCol + width;
        int endRow = startRow + height;
        return getRegionsLayer().getCellCentre(endCol, endRow).add(getRegionsLayer().getCellCentre(startCol, startRow)).scl(0.5f);
    }

    public FoodStackSubRegion getFoodStackRegion() {
        return foodStackRegion;
    }
}

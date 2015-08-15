package universe25.Food;

import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.World.GridLayers.FoodLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 14/08/15.
 */
public class Food {
    private float initialDensityPerCell;
    private FoodLayer layer;
    private ArrayList<GridCell> cells;

    public Food(float initialDensityPerCell, FoodLayer layer, ArrayList<GridCell> cells) {
        this.initialDensityPerCell = initialDensityPerCell;
        this.layer = layer;
        this.cells = new ArrayList<>();
        this.cells.addAll(cells);
    }

    public void onFoodEnded(GridCell cell) {
        cells.remove(cell);
        // FIXME: Prolly do other stuff
    }

    public void putInLayer() {
        for ( GridCell cell : cells )
            layer.putFoodSource(cell, this, initialDensityPerCell);
    }
}

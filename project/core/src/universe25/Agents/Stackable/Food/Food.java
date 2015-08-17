package universe25.Agents.Stackable.Food;

import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.Agents.Stackable.Stackable;
import universe25.World.GridLayers.StackablesLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 14/08/15.
 */
public class Food extends Stackable {
    private float initialDensityPerCell;

    public Food(float initialDensityPerCell, StackablesLayer layer, ArrayList<GridCell> cells) {
        super(layer, cells);
        this.initialDensityPerCell = initialDensityPerCell;
    }

    public void putInLayer() {
        putInLayer(initialDensityPerCell);
    }

    @Override
    public void onStackEnded(GridCell cell) {
        //FIXME
    }
}

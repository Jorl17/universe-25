package universe25.Agents.Stackable.Food;

import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.World.GridLayers.StackablesLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 20/08/15.
 */
public class FoodDeposit extends Food {
    public FoodDeposit(StackablesLayer layer, ArrayList<GridCell> cells) {
        super(-1/* don't care */, layer, cells);
    }
}

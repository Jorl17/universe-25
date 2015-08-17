package universe25.Agents.Stackable.Food;

import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.World.GridLayers.StackablesLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 15/08/15.
 */
public class Bread extends Food {
    public Bread(StackablesLayer layer, ArrayList<GridCell> cells) {
        super(100, layer, cells);
    }
}

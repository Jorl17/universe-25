package universe25.Food;

import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.World.GridLayers.FoodLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 15/08/15.
 */
public class AntPoison extends Food {
    public AntPoison( FoodLayer layer, ArrayList<GridCell> cells) {
        super(50, layer, cells);
    }
}

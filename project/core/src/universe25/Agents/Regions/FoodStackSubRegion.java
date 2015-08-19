package universe25.Agents.Regions;

import universe25.GameLogic.Movement.Pathfinding.GridCell;

/**
 * Created by jorl17 on 17/08/15.
 */
public class FoodStackSubRegion extends SubRegion {
    public FoodStackSubRegion(Region parentRegion) {
        super(parentRegion);
    }

    public GridCell getCurrentFoodStackCell() {
        //FIXME: Testing only, completely hacked in
        return getCells().get(0);
    }
}

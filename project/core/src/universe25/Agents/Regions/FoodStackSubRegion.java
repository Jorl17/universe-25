package universe25.Agents.Regions;

import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.Objects.FoodBits;
import universe25.Objects.WorldObject;
import universe25.World.GridLayers.StackablesLayer;
import universe25.World.World;

import java.util.ArrayList;

/**
 * Created by jorl17 on 17/08/15.
 */
public class FoodStackSubRegion extends SubRegion {
    private World world;
    private ArrayList<ArrayList<FoodBits>> foodBits; //FIXME: This should be temporary
    private StackablesLayer stackablesLayer;
    public FoodStackSubRegion(World world, Region parentRegion, StackablesLayer stackablesLayer) {
        super(parentRegion);
        this.world = world;
        foodBits = new ArrayList<>();
        this.stackablesLayer = stackablesLayer;
    }

    @Override
    public boolean randomlyExpand() {
        boolean ret = super.randomlyExpand();
        if ( ret )
            foodBits.add(new ArrayList<>());

        return ret;
    }

    public boolean stack (FoodBits bit) {
        //assert ( getCurrentFoodStackCell().isPointInCell(bit.getPosition()) );
        FoodBits cpy = bit.cpy();
        world.addActor(cpy);
        foodBits.get(getCurrentFoodStackCellIndex()).add(cpy);

        // FIXME: do isFull() and all that jiggerjabber
        bit.setFoodAmount(stackablesLayer.putBitsAtCell(getCurrentFoodStackCell(), bit));

        //FIXME: set the right amount of good in cpy (it currently has the same as bit did)

        return bit.getFoodAmount() > 0;
    }

    protected int getCurrentFoodStackCellIndex() {
        //FIXME: Testing only, completely hacked in
        return 0;
    }

    public GridCell getCurrentFoodStackCell() {
        //FIXME: Testing only, completely hacked in
        return getCells().get(getCurrentFoodStackCellIndex());
    }
}

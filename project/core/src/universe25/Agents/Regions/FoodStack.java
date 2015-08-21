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
public class FoodStack extends SubRegion {
    private World world;
    private ArrayList<ArrayList<FoodBits>> foodBits; //FIXME: This should be temporary
    private StackablesLayer stackablesLayer;
    private int currentDeposit;
    public FoodStack(World world, Region parentRegion, StackablesLayer stackablesLayer) {
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

        currentDeposit = getCells().size()-1;
        return ret;
    }

    public boolean stack (FoodBits bit) {
        //assert ( getCurrentFoodStackCell().isPointInCell(bit.getPosition()) );
        FoodBits cpy = bit.cpy();
        world.addActor(cpy);
        foodBits.get(getCurrentFoodStackCellIndex()).add(cpy);


        float remaining = stackablesLayer.putBitsAtCell(getCurrentFoodStackCell(), bit);
        bit.setFoodAmount(remaining);

        if ( currentFoodStackCellIsFull() )
            randomlyExpand();

        return remaining == 0;
    }

    private boolean currentFoodStackCellIsFull() {
        return stackablesLayer.isFull(getCurrentFoodStackCell());
    }

    protected int getCurrentFoodStackCellIndex() {
        return currentDeposit;
    }

    public GridCell getCurrentFoodStackCell() {
        return getCells().get(getCurrentFoodStackCellIndex());
    }
}

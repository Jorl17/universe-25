package universe25.Agents.States.SimplisticAntStates;

import universe25.Agents.SimplisticAnt.SimplisticAnt;
import universe25.Agents.States.GoToCell;
import universe25.Agents.ValuePositionPair;
import universe25.Food.FoodQuantityPair;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public class GoToFood extends GoToCell<SimplisticAnt> {
    public GoToFood(SimplisticAnt agent, int priority) {
        super(agent, priority, "GoToFood");
    }

    @Override
    protected boolean areThereCellsWithValues() {
        return agent.areThereCellsWithFood();
    }

    @Override
    protected ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithValues() {
        ArrayList<ValuePositionPair<FoodQuantityPair>> centerOfCellsInFieldOfViewWithFood = agent.getCenterOfCellsInFieldOfViewWithFood();

        // Need to convert the array above into an array with just the densities (floats)
        ArrayList<ValuePositionPair<Float>> ret = new ArrayList<>();
        for ( ValuePositionPair<FoodQuantityPair> pair : centerOfCellsInFieldOfViewWithFood )
            ret.add ( new ValuePositionPair<>(pair.getValue().getAmount(), pair.getPosition()) );

        return ret;
    }
}

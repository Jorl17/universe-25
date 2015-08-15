package universe25.Agents.States.SimplisticAntStates;

import sun.management.resources.agent;
import universe25.Agents.SimplisticAnt.SimplisticAnt;
import universe25.Agents.States.AvoidCells;
import universe25.Agents.States.GoToCells;
import universe25.Agents.ValuePositionPair;
import universe25.Food.FoodQuantityPair;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public class GoToFood extends GoToCells<SimplisticAnt> {
    public GoToFood(SimplisticAnt agent, int priority) {
        super(agent, priority, "GoToFood");
    }

    @Override
    protected boolean areThereCellsToGoTo() {
        return agent.areThereCellsWithFood();
    }

    @Override
    protected ArrayList<ValuePositionPair<Float>> getCenterOfCellsToGoTo() {
        ArrayList<ValuePositionPair<FoodQuantityPair>> centerOfCellsInFieldOfViewWithFood = agent.getCenterOfCellsInFieldOfViewWithFood();

        // Need to convert the array above into an array with just the densities (floats)
        ArrayList<ValuePositionPair<Float>> ret = new ArrayList<>();
        for ( ValuePositionPair<FoodQuantityPair> pair : centerOfCellsInFieldOfViewWithFood )
            ret.add ( new ValuePositionPair<>(pair.getValue().getAmount(), pair.getPosition()) );

        return ret;
    }
}

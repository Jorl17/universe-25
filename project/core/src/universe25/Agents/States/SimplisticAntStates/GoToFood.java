package universe25.Agents.States.SimplisticAntStates;

import universe25.Agents.SimplisticAnt.SimplisticAnt;
import universe25.Agents.Stackable.Food.StackableSourceQuantityPair;
import universe25.Agents.States.GoToCells;
import universe25.Agents.ValuePositionPair;
import universe25.Agents.Stackable.Food.AntPoison;

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
        ArrayList<ValuePositionPair<StackableSourceQuantityPair>> centerOfCellsInFieldOfViewWithFood = agent.getCenterOfCellsInFieldOfViewWithFood();

        // Need to convert the array above into an array with just the densities (floats)
        // Also exclude AntPoison
        ArrayList<ValuePositionPair<Float>> ret = new ArrayList<>();
        for ( ValuePositionPair<StackableSourceQuantityPair> pair : centerOfCellsInFieldOfViewWithFood )
        if ( ! (pair.getValue().getSource() instanceof AntPoison))
            ret.add ( new ValuePositionPair<>(pair.getValue().getAmount(), pair.getPosition()) );

        return ret;
    }
}

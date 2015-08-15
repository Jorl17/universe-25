package universe25.Agents.States.SimplisticAntStates;

import universe25.Agents.Agent;
import universe25.Agents.SimplisticAnt.SimplisticAnt;
import universe25.Agents.States.AvoidCells;
import universe25.Agents.ValuePositionPair;
import universe25.Food.AntPoison;
import universe25.Food.FoodQuantityPair;

import java.util.ArrayList;

/**
 * Created by jorl17 on 15/08/15.
 */
public class AvoidAntPoison extends AvoidCells<SimplisticAnt> {
    public AvoidAntPoison(SimplisticAnt agent, int priority, float runAwayFactor) {
        super(agent, priority, "AvoidAntPoison", runAwayFactor);
    }

    public AvoidAntPoison(SimplisticAnt agent, int priority) {
        super(agent, priority, "AvoidAntPoison");
    }

    @Override
    protected boolean areThereCellsToAvoid() {
        return agent.areThereCellsWithAntPoison();
    }

    @Override
    protected ArrayList<ValuePositionPair<Float>> getCenterOfCellsToAvoid() {
        ArrayList<ValuePositionPair<FoodQuantityPair>> centerOfCellsInFieldOfViewWithFood = agent.getCenterOfCellsInFieldOfViewWithFood();

        // Need to convert the array above into an array with just the densities (floats)
        // Also only include AntPoison
        ArrayList<ValuePositionPair<Float>> ret = new ArrayList<>();
        for ( ValuePositionPair<FoodQuantityPair> pair : centerOfCellsInFieldOfViewWithFood )
            if ( (pair.getValue().getSource() instanceof AntPoison))
                ret.add ( new ValuePositionPair<>(pair.getValue().getAmount(), pair.getPosition()) );

        return ret;
    }
}

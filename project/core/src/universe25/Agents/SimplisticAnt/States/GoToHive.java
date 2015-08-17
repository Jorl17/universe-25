package universe25.Agents.SimplisticAnt.States;

import universe25.Agents.Regions.Region;
import universe25.Agents.SimplisticAnt.SimplisticAnt;
import universe25.Agents.Stackable.Food.StackableSourceQuantityPair;
import universe25.Agents.States.GoToCells;
import universe25.Agents.ValuePositionPair;
import universe25.Agents.Stackable.Food.AntPoison;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public class GoToHive extends GoToCells<SimplisticAnt> {
    public GoToHive(SimplisticAnt agent, int priority) {
        super(agent, priority, "GoToHive");
    }

    @Override
    protected boolean areThereCellsToGoTo() {
        return agent.isOutsideHive() && agent.areThereHiveCells();
    }

    @Override
    protected ArrayList<ValuePositionPair<Float>> getCenterOfCellsToGoTo() {
        ArrayList<ValuePositionPair<Region>> hiveCells = agent.getHiveCellsInFieldOfView();

        // Need to convert the array above into an array with just the densities (floats)
        // Also exclude AntPoison
        ArrayList<ValuePositionPair<Float>> ret = new ArrayList<>();

        for ( ValuePositionPair<Region> pair : hiveCells )
                ret.add ( new ValuePositionPair<>(10.0f/pair.getPosition().sub(agent.getPosition()).len(), pair.getPosition()) );

        return ret;
    }
}

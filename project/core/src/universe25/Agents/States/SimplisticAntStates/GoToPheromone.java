package universe25.Agents.States.SimplisticAntStates;

import universe25.Agents.Pheromone.Pheromone;
import universe25.Agents.SimplisticAnt.SimplisticAnt;
import universe25.Agents.States.GoToCell;
import universe25.Agents.ValuePositionPair;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public class GoToPheromone extends GoToCell<SimplisticAnt> {
    private Pheromone pheromone;
    public GoToPheromone(SimplisticAnt agent, int priority, Pheromone pheromone) {
        super(agent, priority, "GoToPheromone (" + pheromone + ")");
        this.pheromone = pheromone;
    }

    @Override
    protected boolean areThereCellsWithValues() {
        return agent.areThereCellsWithPheromone(pheromone);
    }

    @Override
    protected ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithValues() {
        return agent.getCenterOfCellsInFieldOfViewWithPheromone(pheromone);
    }
}

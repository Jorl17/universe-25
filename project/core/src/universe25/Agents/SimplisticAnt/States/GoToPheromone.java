package universe25.Agents.SimplisticAnt.States;

import universe25.Agents.Pheromones.Pheromone;
import universe25.Agents.SimplisticAnt.SimplisticAnt;
import universe25.Agents.States.GoToCells;
import universe25.Agents.ValuePositionPair;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public class GoToPheromone extends GoToCells<SimplisticAnt> {
    private Pheromone pheromone;
    public GoToPheromone(SimplisticAnt agent, int priority, Pheromone pheromone) {
        super(agent, priority, "GoToPheromone (" + pheromone + ")");
        this.pheromone = pheromone;
    }

    @Override
    protected boolean areThereCellsToGoTo() {
        return agent.areThereCellsWithPheromone(pheromone);
    }

    @Override
    protected ArrayList<ValuePositionPair<Float>> getCenterOfCellsToGoTo() {
        return agent.getCenterOfCellsInFieldOfViewWithPheromone(pheromone);
    }
}

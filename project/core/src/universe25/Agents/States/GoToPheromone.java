package universe25.Agents.States;

import universe25.Agents.Agent;
import universe25.Agents.SimplisticAnt;
import universe25.Agents.ValuePositionPair;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public class GoToPheromone extends GoToCell<SimplisticAnt> {
    private String pheromoneType;
    public GoToPheromone(SimplisticAnt agent, int priority, String pheromoneType) {
        super(agent, priority, "GoToPheromone (" + pheromoneType + ")");
        this.pheromoneType = pheromoneType;
    }

    @Override
    protected boolean areThereCellsWithValues() {
        return agent.areThereCellsWithPheromone(pheromoneType);
    }

    @Override
    protected ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithValues() {
        return agent.getCenterOfCellsInFieldOfViewWithPheromone(pheromoneType);
    }
}

package universe25.Agents.States;

import universe25.Agents.Agent;
import universe25.Agents.ValuePositionPair;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public class GoToPheromone extends GoToCell {
    public GoToPheromone(Agent agent, int priority) {
        super(agent, priority, "GoToPheromone");
    }

    @Override
    protected boolean areThereCellsWithValues() {
        return agent.areThereCellsWithPheromone();
    }

    @Override
    protected ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithValues() {
        return agent.getCenterOfCellsInFieldOfViewWithPheromone();
    }
}

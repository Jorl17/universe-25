package universe25.Agents.States;

import universe25.Agents.Agent;
import universe25.Agents.ValuePositionPair;
import universe25.GameLogic.Movement.WeightedGoal;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public abstract class GoToCells<T extends Agent> extends ToggablePriorityState<T> {
    private ArrayList<ValuePositionPair<Float>> cellsWithValues;
    public GoToCells(T agent, int priority, String name) {
        super(agent, name, priority);
        makeUnreachable();
    }

    protected  abstract boolean areThereCellsToGoTo();
    protected abstract ArrayList<ValuePositionPair<Float>> getCenterOfCellsToGoTo();
    @Override
    public void updatePriority() {
        if ( areThereCellsToGoTo() )
            makeReachable();
        else
            makeUnreachable();
    }

    @Override
    public String update() {
        cellsWithValues = getCenterOfCellsToGoTo();//agent.getCenterOfCellsInFieldOfViewWithPheromone();
        ArrayList<WeightedGoal> goals = new ArrayList<>();

        for ( ValuePositionPair<Float> cell : cellsWithValues)
            goals.add(WeightedGoal.fromFloatValuePositionPair(cell));

        agent.getGoalMovement().setWeightedGoals(goals);

        return null;
    }

    @Override
    public void leaveState() {
        //System.out.println("Left " + getName());
    }

    @Override
    public void enterState() {
        //System.out.println("Entered " + getName());
        agent.getGoalMovement().clearGoals();
    }
}

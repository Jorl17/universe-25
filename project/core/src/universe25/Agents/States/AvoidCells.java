package universe25.Agents.States;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;
import universe25.Agents.ValuePositionPair;
import universe25.GameLogic.Movement.WeightedGoal;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public abstract class AvoidCells<T extends Agent> extends ToggablePriorityState<T> {
    private ArrayList<ValuePositionPair<Float>> cellsWithValues;
    private ArrayList<WeightedGoal> goals;
    private float runAwayFactor;

    public AvoidCells(T agent, int priority, String name, float runAwayFactor) {
        super(agent, name, priority);
        this.runAwayFactor = runAwayFactor;
        makeUnreachable();
        goals = new ArrayList<>();
    }

    public AvoidCells(T agent, int priority, String name) {
        this(agent, priority, name, 1.0f);
    }

    protected  abstract boolean areThereCellsToAvoid();
    protected abstract ArrayList<ValuePositionPair<Float>> getCenterOfCellsToAvoid();
    @Override
    public void updatePriority() {
        if ( areThereCellsToAvoid() )
            makeReachable();
        else
            makeUnreachable();
    }

    @Override
    public String update() {
        cellsWithValues = getCenterOfCellsToAvoid();//agent.getCenterOfCellsInFieldOfViewWithPheromone();

        agent.getGoalMovement().removeGoalsIfExist(goals);
        goals.clear();
        Vector2 pos = agent.getPosition();
        for ( ValuePositionPair<Float> cell : cellsWithValues) {
            float dist = cell.getPosition().sub(pos).len();
            goals.add(new WeightedGoal(cell.getPosition(), -runAwayFactor/dist));

        }

        agent.getGoalMovement().addWeightedGoals(goals);

        return null;
    }

    @Override
    public void leaveState() {
        //System.out.println("Left " + getName());
        agent.getGoalMovement().removeGoalsIfExist(goals);
        goals.clear();
    }

    @Override
    public void enterState() {
        //System.out.println("Entered " + getName());
    }
}

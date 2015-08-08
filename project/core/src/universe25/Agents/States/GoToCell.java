package universe25.Agents.States;

import universe25.Agents.Agent;
import universe25.Agents.ValuePositionPair;
import universe25.GameLogic.Movement.WeightedGoal;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public abstract class GoToCell<T extends Agent> extends StateWithPriority<T> {
    private int priority;
    private ArrayList<ValuePositionPair<Float>> cellsWithValues;
    public GoToCell(T agent, int priority, String name) {
        super(agent, name, -1);
        this.priority = priority;
    }

    protected  abstract boolean areThereCellsWithValues();
    protected abstract ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithValues();
    @Override
    public void updatePriority() {
        setPriority(areThereCellsWithValues() ? priority : -1);
    }

    @Override
    public String update() {
        cellsWithValues = getCenterOfCellsInFieldOfViewWithValues();//agent.getCenterOfCellsInFieldOfViewWithPheromone();
        ArrayList<WeightedGoal> goals = new ArrayList<>();
        //TestPheromoneMapLayer pheromones = (TestPheromoneMapLayer) agent.getWorld().getGridLayers().get("TestLayer");
        for ( ValuePositionPair<Float> cell : cellsWithValues)
            goals.add(WeightedGoal.fromFloatValuePositionPair(cell));

        agent.getGoalMovement().setWeightedGoals(goals);

        return null;
    }

    @Override
    public void leaveState() {
        System.out.println("Left " + getName());
    }

    @Override
    public void enterState() {
        System.out.println("Entered " + getName());
    }
}

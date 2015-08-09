package universe25.Agents.SimplisticAnt;

import universe25.Agents.States.SimplisticAntStates.GoToFood;
import universe25.Agents.States.SimplisticAntStates.GoToPheromone;
import universe25.Agents.States.PriorityAggregatorState;
import universe25.Agents.States.Wander;
import universe25.Agents.Worlds.GridLayers.FloatLayer;

/**
 * Created by jorl17 on 08/08/15.
 */
public class PheromoneFollowingAnt extends SimplisticAnt {
    public PheromoneFollowingAnt() {
        //super(30, 150, 1, 1, 15);
        super(60, 50, 1, 1, 1*3, 1*3/5.0f);
    }

    @Override
    protected void prepareStates() {
        PriorityAggregatorState priorityAggregatorStates = new PriorityAggregatorState(this, "prioritisedStates");
        priorityAggregatorStates.addState(new Wander<>(this, 100, 80, 0.2f, 15));
        priorityAggregatorStates.addState(new GoToPheromone(this, 10, "TestPheromone"));
        priorityAggregatorStates.addState(new GoToPheromone(this, 16, "FoodPheromone"));
        priorityAggregatorStates.addState(new Wander<>(this, 100, 80, 0.05f, 17));
        priorityAggregatorStates.addState(new GoToFood(this, 20));
        states.addState(priorityAggregatorStates);
        states.addState(new GoToPheromone(this, 16, "FoodPheromone2"));
    }

    @Override
    public void update() {
        super.update();
        if ( ((FloatLayer)getWorld().getGridLayers().get("FoodLayer")).getValueAt(getPosition()) > 0) {
            getWorld().getActors().removeValue(this, false);
            //getGoalMovement().clearGoals();
            //states.clearStates();
        }
    }
}

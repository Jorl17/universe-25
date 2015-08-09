package universe25.Agents.SimplisticAnt;

import universe25.Agents.States.SimplisticAntStates.GoToFood;
import universe25.Agents.States.SimplisticAntStates.GoToPheromone;
import universe25.Agents.States.PriorityAggregatorState;
import universe25.Agents.States.TickBasedPriorityStateActivator;
import universe25.Agents.States.Wander;
import universe25.Worlds.GridLayers.FloatLayer;

import java.util.function.BooleanSupplier;

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
        priorityAggregatorStates.addState(new GoToPheromone(this, 10, pathPheronome));
        priorityAggregatorStates.addState(new GoToPheromone(this, 16, foodPheromone));
        priorityAggregatorStates.addState(new Wander<>(this, 100, 80, 0.05f, 17));
        priorityAggregatorStates.addState(new TickBasedPriorityStateActivator<>(this, "RampageForFood", 19,
                new Wander<>(this, 100, 80, 0.00f/*Does not matter*/, -1/*Does not matter*/),
                PheromoneFollowingAnt.this::areThereCellsWithFood, 1000, 300, true));
        priorityAggregatorStates.addState(new GoToFood(this, 20));
        states.addState(priorityAggregatorStates);
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

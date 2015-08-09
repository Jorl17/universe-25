package universe25.Agents;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.States.GoToFood;
import universe25.Agents.States.GoToPheromone;
import universe25.Agents.States.PriorityAggregatorState;
import universe25.Agents.States.Wander;
import universe25.Agents.Worlds.FloatLayer;

/**
 * Created by jorl17 on 08/08/15.
 */
public class PheromoneFollowingAnt extends SimplisticAnt {
    public PheromoneFollowingAnt() {
        //super(30, 150, 1, 1, 15);
        super(60, 50, 1, 1, 1*3);
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
    }

    @Override
    public void update() {
        super.update();
        if ( ((FloatLayer)getWorld().getGridLayers().get("FoodLayer")).getValueAt(getPosition()) > 0)
            setVisible(false);
    }
}

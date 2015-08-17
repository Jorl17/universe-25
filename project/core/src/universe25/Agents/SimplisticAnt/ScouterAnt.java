package universe25.Agents.SimplisticAnt;

import com.badlogic.gdx.graphics.Color;
import universe25.Agents.States.*;
import universe25.Agents.SimplisticAnt.States.GoToFood;

/**
 * Created by jorl17 on 06/08/15.
 */
public class ScouterAnt extends SimplisticAnt {

    public ScouterAnt() {
        //super(90, 200, 3, 3, 10);
        super(90, 70, 3, 3, 3*8, 0.5f, 100);
        setColor(new Color(1.0f,0.0f,0.0f,1.0f));
        foodImmediancyPheromoneController = null;
    }

    @Override
    protected void prepareStates() {
        PriorityAggregatorState priorityAggregatorStates = new PriorityAggregatorState(this, "prioritisedStates");
        priorityAggregatorStates.addState(new Wander<>(this, 100, 80, 0.2f, 15));
        priorityAggregatorStates.addState(new GoToFood(this, 20));
        states.addState(priorityAggregatorStates);
    }
}

package universe25.Agents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import universe25.Agents.States.*;
import universe25.Agents.Worlds.TestPheromoneMapLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 06/08/15.
 */
public class ScouterAnt extends SimplisticAnt {

    public ScouterAnt() {
        //super(90, 200, 3, 3, 10);
        super(90, 70, 3, 3, 3*3);
        setColor(new Color(1.0f,0.0f,0.0f,1.0f));
    }

    @Override
    protected void prepareStates() {
        PriorityAggregatorState priorityAggregatorStates = new PriorityAggregatorState(this, "prioritisedStates");
        priorityAggregatorStates.addState(new Wander<>(this, 100, 80, 0.2f, 15));
        priorityAggregatorStates.addState(new GoToFood(this, 20));
        states.addState(priorityAggregatorStates);
    }
}

package universe25.Agents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import universe25.Agents.States.*;
import universe25.Agents.Worlds.TestPheromoneMapLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 06/08/15.
 */
public class SimplisticAnt extends Agent {
    public SimplisticAnt() {
        super(new Texture("ant.png"), 90, 150);
        //super(new Texture("ant.png"), 30, 150);
        PriorityAggregatorState priorityAggregatorStates = new PriorityAggregatorState(this, "prioritisedStates");
        priorityAggregatorStates.addState(new Wander(this, 100, 80, 0.05f, 15));
        priorityAggregatorStates.addState(new GoToPheromone(this, 10));
        priorityAggregatorStates.addState(new GoToFood(this, 20));
        states.addState(priorityAggregatorStates);
    }

    private void increasePheromone() {
        TestPheromoneMapLayer testLayer = (TestPheromoneMapLayer) getWorld().getGridLayers().get("TestLayer");
        Vector2 pos = getPosition();
        testLayer.increasePheromoneAt(pos.x, pos.y, 1);
    }

    @Override
    public void update() {
        increasePheromone();
    }

    public boolean areThereCellsWithPheromone() {
        return areThereCellsWithValueAtFloatLayer("TestLayer");
    }

    public boolean areThereCellsWithFood() {
        return areThereCellsWithValueAtFloatLayer("FoodLayer");
    }

    public ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithPheromone() {
        return getCenterOfCellsInFieldOfViewWithValueForSomeFloatLayer("TestLayer");
    }

    public ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithFood() {
        return getCenterOfCellsInFieldOfViewWithValueForSomeFloatLayer("FoodLayer");
    }
}

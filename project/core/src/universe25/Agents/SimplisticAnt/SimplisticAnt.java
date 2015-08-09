package universe25.Agents.SimplisticAnt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;
import universe25.Agents.ValuePositionPair;
import universe25.Agents.Worlds.GridLayers.TestPheromoneMapLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 06/08/15.
 */
public abstract class SimplisticAnt extends Agent {
    private float pheromoneIncrease, pheromoneIncreaseWhenSeeingFood;
    private float pheromoneIncreaseWhenSeeingFoodPheromone;
    private static final Texture texture = new Texture("ant.png");
    protected SimplisticAnt(float fov, float seeDistance, float speed, float pheromoneIncrease, float pheromoneIncreaseWhenSeeingFood, float pheromoneIncreaseWhenSeeingFoodPheromone) {
        super(texture, false, fov, seeDistance, speed);
        setSize(8,8);
        setOriginX(4);
        setOriginY(4);
        this.pheromoneIncrease = pheromoneIncrease;
        this.pheromoneIncreaseWhenSeeingFood = pheromoneIncreaseWhenSeeingFood;
        this.pheromoneIncreaseWhenSeeingFoodPheromone = pheromoneIncreaseWhenSeeingFoodPheromone;
        //super(new Texture("ant.png"), 30, 150);
        prepareStates();
    }

    protected abstract void prepareStates();

    private void increasePheromone() {
        TestPheromoneMapLayer testLayer = (TestPheromoneMapLayer) getWorld().getGridLayers().get("TestPheromoneLayer");
        TestPheromoneMapLayer foodPheromone = (TestPheromoneMapLayer) getWorld().getGridLayers().get("FoodPheromoneLayer");
        Vector2 pos = getPosition();

        if ( areThereCellsWithFood() ) {
            foodPheromone.increasePheromoneAt(pos.x, pos.y, pheromoneIncreaseWhenSeeingFood);
        } else if ( areThereCellsWithPheromone("FoodPheromone") ) {
            foodPheromone.increasePheromoneAt(pos.x, pos.y, pheromoneIncreaseWhenSeeingFoodPheromone);
        }

        testLayer.increasePheromoneAt(pos.x, pos.y, pheromoneIncrease);
        //testLayer.increasePheromoneAt(pos.x, pos.y, areThereCellsWithFood() ? pheromoneIncreaseWhenSeeingFood : pheromoneIncrease);
    }

    @Override
    public void update() {
        increasePheromone();
    }

    public boolean areThereCellsWithPheromone(String pheromoneType) {
        return areThereCellsWithValueAtFloatLayer(pheromoneType + "Layer");
    }

    public boolean areThereCellsWithFood() {
        return areThereCellsWithValueAtFloatLayer("FoodLayer");
    }

    public ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithPheromone(String pheromoneType) {
        return getCenterOfCellsInFieldOfViewWithValueForSomeFloatLayer(pheromoneType + "Layer");
    }

    public ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithFood() {
        return getCenterOfCellsInFieldOfViewWithValueForSomeFloatLayer("FoodLayer");
    }
}

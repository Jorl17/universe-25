package universe25.Agents.SimplisticAnt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;
import universe25.Agents.Pheromone;
import universe25.Agents.SpeciesAgent;
import universe25.Agents.ValuePositionPair;
import universe25.Worlds.GridLayers.PheromoneMapLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 06/08/15.
 */
public abstract class SimplisticAnt extends SpeciesAgent {
    private float pheromoneIncrease, pheromoneIncreaseWhenSeeingFood;
    private float pheromoneIncreaseWhenSeeingFoodPheromone;
    private static final Texture texture = new Texture("ant.png");

    protected static final Pheromone foodPheromone = new Pheromone("Food", getSpecies(), 0.5f, 0.002f, 0.1f, 500, Color.CYAN);
    protected static final Pheromone pathPheronome = new Pheromone("Path", getSpecies(), 0.2f, 0.002f, 0.1f, 500, Color.YELLOW);

    public static void initializePheromones() {
        addSpeciesPheromone(pathPheronome);
        addSpeciesPheromone(foodPheromone);
    }

    protected SimplisticAnt(float fov, float seeDistance, float speed, float pheromoneIncrease, float pheromoneIncreaseWhenSeeingFood, float pheromoneIncreaseWhenSeeingFoodPheromone) {
        super(texture, false, fov, seeDistance, speed, "SimplisticAnt");
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
        Vector2 pos = getPosition();

        if ( areThereCellsWithFood() ) {
            foodPheromone.getWorldLayer().increasePheromoneAt(pos.x, pos.y, pheromoneIncreaseWhenSeeingFood);
        } else if ( areThereCellsWithPheromone(foodPheromone) ) {
            foodPheromone.getWorldLayer().increasePheromoneAt(pos.x, pos.y, pheromoneIncreaseWhenSeeingFoodPheromone);
        }

        pathPheronome.getWorldLayer().increasePheromoneAt(pos.x, pos.y, pheromoneIncrease);
    }

    @Override
    public void update() {
        increasePheromone();
    }

    public boolean areThereCellsWithPheromone(Pheromone pheromoneType) {
        return areThereCellsWithValueAtFloatLayer(pheromoneType.getWorldLayer());
    }

    public boolean areThereCellsWithFood() {
        return areThereCellsWithValueAtFloatLayer("FoodLayer");
    }

    public ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithPheromone(Pheromone pheromoneType) {
        return getCenterOfCellsInFieldOfViewWithValueForSomeFloatLayer(pheromoneType.getWorldLayer());
    }

    public ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithFood() {
        return getCenterOfCellsInFieldOfViewWithValueForSomeFloatLayer("FoodLayer");
    }

    public static Pheromone getPathPheronome() {
        return pathPheronome;
    }

    public static Pheromone getFoodPheromone() {
        return foodPheromone;
    }
}

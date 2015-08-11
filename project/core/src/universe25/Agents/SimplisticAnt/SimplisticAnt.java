package universe25.Agents.SimplisticAnt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import universe25.Agents.Pheromones.*;
import universe25.Agents.SpeciesAgent;
import universe25.Agents.ValuePositionPair;

import java.util.ArrayList;

/**
 * Created by jorl17 on 06/08/15.
 */
public abstract class SimplisticAnt extends SpeciesAgent {
    private static final Texture texture = new Texture("ant.png");

    protected static final Pheromone foodPheromone = new Pheromone("Food", getSpecies(), 0.01f, 0.002f, 0.1f, 500, Color.CYAN);
    protected static final Pheromone pathPheronome = new Pheromone("Path", getSpecies(), 0.2f, 0.002f, 0.1f, 500, Color.YELLOW);

    private PheromoneController foodPheromoneController, pathPheronomeController;

    public static void initializePheromones() {
        addSpeciesPheromone(pathPheronome);
        addSpeciesPheromone(foodPheromone);
    }

    protected SimplisticAnt(float fov, float seeDistance, float speed, float pathPheronomeIncrease, float foodPheromoneIncreaseWhenSeeingFood, float foodPheromoneIncreaseWhenSeeingFoodPheromone) {
        super(texture, false, fov, seeDistance, speed, "SimplisticAnt");
        setSize(8,8);
        setOriginX(4);
        setOriginY(4);
        foodPheromoneController = new AlternativeOrderedPheromoneController(
                                    new ConditionalIncreasePheromoneController(this, foodPheromone, foodPheromoneIncreaseWhenSeeingFood,
                                    (agent) -> ((SimplisticAnt)agent).areThereCellsWithFood()),
                                    new ConditionalIncreasePheromoneController(this, foodPheromone, foodPheromoneIncreaseWhenSeeingFoodPheromone,
                                    (agent) -> ((SimplisticAnt)agent).areThereCellsWithPheromone(foodPheromone))
                                );

        pathPheronomeController = new IncreasePheromoneController(this, pathPheronome, pathPheronomeIncrease);

        prepareStates();
    }

    protected abstract void prepareStates();

    @Override
    public void update() {
        foodPheromoneController.update();
        pathPheronomeController.update();
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

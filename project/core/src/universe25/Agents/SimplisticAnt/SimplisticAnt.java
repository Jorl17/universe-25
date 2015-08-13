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

    protected static final Pheromone foodPheromone = new Pheromone("Food", getSpecies(), 0.001f, 0.002f, 0.1f, 500, Color.CYAN);
    protected static final Pheromone pathPheronome = new Pheromone("Path", getSpecies(), 0.2f, 0.002f, 0.1f, 500, Color.YELLOW);
    protected static final Pheromone foodImmediancyPheromone = new Pheromone("FoodImmediancePheromone", getSpecies(), 0.001f, 0.002f, 0.1f, 500, Color.MAGENTA);

    protected PheromoneController foodPheromoneController, pathPheronomeController, foodImmediancyPheromoneController;

    public static void initializePheromones() {
        addSpeciesPheromone(pathPheronome);
        addSpeciesPheromone(foodPheromone);
        addSpeciesPheromone(foodImmediancyPheromone);
    }

    protected SimplisticAnt(float fov, float seeDistance, float speed, int movesMemorySize, float pathPheronomeIncrease, float foodPheromoneIncreaseWhenSeeingFood, float foodPheromoneIncreaseWhenSeeingFoodPheromone) {
        super(texture, false, fov, seeDistance, speed, movesMemorySize, "SimplisticAnt");
        setSize(8,8);
        setOriginX(4);
        setOriginY(4);
        foodPheromoneController = new AlternativeOrderedPheromoneController(
                                    new ConditionalIncreasePheromoneController<>(this, foodPheromone, foodPheromoneIncreaseWhenSeeingFood,
                                            SimplisticAnt::areThereCellsWithFood),
                                   /* new ConditionalIncreasePheromoneController(this, foodPheromone, foodPheromoneIncreaseWhenSeeingFoodPheromone,
                                    (agent) -> ((SimplisticAnt)agent).areThereCellsWithPheromone(foodPheromone)*/
                                    new ProportionalPheromoneController(this, foodPheromone, 0.20f, 1, true, 100)
                                 );

        pathPheronomeController = /*new AlternativeOrderedPheromoneController( new ConditionalIncreasePheromoneController(this, pathPheronome, pathPheronomeIncrease,
                (agent) -> !((SimplisticAnt)agent).areThereCellsWithPheromone(pathPheronome)),
                new ProportionalPheromoneController(this, pathPheronome, 0.5f, pathPheronomeIncrease, true, 100));*/
                new IncreasePheromoneController(this, pathPheronome, pathPheronomeIncrease);

        foodImmediancyPheromoneController = new ConditionalIncreasePheromoneController<>(this, foodImmediancyPheromone, 1,
                SimplisticAnt::areThereCellsWithFood);

        prepareStates();
    }

    public static Pheromone getFoodImmediancyPheromone() {
        return foodImmediancyPheromone;
    }

    protected abstract void prepareStates();

    @Override
    public void update() {
        if ( foodPheromoneController != null)
            foodPheromoneController.update();
        if ( pathPheronomeController != null)
            pathPheronomeController.update();
        if ( foodImmediancyPheromoneController != null)
            foodImmediancyPheromoneController.update();
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

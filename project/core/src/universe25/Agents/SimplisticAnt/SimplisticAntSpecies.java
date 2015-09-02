package universe25.Agents.SimplisticAnt;

import com.badlogic.gdx.graphics.Color;
import universe25.Agents.Pheromones.Pheromone;
import universe25.Agents.Regions.Hive;
import universe25.Agents.Species;
import universe25.Agents.SpeciesAgent;
import universe25.Utils.RandomUtils;

/**
 * Created by jorl17 on 17/08/15.
 */
public class SimplisticAntSpecies extends Species<SimplisticAnt> {
    protected final Pheromone foodPheromone;
    protected final Pheromone pathPheromone;
    protected final Pheromone foodImmediancyPheromone;
    protected final Pheromone hivePheromone;

    public SimplisticAntSpecies(String subname) {
        super("SimplisticAnt " + "(" + subname + ")");
        //FIXME: Add the below parameters to the species parameters as well
        foodPheromone = new Pheromone("Food", this, 0.001f, 0.002f, 0.1f, 500, Color.CYAN);
        pathPheromone = new Pheromone("Path", this, 0.2f, 0.002f, 0.1f, 500, Color.YELLOW);
        foodImmediancyPheromone = new Pheromone("FoodImmediancePheromone", this, 0.02f, 0.002f, 0.1f, 500, Color.MAGENTA);
        hivePheromone = new Pheromone("HivePheromone", this, 0.001f, 0.002f, 0.1f, 500, Color.BLUE.mul(0.5f,0.5f,0.5f,1.0f));
        addSpeciesPheromone(pathPheromone);
        addSpeciesPheromone(hivePheromone);
        addSpeciesPheromone(foodPheromone);
        addSpeciesPheromone(foodImmediancyPheromone);
        //hive = new Hive<>();
    }

    @Override
    public SimplisticAnt newIndividual() {
        return RandomUtils.coin(0.99f) ? new PheromoneFollowingAnt(this) : new ScouterAnt(this);
    }

    public Pheromone getFoodPheromone() {
        return foodPheromone;
    }

    public Pheromone getPathPheromone() {
        return pathPheromone;
    }

    public Pheromone getFoodImmediancyPheromone() {
        return foodImmediancyPheromone;
    }

    public Pheromone getHivePheromone() {
        return hivePheromone;
    }
}

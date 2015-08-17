package universe25.Agents.SimplisticAnt;

import com.badlogic.gdx.graphics.Color;
import universe25.Agents.Pheromones.Pheromone;
import universe25.Agents.Species;

/**
 * Created by jorl17 on 17/08/15.
 */
public class SimplisticAntSpecies extends Species {
    protected final Pheromone foodPheromone;
    protected final Pheromone pathPheromone;
    protected final Pheromone foodImmediancyPheromone;

    public SimplisticAntSpecies(String subname) {
        super("SimplisticAnt " + "(" + subname + ")");
        //FIXME: Add the below parameters to the species parameters as well
        foodPheromone = new Pheromone("Food", this, 0.001f, 0.002f, 0.1f, 500, Color.CYAN);
        pathPheromone = new Pheromone("Path", this, 0.2f, 0.002f, 0.1f, 500, Color.YELLOW);
        foodImmediancyPheromone = new Pheromone("FoodImmediancePheromone", this, 0.001f, 0.002f, 0.1f, 500, Color.MAGENTA);
        addSpeciesPheromone(foodPheromone);
        addSpeciesPheromone(pathPheromone);
        addSpeciesPheromone(foodImmediancyPheromone);
    }

    @Override
    public Species newIndividual() {
        //return new SimplisticAntSpecies(this);
        return null; //FIXME
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
}

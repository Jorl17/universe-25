package universe25.Agents;

import com.badlogic.gdx.graphics.Texture;
import universe25.Agents.Pheromone.Pheromone;

import java.util.ArrayList;

/**
 * Created by jorl17 on 09/08/15.
 */
public abstract class SpeciesAgent extends Agent {
    private static String species;
    private static ArrayList<Pheromone> pheromones = new ArrayList<>();
    protected SpeciesAgent(Texture texture, boolean shouldDisposeTexture, float fov, float seeDistance, float speed, String species) {
        super(texture, shouldDisposeTexture, fov, seeDistance, speed);
        SpeciesAgent.species = species;
    }

    protected SpeciesAgent(Texture texture, boolean shouldDisposeTexture, float fov, float seeDistance, float speed, boolean debugDrawFov, boolean debugDrawCellsUnderFov, boolean debugDrawGoals, boolean debugDrawfacing, String species) {
        super(texture, shouldDisposeTexture, fov, seeDistance, speed, debugDrawFov, debugDrawCellsUnderFov, debugDrawGoals, debugDrawfacing);
        SpeciesAgent.species = species;
    }

    public static String getSpecies() {
        return species;
    }

    public static ArrayList<Pheromone> getPheromones() {
        return pheromones;
    }

    protected static void addSpeciesPheromone(Pheromone p) {
        pheromones.add(p);
    }

}

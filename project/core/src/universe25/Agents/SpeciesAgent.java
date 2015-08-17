package universe25.Agents;

import com.badlogic.gdx.graphics.Texture;
import universe25.Agents.Pheromones.Pheromone;

import java.util.ArrayList;

/**
 * Created by jorl17 on 09/08/15.
 */
public abstract class SpeciesAgent extends Agent {
    private Species species;

    protected SpeciesAgent(Texture texture, boolean shouldDisposeTexture, Species species, float fov, float seeDistance, float speed,
                           int movesMemorySize) {
        super(texture, shouldDisposeTexture, fov, seeDistance, speed, movesMemorySize);
        this.species = species;
    }

    protected SpeciesAgent(Texture texture, boolean shouldDisposeTexture, Species species, float fov, float seeDistance, float speed,
                           int movesMemorySize, boolean debugDrawFov, boolean debugDrawCellsUnderFov,
                           boolean debugDrawGoals, boolean debugDrawfacing) {
        super(texture, shouldDisposeTexture, fov, seeDistance, speed, movesMemorySize, debugDrawFov, debugDrawCellsUnderFov, debugDrawGoals, debugDrawfacing);
        this.species = species;
    }

    public Species getSpecies() {
        return species;
    }
}

package universe25.Agents;

import com.badlogic.gdx.graphics.Texture;
import universe25.Agents.SpeciesParameters.SpeciesParameter;
import universe25.Agents.SpeciesParameters.SpeciesParameters;

/**
 * Created by jorl17 on 09/08/15.
 */
public abstract class SpeciesAgent extends Agent {
    private Species species;
    private SpeciesParameters parameters;

    protected SpeciesAgent(Texture texture, boolean shouldDisposeTexture, Species species, SpeciesParameters parameters) {
        super(texture, shouldDisposeTexture,
                (float)parameters.get("fov").get(),
                (float)parameters.get("seeDistance").get(),
                (float)parameters.get("speed").get(),
                (int)parameters.get("movesMemorySize").get());
        this.species = species;
        this.parameters = parameters.cpy();
    }

    protected SpeciesAgent(Texture texture, boolean shouldDisposeTexture, Species species, SpeciesParameters parameters,
                           boolean debugDrawFov, boolean debugDrawCellsUnderFov, boolean debugDrawGoals, boolean debugDrawfacing) {
        super(texture, shouldDisposeTexture,
                (float)parameters.get("fov").get(),
                (float)parameters.get("seeDistance").get(),
                (float)parameters.get("speed").get(),
                (int)parameters.get("movesMemorySize").get(),debugDrawFov, debugDrawCellsUnderFov, debugDrawGoals, debugDrawfacing);
        this.species = species;
        this.parameters = parameters.cpy();
    }

    public Species getSpecies() {
        return species;
    }

    public SpeciesParameters getParameters() {
        return parameters;
    }

    public SpeciesParameter getParameter(String name) {
        return parameters.get(name);
    }
}

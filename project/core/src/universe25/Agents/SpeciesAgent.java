package universe25.Agents;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by jorl17 on 09/08/15.
 */
public abstract class SpeciesAgent extends Agent {
    private final String species;
    protected SpeciesAgent(Texture texture, boolean shouldDisposeTexture, float fov, float seeDistance, float speed, String species) {
        super(texture, shouldDisposeTexture, fov, seeDistance, speed);
        this.species = species;
    }

    protected SpeciesAgent(Texture texture, boolean shouldDisposeTexture, float fov, float seeDistance, float speed, boolean debugDrawFov, boolean debugDrawCellsUnderFov, boolean debugDrawGoals, boolean debugDrawfacing, String species) {
        super(texture, shouldDisposeTexture, fov, seeDistance, speed, debugDrawFov, debugDrawCellsUnderFov, debugDrawGoals, debugDrawfacing);
        this.species = species;
    }

    public String getSpecies() {
        return species;
    }
}

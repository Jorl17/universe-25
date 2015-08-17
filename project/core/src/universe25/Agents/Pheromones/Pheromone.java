package universe25.Agents.Pheromones;

import com.badlogic.gdx.graphics.Color;
import universe25.Agents.Species;
import universe25.World.GridLayers.PheromoneMapLayer;
import universe25.World.World;

/**
 * Created by jorl17 on 09/08/15.
 */
public class Pheromone {
    private World world;
    private String pheromoneName;
    private Species species;
    private float evaporationRate;
    private float spreadRate;
    private float spreadProbability;
    private float pheromoneLayerMax;
    private PheromoneMapLayer layer;
    private Color color;

    public Pheromone(String pheromoneName, Species species, float evaporationRate, float spreadRate, float spreadProbability, float pheromoneLayerMax, Color color) {
        this.pheromoneName = pheromoneName;
        this.species = species;
        this.evaporationRate = evaporationRate;
        this.spreadRate = spreadRate;
        this.spreadProbability = spreadProbability;
        this.pheromoneLayerMax = pheromoneLayerMax;
        this.color = color;
    }

    // Called once all pheromones have been created, when the world is adding the necessary pheromone layers
    public void registerWithWorld(World world) {
        this.world = world;
        world.registerPheromoneLayer(getWorldLayerName(), pheromoneLayerMax, color);
        this.layer = (PheromoneMapLayer) world.getGridLayers().get(getWorldLayerName());
    }

    public String getFullPheromoneName() {
        return species.getName() + pheromoneName;
    }

    public String getWorldLayerName() {
        return getFullPheromoneName() + " Layer";
    }

    public PheromoneMapLayer getWorldLayer() {
        return this.layer;
    }

    public void evaporate() {
        this.layer.evaporate(evaporationRate);
    }

    public void spread() {
        this.layer.spread(spreadRate, spreadProbability);
    }

    @Override
    public String toString() {
        return "Pheromone{worldLayerName=" + getWorldLayerName() +
                ", world=" + world +
                ", pheromoneName='" + pheromoneName + '\'' +
                ", species=" + species +
                ", evaporationRate=" + evaporationRate +
                ", spreadRate=" + spreadRate +
                ", spreadProbability=" + spreadProbability +
                ", pheromoneLayerMax=" + pheromoneLayerMax +
                ", layer=" + layer +
                ", color=" + color +
                '}';
    }
}

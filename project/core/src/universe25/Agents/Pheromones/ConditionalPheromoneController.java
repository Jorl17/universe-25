package universe25.Agents.Pheromones;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;

import java.util.function.Function;

/**
 * Created by jorl17 on 09/08/15.
 */
public class ConditionalPheromoneController<T extends Agent> implements PheromoneController {
    private T agent;
    private PheromoneController pheromoneController;
    private Function<T, Boolean> verifier;

    public ConditionalPheromoneController(T agent, PheromoneController pheromoneController, Function<T, Boolean> verifier) {
        this.agent = agent;
        this.pheromoneController = pheromoneController;
        this.verifier = verifier;
    }

    @Override
    public boolean update() {
        if ( verifier.apply(agent) ) {
            Vector2 position = agent.getPosition();
            pheromoneController.update();
            return true;
        } else
            return false;
    }
}

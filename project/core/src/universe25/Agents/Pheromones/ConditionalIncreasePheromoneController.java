package universe25.Agents.Pheromones;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;

import java.util.function.Function;

/**
 * Created by jorl17 on 09/08/15.
 */
public class ConditionalIncreasePheromoneController<T extends Agent> implements PheromoneController {
    private T agent;
    private Pheromone pheromone;
    private float amountToIncrease;
    private Function<T, Boolean> verifier;

    public ConditionalIncreasePheromoneController(T agent, Pheromone pheromone, float amountToIncrease, Function<T, Boolean> verifier) {
        this.agent = agent;
        this.pheromone = pheromone;
        this.amountToIncrease = amountToIncrease;
        this.verifier = verifier;
    }

    @Override
    public boolean update() {
        if ( verifier.apply(agent) ) {
            Vector2 position = agent.getPosition();
            pheromone.getWorldLayer().increasePheromoneAt(position.x, position.y, amountToIncrease);
            return true;
        } else
            return false;
    }
}

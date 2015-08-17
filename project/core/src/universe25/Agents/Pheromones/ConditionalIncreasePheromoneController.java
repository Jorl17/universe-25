package universe25.Agents.Pheromones;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;

import java.util.function.Function;

/**
 * Created by jorl17 on 09/08/15.
 */
public class ConditionalIncreasePheromoneController<T extends Agent> extends ConditionalPheromoneController<T> {
    private T agent;
    private Pheromone pheromone;
    private float amountToIncrease;
    private Function<T, Boolean> verifier;

    public ConditionalIncreasePheromoneController(T agent, Pheromone pheromone, float amountToIncrease, Function<T, Boolean> verifier) {
        super(agent, new IncreasePheromoneController(agent, pheromone, amountToIncrease), verifier);
    }
}

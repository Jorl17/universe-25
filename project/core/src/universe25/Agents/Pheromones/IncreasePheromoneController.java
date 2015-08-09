package universe25.Agents.Pheromones;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;

/**
 * Created by jorl17 on 09/08/15.
 */
public class IncreasePheromoneController implements PheromoneController {
    private Agent agent;
    private Pheromone pheromone;
    private float amountToIncrease;

    public IncreasePheromoneController(Agent agent, Pheromone pheromone, float amountToIncrease) {
        this.agent = agent;
        this.pheromone = pheromone;
        this.amountToIncrease = amountToIncrease;
    }

    @Override
    public boolean update() {
        Vector2 position = agent.getPosition();
        pheromone.getWorldLayer().increasePheromoneAt(position.x, position.y, amountToIncrease);
        return true;
    }
}

package universe25.Agents.Pheromones;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;
import universe25.GameLogic.Movement.MoveSequence.FixedGridMoveSequence;
import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.World.GridLayers.PheromoneMapLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 09/08/15.
 */
public class ProportionalPheromoneController implements PheromoneController {
    private Agent agent;
    private Pheromone pheromone;
    private float factor;
    private float threshold;
    private float upperThreshold;
    private boolean dontRepeat;

    private FixedGridMoveSequence memory;

    public ProportionalPheromoneController(Agent agent, Pheromone pheromone, float factor, float threshold, float upperThreshold, boolean dontRepeat, int memorySize) {
        this.agent = agent;
        this.pheromone = pheromone;
        this.factor = factor;
        this.threshold = threshold;
        this.upperThreshold = upperThreshold;
        if ( upperThreshold == -1 ) upperThreshold = pheromone.getMax()+0.1f;
        this.dontRepeat = dontRepeat;
        if ( dontRepeat )
            memory = new FixedGridMoveSequence(memorySize);
    }

    @Override
    public boolean update() {
        Vector2 position = agent.getPosition();

        ArrayList<GridCell> cellsInFov = agent.getCellsInFov();
        PheromoneMapLayer worldLayer = pheromone.getWorldLayer();

        float sum = 0;
        for (GridCell cell : cellsInFov)
            sum += worldLayer.getValueAtCell(cell);

        sum /= cellsInFov.size();

        if ( sum > threshold && sum < upperThreshold ) {
            if ( dontRepeat ) {
                memory.setGrid(agent.getWorld().getWorldObjectsLayer());
                GridCell agentCell = agent.getWorld().getWorldObjectsLayer().getCell(position.x, position.y);
                if (memory.containsMove(agentCell) && pheromone.getWorldLayer().getValueAt(position.x, position.y) > 0) {
                    //System.out.println("Hehe, not adding!");
                    return false;
                } else
                    memory.addMoveIfUnique(agentCell);
            }
            pheromone.getWorldLayer().increasePheromoneAt(position.x, position.y, factor * sum);
        }
        return true;
    }
}

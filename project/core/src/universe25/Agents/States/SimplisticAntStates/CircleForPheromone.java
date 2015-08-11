package universe25.Agents.States.SimplisticAntStates;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Pheromones.Pheromone;
import universe25.Agents.SimplisticAnt.SimplisticAnt;
import universe25.Agents.States.StateWithPriority;
import universe25.GameLogic.NumberProducers.NumberProducer;
import universe25.GameLogic.Time.Ticks;

/**
 * Created by jorl17 on 11/08/15.
 */
public class CircleForPheromone extends StateWithPriority<SimplisticAnt> {
    private int priorityWhenInPheromone;
    private Pheromone pheromone;
    private float maxAllowedDegree;
    private NumberProducer<Long> maximumTicksToTryToFindFood;
    private boolean decreaseWhenTimeRunsOut;
    private float amountToDecrease;

    private long ticksWhenEntered;
    private boolean active;
    private float origRotation;


    public CircleForPheromone(SimplisticAnt agent, String name, int priorityWhenInPheromone, Pheromone pheromone,
                              float maxAllowedDegree, NumberProducer<Long> maximumTicksToTryToFindFood) {
        this(agent, name, priorityWhenInPheromone, pheromone, maxAllowedDegree, maximumTicksToTryToFindFood, false, 0);
    }

    public CircleForPheromone(SimplisticAnt agent, String name, int priorityWhenInPheromone, Pheromone pheromone,
                              float maxAllowedDegree, NumberProducer<Long> maximumTicksToTryToFindFood, boolean decreaseWhenTimeRunsOut, float amountToDecrease) {
        super(agent, name);
        this.priorityWhenInPheromone = priorityWhenInPheromone;
        this.pheromone = pheromone;
        this.maxAllowedDegree = maxAllowedDegree;
        this.maximumTicksToTryToFindFood = maximumTicksToTryToFindFood;
        this.decreaseWhenTimeRunsOut = decreaseWhenTimeRunsOut;
        this.amountToDecrease = amountToDecrease;
    }

    @Override
    public void updatePriority() {
        Vector2 pos = agent.getPosition();
        if ( !active && (pheromone.getWorldLayer().getValueAt(pos.x, pos.y) > 0 /*&& Math.random() > 0.5f*/) ) {
            setPriority(priorityWhenInPheromone);
        }

        if ( active ) {
            if ( Ticks.ticksSince(ticksWhenEntered) > maximumTicksToTryToFindFood.produce() ) {
                makeUnreachable();
                if (decreaseWhenTimeRunsOut) {
                    if (amountToDecrease == -1) {
                        pheromone.getWorldLayer().setPheromoneAt(pos.x, pos.y, 0);
                    } else {
                        pheromone.getWorldLayer().decreasePheromoneAt(pos.x, pos.y, amountToDecrease);
                    }
                }
            }
        }
    }

    @Override
    public String update() {
        // Remember: if update is called, then there is no food, because we assume that GoToFood is put at a HIGHER priority.
        makeUnreachable();


        //FIXME: Hack. Just so they don't get "stuck" so often, let's first try a couple of rotations followed by updates
        // 20 tries.. should
        float rotation = agent.getRotation();
        for (int i = 0 ; i < 90; i++) {
            agent.setRotation(rotation + i);
            agent.updateFov();
            agent.updateCellsInFov();
            if ( agent.areThereCellsWithFood() ) return null;
            agent.setRotation(rotation - i);
            agent.updateFov();
            agent.updateCellsInFov();
            if ( agent.areThereCellsWithFood() ) return null;

        }

        agent.updateFov();
        agent.updateCellsInFov();
        agent.setRotation(origRotation);
        // Always rotate in the same direction
        //agent.rotateBy((float) (Math.random()*maxAllowedDegree));

        return null;
    }

    @Override
    public void leaveState() {
        // Generally means that there is now food..or that the time just ran out
        agent.getGoalMovement().clearGoals();
        agent.setRotation(origRotation);
        active = false;
    }

    @Override
    public void enterState() {
        origRotation = agent.getRotation();
        ticksWhenEntered = Ticks.getTicks();
        agent.getGoalMovement().clearGoals();
        active = true;
    }
}

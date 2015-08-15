package universe25.Agents.States;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;
import universe25.GameLogic.Movement.WeightedGoal;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public class Wander<T extends Agent> extends ToggablePriorityState<T> {
    private long directionChangeIntervalMs;
    private float maxAllowedDegreeChange;
    private long lastChangeTime;
    private Vector2 target;
    private float dontWanderProb;

    public Wander(T agent, long directionChangeIntervalMs, float maxAllowedDegreeChange, float dontWanderProb, int maxWanderPriority) {
        super(agent, "Wander", maxWanderPriority);
        this.directionChangeIntervalMs = directionChangeIntervalMs;
        this.lastChangeTime = -1;
        this.maxAllowedDegreeChange = maxAllowedDegreeChange;
        this.target = new Vector2();
        this.dontWanderProb = dontWanderProb;
        giveLowestPriority();
    }

    // By default, it wanders permantently (with zero probability)
    public Wander(T agent, long directionChangeIntervalMs, float maxAllowedDegreeChange) {
        this(agent,directionChangeIntervalMs,maxAllowedDegreeChange,0.0f,-1);
    }

    private void randomTarget() {
        Vector2 dir = agent.getFacingDirection();

        float rotationAngle = (float) (Math.random() * maxAllowedDegreeChange - maxAllowedDegreeChange / 2);
        dir = dir.rotate(rotationAngle);

        int collisionsWithWorld = agent.getCollisionsWithWorld();

        //System.out.println(collisionsWithWorld);
        if ((collisionsWithWorld & Agent.COLLIDED_TOP) == Agent.COLLIDED_TOP) {
            dir.add(new Vector2(0,-1));
            //System.out.println("Collided top!");
        } else if ((collisionsWithWorld & Agent.COLLIDED_BOTTOM) == Agent.COLLIDED_BOTTOM) {
            dir.add(new Vector2(0,1));
            //System.out.println("Collided Bottom!");
        }

        if ((collisionsWithWorld & Agent.COLLIDED_LEFT) == Agent.COLLIDED_LEFT) {
            dir.add(new Vector2(1,0));
            //System.out.println("Collided Left!");
        } else if ((collisionsWithWorld & Agent.COLLIDED_RIGHT) == Agent.COLLIDED_RIGHT) {
            dir.add(new Vector2(-1,0));
            //System.out.println("Collided Right!");
        }

        this.target.set(agent.getPosition().add(dir.scl(700.0f)));
    }

    @Override
    public String update() {
        long currentTime = System.currentTimeMillis();
        if ( (lastChangeTime == -1) || (currentTime - lastChangeTime > directionChangeIntervalMs)) {
            //agent.getGoalMovement().removeGoalIfExists(target);
            randomTarget();
            lastChangeTime = currentTime;

            //agent.getGoalMovement().addGoal(new WeightedGoal(target, 1));
            agent.getGoalMovement().setGoal(target);
        }
        return sameState();
    }

    @Override
    public void leaveState() {
        //System.out.println("Left " + getName());
        lastChangeTime = -1;
    }

    @Override
    public void enterState() {
        //System.out.println("Entered " + getName());
        lastChangeTime = -1;
        if ( agent.getGoalMovement() != null )
            agent.getGoalMovement().clearGoals();
    }

    @Override
    public void updatePriority() {
        if (Math.random() > (1-dontWanderProb)) {
            makeReachable();
        } else
            giveLowestPriority();
    }
}

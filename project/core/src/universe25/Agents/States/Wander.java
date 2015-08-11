package universe25.Agents.States;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public class Wander<T extends Agent> extends StateWithPriority<T> {
    private long directionChangeIntervalMs;
    private float maxAllowedDegreeChange;
    private long lastChangeTime;
    private Vector2 target;
    private int maxWanderPriority;
    private float dontWanderProb;

    public Wander(T agent, long directionChangeIntervalMs, float maxAllowedDegreeChange, float dontWanderProb, int maxWanderPriority) {
        super(agent, "Wander", 0);
        this.directionChangeIntervalMs = directionChangeIntervalMs;
        this.lastChangeTime = -1;
        this.maxAllowedDegreeChange = maxAllowedDegreeChange;
        this.target = new Vector2();
        this.dontWanderProb = dontWanderProb;
        this.maxWanderPriority = maxWanderPriority;
    }

    // By default, it wanders permantently (with zero probability)
    public Wander(T agent, long directionChangeIntervalMs, float maxAllowedDegreeChange) {
        this(agent,directionChangeIntervalMs,maxAllowedDegreeChange,0.0f,-1);
    }

    private void randomTarget() {
        Vector2 dir = agent.getFacingDirection();
        Vector2 pos = agent.getPosition();
        //cSystem.out.println("rnd");

        /*ArrayList<Vector2> directions = agent.getCenterOfCellsWithOjects();
        for ( Vector2 v : directions ) v.sub(pos);*/

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


        /*for ( Vector2 v : directions) {
            Vector2 tmpTarget = agent.getPosition().add(dir);
            if (Math.abs(v.angle(tmpTarget)) < 20)
                dir.sub(v.scl(0.005f));
        }*/

        this.target.set(agent.getPosition().add(dir.scl(700.0f)));
    }

    @Override
    public String update() {
        long currentTime = System.currentTimeMillis();
        if ( (lastChangeTime == -1) || (currentTime - lastChangeTime > directionChangeIntervalMs)) {
            randomTarget();
            lastChangeTime = currentTime;
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
        setPriority(Math.random() > (1-dontWanderProb) ? maxWanderPriority : 0);

    }
}

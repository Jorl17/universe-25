package universe25.Agents.States;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;

/**
 * Created by jorl17 on 08/08/15.
 */
public class Wander extends StateWithPriority {
    private long directionChangeIntervalMs;
    private float maxAllowedDegreeChange;
    private long lastChangeTime;
    private Vector2 target;
    private int maxWanderPriority;
    private float dontWanderProb;

    public Wander(Agent agent, long directionChangeIntervalMs, float maxAllowedDegreeChange, float dontWanderProb, int maxWanderPriority) {
        super(agent, "Wander", 0);
        this.directionChangeIntervalMs = directionChangeIntervalMs;
        this.lastChangeTime = -1;
        this.maxAllowedDegreeChange = maxAllowedDegreeChange;
        this.target = new Vector2();
        this.dontWanderProb = dontWanderProb;
        this.maxWanderPriority = maxWanderPriority;
    }

    private void randomTarget() {
        Vector2 dir = agent.getFacingDirection();
        //System.out.println(dir);
        float rotationAngle = (float) (Math.random()*maxAllowedDegreeChange - maxAllowedDegreeChange/2);
        dir.rotate(rotationAngle);
        //System.out.println(rotationAngle);
        //float worldDiagonalLen = new Vector2(agent.getWorld().getWidth(), agent.getWorld().getHeight()).len();
        this.target.set(agent.getPosition().add(dir.scl(/*agent.getWidth()*2.5f*/500.0f)));
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
        System.out.println("Left " + getName());
        lastChangeTime = -1;
    }

    @Override
    public void enterState() {
        System.out.println("Entered " + getName());
        lastChangeTime = -1;
        if ( agent.getGoalMovement() != null )
            agent.getGoalMovement().clearGoals();
    }

    @Override
    public void updatePriority() {
        setPriority(Math.random() > (1-dontWanderProb) ? maxWanderPriority : -1);

    }
}

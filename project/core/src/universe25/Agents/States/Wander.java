package universe25.Agents.States;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;

/**
 * Created by jorl17 on 08/08/15.
 */
public class Wander extends State {
    private long directionChangeIntervalMs;
    private long lastChangeTime;
    private Vector2 target;

    public Wander(Agent agent, long directionChangeIntervalMs) {
        super(agent, "Wander");
        this.directionChangeIntervalMs = directionChangeIntervalMs;
        this.lastChangeTime = -1;
        this.target = new Vector2();
    }

    private void randomTarget() {
        this.target.set((float)Math.random()*agent.getWorld().getWidth(),(float)Math.random()*agent.getWorld().getHeight());
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
        lastChangeTime = -1;
    }

    @Override
    public void enterState() {
        lastChangeTime = -1;
        if ( agent.getGoalMovement() != null )
            agent.getGoalMovement().clearGoals();
    }
}

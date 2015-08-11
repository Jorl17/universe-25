package universe25.Agents.States;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;
import universe25.GameLogic.NumberProducers.NumberProducer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 11/08/15.
 */
public class WanderAvoidingObstacles<T extends Agent> extends  StateWithPriority<T> {
    private float maxAllowedChangeDeg;
    private NumberProducer<Float> weight;
    private boolean alwaysPerturbateMovement;

    public WanderAvoidingObstacles(T agent, String name, int priority, float maxAllowedChangeDeg, NumberProducer<Float> weight, boolean alwaysPerturbateMovement) {
        super(agent, name, priority);
        this.maxAllowedChangeDeg = maxAllowedChangeDeg;
        this.weight = weight;
        this.alwaysPerturbateMovement = alwaysPerturbateMovement;
    }

    public WanderAvoidingObstacles(T agent, String name, float maxAllowedChangeDeg, NumberProducer<Float> weight, boolean alwaysPerturbateMovement) {
        super(agent, name);
        this.maxAllowedChangeDeg = maxAllowedChangeDeg;
        this.weight = weight;
        this.alwaysPerturbateMovement = alwaysPerturbateMovement;
    }

    @Override
    public void updatePriority() {

    }

    @Override
    public String update() {
        Vector2 dir = new Vector2(0,0);
        Vector2 pos = agent.getPosition();

        ArrayList<Vector2> centerOfCellsInFieldOfView = agent.getCenterOfCellsInFieldOfView();


        for (Vector2 v : centerOfCellsInFieldOfView)
            dir.add(v.sub(pos).nor().scl((float) (Math.random()*weight.produce())));

        if ( dir.isZero() || alwaysPerturbateMovement ) {
            float rotationAngle = (float) (Math.random() * maxAllowedChangeDeg - maxAllowedChangeDeg / 2);
            dir = agent.getFacingDirection().rotate(rotationAngle);
        }

        agent.getGoalMovement().setGoal(pos.add(dir.scl(700.0f)));

        return null;
    }

    @Override
    public void leaveState() {

    }

    @Override
    public void enterState() {

    }
}

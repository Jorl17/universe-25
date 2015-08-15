package universe25.GameLogic.Movement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 07/08/15.
 */
public class GoalMovement {
    //FIXME: Actor and GoalMovement are highly coupled. Suggest using interfaces to tie them together, like
    // Steerable things do
    private Actor actor;
    private final ArrayList<WeightedGoal> goals;
    private Vector2 goalDirection;

    public GoalMovement(Actor actor) {
        this.actor = actor;
        this.goals = new ArrayList<WeightedGoal>();
        this.goalDirection = new Vector2();
    }

    public void setGoal(Vector2 goal) {
        this.goals.clear();
        this.goals.add(new WeightedGoal(goal, 1.0f));
        updateGoalDirection();
    }

    public void addGoal(WeightedGoal g) {
        this.goals.add(g);
        updateGoalDirection();
    }

    public void setWeightedGoals(WeightedGoal... g) {
        this.goals.clear();
        Collections.addAll(this.goals, g);
        updateGoalDirection();
    }

    public void setWeightedGoals(ArrayList<WeightedGoal> g) {
        this.goals.clear();
        this.goals.addAll(g);
        updateGoalDirection();
    }

    public void clearGoals() {
        this.goals.clear();
    }

    public Vector2 getMovementDirection() {
        return goalDirection.cpy();
    }

    public Vector2 getMovementSpeedVector(float speed) {
        return goalDirection.cpy().scl(speed);
    }

    public void updateGoalDirection() {
        goalDirection.setZero();
        Vector2 position = new Vector2(actor.getX(Align.center), actor.getY(Align.center));
        for (WeightedGoal goal : goals)
            goalDirection.add(goal.getWeightedGoal(position));

        goalDirection.nor();
    }

    public ArrayList<WeightedGoal> getGoals() {
        return goals;
    }

    public boolean hasGoals() {
        return !goals.isEmpty();
    }

    public void removeGoalIfExists(Vector2 runawayFromObjectsVector) {
        for ( WeightedGoal goal : goals )
            if ( goal.getGoal().equals(runawayFromObjectsVector)) {
                goals.remove(goal);
                return;
            }
    }

    public void removeGoalIfExists(WeightedGoal goal) {
        for ( WeightedGoal g : goals )
            if ( g == goal) {
                goals.remove(goal);
                return;
            }
    }

    public void removeGoalsIfExist(ArrayList<WeightedGoal> goals) {
        for ( WeightedGoal g : goals)
            removeGoalIfExists(g);
    }

    public float getHighestWeight() {
        if ( goals.isEmpty() ) return 1.0f; /* FIXME: is this right? */
        float highestWeight = goals.get(0).getWeight();
        for (int i = 1; i < goals.size(); i++)
            if ( goals.get(i).getWeight() > highestWeight )
                highestWeight = goals.get(i).getWeight();

        return highestWeight;
    }

    public void addWeightedGoals(ArrayList<WeightedGoal> goals) {
        for ( WeightedGoal goal : goals)
            addGoal(goal);
    }
}

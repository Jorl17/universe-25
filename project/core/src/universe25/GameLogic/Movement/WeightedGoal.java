package universe25.GameLogic.Movement;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.ValuePositionPair;

/**
 * Created by jorl17 on 07/08/15.
 */
public class WeightedGoal {
    private Vector2 goal;
    private float weight;

    public WeightedGoal(Vector2 goal, float weight) {
        setGoalAndWeight(goal, weight);
    }

    public Vector2 getGoal() {
        return goal;
    }

    public void setGoal(Vector2 goal) {
        this.goal = goal;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setGoalAndWeight(Vector2 goal, float weight) {
        this.goal = goal;
        this.weight = weight;
    }

    public Vector2 getWeightedGoal(Vector2 src) {
        return goal.cpy().sub(src).nor().scl(weight);
    }

    public static WeightedGoal fromFloatValuePositionPair(ValuePositionPair<Float> pair) {
        return new WeightedGoal(pair.getPosition(), pair.getValue());
    }
}

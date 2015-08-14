package universe25.Agents.SimplisticAnt;

import com.badlogic.gdx.graphics.Color;
import universe25.Agents.States.*;
import universe25.Agents.States.SimplisticAntStates.CircleForPheromone;
import universe25.Agents.States.SimplisticAntStates.GoToFood;
import universe25.Agents.States.SimplisticAntStates.GoToPheromone;
import universe25.GameLogic.NumberProducers.GaussianFloatProducer;
import universe25.GameLogic.NumberProducers.GaussianLongProducer;
import universe25.Worlds.GridLayers.FloatLayer;

/**
 * Created by jorl17 on 08/08/15.
 */
public class PheromoneFollowingAnt extends SimplisticAnt {
    private static float fov=60;
    private static float seeDistance=50;
    private static float speed=1.0f;
    private static int   movesMemorySize=500;
    private static float pathPheromoneIncrease=5/*1*/;
    private static float floatPheromoneIncreaseWhenSeeingFood=pathPheromoneIncrease*5;
    private static float floatPheromoneIncreaseWhenSeeingFoodPheromone=floatPheromoneIncreaseWhenSeeingFood/50.0f;
    public PheromoneFollowingAnt() {
        //super(30, 150, 1, 1, 15);

        super(fov, seeDistance, speed, movesMemorySize, pathPheromoneIncrease, floatPheromoneIncreaseWhenSeeingFood,
              floatPheromoneIncreaseWhenSeeingFoodPheromone);
    }

    @Override
    protected void prepareStates() {
        long wanderDirectionChangeIntervalMs = 100;
        float wanderMaxAllowedChangeDegree = 80;
        float wanderWhenSeeingPathPheromoneProbability = 0.2f;
        float wanderWhenSeeingFoodPheromoneProbability = 0.05f;
        long rampageActivationTimeMean = 3000L;
        long rampageActivationTimeStd = 500L;
        long rampageDeactivationTimeMean = 1000L;
        long rampageDeactivationTimeStd = 300L;
        PriorityAggregatorState priorityAggregatorStates = new PriorityAggregatorState<>(this, "prioritisedStates");

        boolean orangeSpecies = Math.random() > 0.5f;

        if ( orangeSpecies ) {
            setColor(Color.ORANGE);
            priorityAggregatorStates.addState(new WanderAvoidingObstacles<>(this, 15,
                    wanderMaxAllowedChangeDegree, new GaussianFloatProducer(3.0f, 0.25f), false));
        } else
                priorityAggregatorStates.addState(new Wander<>(this,
                                                       wanderDirectionChangeIntervalMs,
                                                       wanderMaxAllowedChangeDegree,
                                                       wanderWhenSeeingPathPheromoneProbability, 15));
        priorityAggregatorStates.addState(new GoToPheromone(this, 10, pathPheronome));
        priorityAggregatorStates.addState(new GoToPheromone(this, 16, foodPheromone));
        priorityAggregatorStates.addState(new Wander<>(this,
                                                       wanderDirectionChangeIntervalMs,
                                                       wanderMaxAllowedChangeDegree,
                                                       wanderWhenSeeingFoodPheromoneProbability, 17));
        priorityAggregatorStates.addState(new TickBasedPriorityStateActivator<>(this, "RampageForFood", 19,
                                          new ParallelPriorityStates<>(this, "RampageAndChangeColor",
                                                  orangeSpecies ?
                                                                       new Wander<>(this, wanderDirectionChangeIntervalMs,
                                                                                          wanderMaxAllowedChangeDegree)
                                                  : new WanderAvoidingObstacles<>(this, 15,
                                                          wanderMaxAllowedChangeDegree, () -> 1.0f, false),
                                                                       new ChangeColorState<>(this, orangeSpecies ?
                                                                               Color.GREEN :
                                                                               Color.CYAN)),
                                                                   PheromoneFollowingAnt.this::areThereCellsWithFood,
                                                                   new GaussianLongProducer(rampageActivationTimeMean,
                                                                                            rampageActivationTimeStd),
                                                                   new GaussianLongProducer(rampageDeactivationTimeMean,
                                                                                            rampageDeactivationTimeStd),
                                                                   true));
        priorityAggregatorStates.addState(new GoToPheromone(this, 20, foodImmediancyPheromone));
        priorityAggregatorStates.addState(new CircleForPheromone(this, "CircleLookingForFood", 21,
                foodImmediancyPheromone, 5, () -> 10L, true, -1/*0.25f*/ /* Because add rate is 1, we remove 25% */));
        priorityAggregatorStates.addState(new GoToFood(this, 22));
        states.addState(priorityAggregatorStates);
    }

    private boolean first = true;
    @Override
    public void update() {
        super.update();
        if ( ((FloatLayer)getWorld().getGridLayers().get("FoodLayer")).getValueAt(getPosition()) > 0 && first) {
            //getWorld().getActors().removeValue(this, false);

            //System.out.println(getMovesMemory().cpy().reverse());
            testDoMoveSequence(getMovesMemory().cpy().reverse());

            first = false;
            //getGoalMovement().clearGoals();
            //states.clearStates();
        }
    }
}

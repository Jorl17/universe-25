package universe25.Agents.SimplisticAnt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import universe25.Agents.SimplisticAnt.States.*;
import universe25.Agents.Stackable.Food.Food;
import universe25.Agents.Stackable.Food.StackableSourceQuantityPair;
import universe25.Agents.Stackable.StackableUtils;
import universe25.Agents.States.*;
import universe25.GameLogic.NumberProducers.GaussianFloatProducer;
import universe25.GameLogic.NumberProducers.GaussianLongProducer;
import universe25.Objects.Crumbs;

/**
 * Created by jorl17 on 08/08/15.
 */
public class PheromoneFollowingAnt extends SimplisticAnt {
    private static SimplisticAntSpeciesParameters defaultSpeciesParameters = new SimplisticAntSpeciesParameters(
            /*fov*/60,
            /*seeDistance*/50,
            /*speed*/1.0f,
            /*pathPheromoneIncrease*/5,
            /*foodPheromoneIncreaseWhenSeeingFood*/5*5,
            /*hivePheromoneIncreaseWhenSeeingHive*/2
    );

    public PheromoneFollowingAnt(SimplisticAntSpecies species, SimplisticAntSpeciesParameters parameters) {
        super(species, parameters);
    }

    public PheromoneFollowingAnt(SimplisticAntSpecies species) {
        this(species, defaultSpeciesParameters);
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
        PriorityAggregatorState searchForFood = new PriorityAggregatorState<>(this, "searchForFood");

        boolean orangeSpecies = Math.random() > 0.5f;

        StateWithPriority<PheromoneFollowingAnt> wanderState, wanderStateHighestPrioTanFollowPathPheromoneState;
        GoToPheromone followPathPheromoneState;
        if ( orangeSpecies ) {
            setColor(Color.ORANGE);
            searchForFood.addState(wanderState = new WanderAvoidingObstacles<>(this, 15,
                    wanderMaxAllowedChangeDegree, new GaussianFloatProducer(3.0f, 0.25f), false));
        } else
                searchForFood.addState(wanderState=new Wander<>(this,
                                                       wanderDirectionChangeIntervalMs,
                                                       wanderMaxAllowedChangeDegree,
                                                       wanderWhenSeeingPathPheromoneProbability, 15));

        searchForFood.addState(followPathPheromoneState=new GoToPheromone(this, 10, pathPheronome));
        searchForFood.addState(new GoToPheromone(this, 16, foodPheromone));
        searchForFood.addState(wanderStateHighestPrioTanFollowPathPheromoneState=new Wander<>(this,
                wanderDirectionChangeIntervalMs,
                wanderMaxAllowedChangeDegree,
                wanderWhenSeeingFoodPheromoneProbability, 17));
        searchForFood.addState(new TickBasedPriorityStateActivator<>(this, "RampageForFood", 19,
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
        searchForFood.addState(new GoToPheromone(this, 20, foodImmediancyPheromone));
        searchForFood.addState(new CircleForPheromone(this, "CircleLookingForFood", 21,
                foodImmediancyPheromone, 5, () -> 10L, true, -1/*0.25f*/ /* Because add rate is 1, we remove 25% */));
        searchForFood.addState(new GoToFood(this, 22));

        PriorityAggregatorState takeFoodBack = new PriorityAggregatorState<>(this, "TakeFoodBack");
        takeFoodBack.addState(wanderState);
        takeFoodBack.addState(followPathPheromoneState);
        takeFoodBack.addState(wanderStateHighestPrioTanFollowPathPheromoneState);
        takeFoodBack.addState(new GoToPheromone(this, 19, hivePheromone));
        takeFoodBack.addState(new GoToHive(this, 20));
        SequentialStatesWithPriority normalOperation = new SequentialStatesWithPriority<>(this, "NormalOperation",
                -1, false, SequentialStatesWithPriority.RestartMode.REENTER_FIRST_STATE_CHECK_CONDITIONS_FIRST);
        normalOperation.addState(searchForFood, null);
        normalOperation.addState(takeFoodBack, this::hasFood);
        normalOperation.addEndingConditionActionPair(() -> !isOutsideHive(), () -> getGoalMovement().clearGoals() );

        //states.addState(searchForFood);
        states.addState(new RootState<>(this, "Root",
                new ParallelPriorityStates<>(this, "Parallel States",
                normalOperation,new AvoidAntPoison(this, 22))));
    }

    private boolean hasFood() {
        //System.out.println("Check eh =" + first);
        return !first;
    }

    private boolean first = true;
    private Vector2 tmpPos = new Vector2();
    @Override
    public void update() {
        super.update();
        tmpPos.set(getX(Align.center), getY(Align.center));
        StackableSourceQuantityPair stackbles = getWorld().getStacksLayer().getValueAt(tmpPos.x, tmpPos.y);
        if ( StackableUtils.hasFood(stackbles) && first) {
            getWorld().getStacksLayer().decreaseQuantityAt(tmpPos.x, tmpPos.y, 10);
            //getWorld().getActors().removeValue(this, false);

            //System.out.println(getMovesMemory().cpy().reverse());
            //testDoMoveSequence(getMovesMemory().cpy().reverse());
            getStack().add(new Crumbs((Food)stackbles.getSource(), 10));

            first = false;

            //getGoalMovement().clearGoals();
            //states.clearStates();
        }
    }
}

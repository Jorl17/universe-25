package universe25.Agents.SimplisticAnt;

import com.badlogic.gdx.graphics.Color;
import universe25.Agents.SimplisticAntSpeciesParameters;
import universe25.Agents.States.*;
import universe25.Agents.SimplisticAnt.States.GoToFood;

/**
 * Created by jorl17 on 06/08/15.
 */
public class ScouterAnt extends SimplisticAnt {
    private static SimplisticAntSpeciesParameters defaultSpeciesParameters = new SimplisticAntSpeciesParameters(
            /*fov*/90,
            /*seeDistance*/70,
            /*speed*/3,
            /*pathPheromoneIncrease*/3,
            /*floatPheromoneIncreaseWhenSeeingFood*/3*8
    );

    protected ScouterAnt(SimplisticAntSpecies species, SimplisticAntSpeciesParameters parameters) {
        super(species, parameters);
        setColor(new Color(1.0f,0.0f,0.0f,1.0f));
        foodImmediancyPheromoneController = null;
    }

    public ScouterAnt(SimplisticAntSpecies species) {
        this(species, defaultSpeciesParameters);
    }

    @Override
    protected void prepareStates() {
        PriorityAggregatorState priorityAggregatorStates = new PriorityAggregatorState(this, "prioritisedStates");
        priorityAggregatorStates.addState(new Wander<>(this, 100, 80, 0.2f, 15));
        priorityAggregatorStates.addState(new GoToFood(this, 20));
        states.addState(priorityAggregatorStates);
    }
}

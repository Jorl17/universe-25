package universe25.Agents.SimplisticAnt;

import universe25.Agents.SpeciesParameters.FloatSpeciesParameter;
import universe25.Agents.SpeciesParameters.IntSpeciesParameter;
import universe25.Agents.SpeciesParameters.SpeciesParameter;
import universe25.Agents.SpeciesParameters.SpeciesParameters;

import java.util.Map;

/**
 * Created by jorl17 on 17/08/15.
 */
public class SimplisticAntSpeciesParameters extends SpeciesParameters {
    protected SimplisticAntSpeciesParameters(Map<String, SpeciesParameter> parameters) {
        super(parameters);
    }

    public SimplisticAntSpeciesParameters(float fov, float seeDistance, float speed,
                                          float pathPheromoneIncrease, float foodPheromoneIncreaseWhenSeeingFood) {
        super();
        add("fov", new FloatSpeciesParameter(fov, 1.0f, 0.5f));
        add("seeDistance", new FloatSpeciesParameter(seeDistance, 20.0f, 2.0f));
        add("speed", new FloatSpeciesParameter(speed, 0.5f, 0.1f));
        add("pathPheromoneIncrease", new FloatSpeciesParameter(pathPheromoneIncrease, 0.5f, 0.1f));
        add("foodPheromoneIncreaseWhenSeeingFood", new FloatSpeciesParameter(foodPheromoneIncreaseWhenSeeingFood, 0.5f, 0.1f));
        add("movesMemorySize", new IntSpeciesParameter(500, 50, 10));
    }

    @Override
    public SpeciesParameters recombine(SpeciesParameter o) {
        //FIXME: Handle this later
        return null;
    }

    @Override
    public SpeciesParameters cpy() {
        return new SimplisticAntSpeciesParameters(getParameters());
    }
}

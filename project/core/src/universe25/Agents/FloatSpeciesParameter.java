package universe25.Agents;

import universe25.GameLogic.NumberProducers.GaussianFloatProducer;
import universe25.GameLogic.NumberProducers.NumberProducer;

/**
 * Created by jorl17 on 17/08/15.
 */
public class FloatSpeciesParameter extends SpeciesParameter<Float> {
    private NumberProducer<Float> change;

    public FloatSpeciesParameter() {
        super();
    }

    public FloatSpeciesParameter(Float parameter, NumberProducer<Float> change) {
        super(parameter);
        this.change = change;
    }

    public FloatSpeciesParameter(Float parameter, float mutationMean, float mutationStd) {
        super(parameter);
        change = new GaussianFloatProducer(mutationMean, mutationStd, false);
    }

    @Override
    protected void randomize() {
        //FIXME
        change = new GaussianFloatProducer(5f, 1.0f, false);
    }

    @Override
    public FloatSpeciesParameter recombine(SpeciesParameter<Float> other) {
        //FIXME maybe change

        // FIXME: We are passing this parameter generator to avoid blowing up the number of random generator instances
        // Perhaps this is suboptimal but fuck it
        return new FloatSpeciesParameter(0.5f * (other.get() + get()), change);
    }

    @Override
    public FloatSpeciesParameter mutate(float mutationFactor) {
        //FIXME probably change

        // FIXME: We are passing this parameter generator to avoid blowing up the number of random generator instances
        // Perhaps this is suboptimal but fuck it
        return new FloatSpeciesParameter(get() + change.produce(), change);
    }

    @Override
    public FloatSpeciesParameter cpy() {
        return new FloatSpeciesParameter(get(), change);
    }
}

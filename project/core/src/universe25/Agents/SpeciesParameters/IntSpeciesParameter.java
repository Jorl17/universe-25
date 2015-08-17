package universe25.Agents.SpeciesParameters;

import universe25.GameLogic.NumberProducers.GaussianIntegerProducer;
import universe25.GameLogic.NumberProducers.NumberProducer;

/**
 * Created by jorl17 on 17/08/15.
 */
public class IntSpeciesParameter extends SpeciesParameter<Integer> {
    private NumberProducer<Integer> change;

    public IntSpeciesParameter() {
        super();
    }

    public IntSpeciesParameter(int parameter, NumberProducer<Integer> change) {
        super(parameter);
        this.change = change;
    }

    public IntSpeciesParameter(int parameter, int mutationMean, int mutationStd) {
        super(parameter);
        change = new GaussianIntegerProducer(mutationMean, mutationStd, false);
    }

    @Override
    protected void randomize() {
        //FIXME
        change = new GaussianIntegerProducer(5, 1, false);
    }

    @Override
    public IntSpeciesParameter recombine(SpeciesParameter<Integer> other) {
        //FIXME maybe change

        // FIXME: We are passing this parameter generator to avoid blowing up the number of random generator instances
        // Perhaps this is suboptimal but fuck it
        return new IntSpeciesParameter((int) (0.5f * (other.get() + get())), change);
    }

    @Override
    public IntSpeciesParameter mutate(float mutationFactor) {
        //FIXME probably change

        // FIXME: We are passing this parameter generator to avoid blowing up the number of random generator instances
        // Perhaps this is suboptimal but fuck it
        return new IntSpeciesParameter(get() + change.produce(), change);
    }

    @Override
    public IntSpeciesParameter cpy() {
        return new IntSpeciesParameter(get(), change);
    }
}

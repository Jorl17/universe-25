package universe25.Agents.SpeciesParameters;

/**
 * Created by jorl17 on 17/08/15.
 */
public abstract class SpeciesParameter<T> {
    private T parameter;

    public SpeciesParameter() {
        randomize(); //FIXME: I don't really like this randomize stuff. So I'll mostly just go around it while puking
    }

    public SpeciesParameter(T parameter) {
        this.parameter = parameter;
    }

    public T get() {
        return parameter;
    }

    public void set(T parameter) {
        this.parameter = parameter;
    }

    protected abstract void randomize();

    public abstract SpeciesParameter<T> recombine(SpeciesParameter<T> other);

    public abstract SpeciesParameter<T> mutate(float mutationFactor);

    public abstract SpeciesParameter<T> cpy();
}

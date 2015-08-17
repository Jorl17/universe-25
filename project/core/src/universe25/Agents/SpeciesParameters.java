package universe25.Agents;

/**
 * Created by jorl17 on 17/08/15.
 */
public abstract class SpeciesParameters<T extends SpeciesAgent> {
    public abstract void randomize();
    public abstract void reset();
}

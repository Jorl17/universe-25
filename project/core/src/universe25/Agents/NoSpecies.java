package universe25.Agents;

/**
 * Created by jorl17 on 17/08/15.
 */
public class NoSpecies extends Species {
    public NoSpecies() {
        super("NoSpecies");
    }

    @Override
    public Species newIndividual() {
        return null;
    }
}

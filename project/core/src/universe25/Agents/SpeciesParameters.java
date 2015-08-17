package universe25.Agents;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jorl17 on 17/08/15.
 */
public abstract class SpeciesParameters {
    private Map<String, SpeciesParameter> parameters;
    public abstract SpeciesParameters recombine(SpeciesParameter o);
    public abstract SpeciesParameters cpy();

    protected SpeciesParameters(Map<String, SpeciesParameter> parameters) {
        this.parameters = new HashMap<>();
        for ( String key : parameters.keySet() )
            this.parameters.put(key, parameters.get(key).cpy());

    }

    public SpeciesParameters() {
        this.parameters = new HashMap<>();
    }

    public SpeciesParameter get(String name) {
        return parameters.get(name);
    }

    protected void add(String name, SpeciesParameter parameter) {
        parameters.put(name, parameter);
    }

    protected Map<String, SpeciesParameter> getParameters() {
        return parameters;
    }
}

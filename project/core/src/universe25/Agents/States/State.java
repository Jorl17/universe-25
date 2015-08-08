package universe25.Agents.States;

import universe25.Agents.Agent;

/**
 * Created by jorl17 on 08/08/15.
 */
public abstract class State {
    public final Agent agent;
    private final String name;

    protected State(Agent agent, String name) {
        this.agent = agent;
        this.name = name;
    }

    protected String sameState() { return null; }

    public abstract String update();

    public String getName() {
        return name;
    }

    public abstract void leaveState();
    public abstract void enterState();
}

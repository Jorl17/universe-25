package universe25.Agents.States;

import universe25.Agents.Agent;

/**
 * Created by jorl17 on 08/08/15.
 */
public abstract class StateWithPriority<T extends Agent> extends State<T> {
    private int priority;
    protected StateWithPriority(T agent, String name, int priority) {
        super(agent, name);
        this.priority = priority;
    }

    protected StateWithPriority(T agent, String name) {
        this(agent,name,0);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void makeUnreachable() {
        setPriority(-1);
    }

    public abstract void updatePriority();
}

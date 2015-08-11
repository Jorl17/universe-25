package universe25.Agents.States;

import universe25.Agents.Agent;

/**
 * Created by jorl17 on 12/08/15.
 */
public abstract class ToggablePriorityState<T extends Agent> extends StateWithPriority<T> {
    private int priorityWhenToggled;
    protected ToggablePriorityState(T agent, String name, int priorityWhenToggled) {
        super(agent, name, -1);
        this.priorityWhenToggled = priorityWhenToggled;
        makeUnreachable();
    }

    public void makeReachable() {
        setPriority(this.priorityWhenToggled);
    }

    public void giveLowestPriority() {
        setPriority(0);
    }
}

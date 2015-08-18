package universe25.Agents.States;

import universe25.Agents.Agent;

/**
 * Created by jorl17 on 18/08/15.
 */
public class RootState<T extends Agent> extends PriorityAggregatorState<T> {
    public RootState(T agent, String name) {
        super(agent, name);
    }

    public RootState(T agent, String name, StateWithPriority... states) {
        super(agent, name, states);
    }

    @Override
    public String update() {
        super.updateStatePriorities();
        return super.update();
    }
}

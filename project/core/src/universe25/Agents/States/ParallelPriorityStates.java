package universe25.Agents.States;

import universe25.Agents.Agent;

/**
 * Created by jorl17 on 09/08/15.
 */
public class ParallelPriorityStates<T extends Agent> extends CompositeStateWithPriority<T> {
    protected ParallelPriorityStates(T agent, String name) {
        super(agent, name);
    }

    public ParallelPriorityStates(T agent, String name, StateWithPriority... states) {
        super(agent, name, states);
    }

    @Override
    public String update() {
        for ( StateWithPriority s : getSubStates() )
            s.update();
        return null; //FIXME: So we basically never exit it?
    }

    @Override
    public void leaveState() {
        for ( StateWithPriority s : getSubStates() )
            s.leaveState();
    }

    @Override
    public void enterState() {
        for ( StateWithPriority s : getSubStates() )
            s.enterState();
    }
}

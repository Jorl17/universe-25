package universe25.Agents.States;

import universe25.Agents.Agent;

import java.util.ArrayList;

/**
 * Created by jorl17 on 09/08/15.
 */
public abstract class CompositeStateWithPriority<T extends Agent> extends ToggablePriorityState<T> {
    private ArrayList<StateWithPriority> subStates = new ArrayList<>();
    protected CompositeStateWithPriority(T agent, String name, int priorityWhenToggled) {
        super(agent, name, priorityWhenToggled);
    }
    protected CompositeStateWithPriority(T agent, String name) {
        this(agent, name, -1);
    }

    protected CompositeStateWithPriority(T agent, String name, int priorityWhenToggled, StateWithPriority... states) {
        super(agent, name, priorityWhenToggled);

        for ( StateWithPriority s : states )
            addState(s);
    }
    protected CompositeStateWithPriority(T agent, String name, StateWithPriority... states) {
        this(agent, name, -1, states);
    }

    public void addState(StateWithPriority state) {
        subStates.add(state);
    }

    private int findState(String name) {
        //FIXME: Slow
        for (int i = 0; i < subStates.size(); i++)
            if ( subStates.get(i).getName().equals(name) )
                return i;

        return -1;
    }

    public void setStatePriority(String name, int priority) {
        int i = findState(name);
        assert ( i != -1 );

        subStates.get(i).setPriority(priority);
    }

    public void setStatePriority(State state, int priority) {
        setStatePriority(state.getName(), priority);
    }

    public void makeStateUnreachable(String name) {
        setStatePriority(name, -1);
    }

    public void makeStateUnreachable(State state) {
        makeStateUnreachable(state.getName());
    }

    protected int findHighestPriorityState() {
        int maxPrio = -1;
        int index = -1;
        for (int i = 0; i < subStates.size(); i++)
            if ( subStates.get(i).getPriority() > maxPrio ) {
                maxPrio = subStates.get(i).getPriority();
                index = i;
            }

        return index;
    }

    protected void updateStatePriorities() {
        for ( StateWithPriority s : subStates )
            s.updatePriority();
    }

    protected ArrayList<StateWithPriority> getSubStates() {
        return subStates;
    }
}

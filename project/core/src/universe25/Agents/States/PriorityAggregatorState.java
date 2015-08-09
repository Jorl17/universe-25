package universe25.Agents.States;

import universe25.Agents.Agent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 08/08/15.
 */
public class PriorityAggregatorState extends State {


    private ArrayList<StateWithPriority> subStates;
    private int currentState;

    public PriorityAggregatorState(Agent agent, String name) {
        super(agent, name);
        subStates = new ArrayList<>();
        currentState = -1;
    }

    public PriorityAggregatorState(Agent agent, String name, StateWithPriority... states) {
        super(agent, name);
        subStates = new ArrayList<>();
        currentState = -1;

        for ( StateWithPriority s : states )
            addState(s);
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

    private int findHighestPriorityState() {
        int maxPrio = -1;
        int index = -1;
        for (int i = 0; i < subStates.size(); i++)
            if ( subStates.get(i).getPriority() > maxPrio ) {
                maxPrio = subStates.get(i).getPriority();
                index = i;
            }

        return index;
    }

    private void enterHighestPriorityState() {
        int highPrio = findHighestPriorityState();
        if ( highPrio == -1 ) {
            assert( currentState == -1 );
            return;
        }

        if ( highPrio == currentState ) return;

        if ( currentState != -1 )
            subStates.get(currentState).leaveState();
        currentState = highPrio;
        subStates.get(currentState).enterState();
    }

    public void updateStatePriorities() {
        for ( StateWithPriority s : subStates )
            s.updatePriority();
    }

    @Override
    public String update() {
        updateStatePriorities();
        //System.out.println(subStates);
        enterHighestPriorityState();
        if ( currentState != -1 )
            return subStates.get(currentState).update();
        return null;
    }

    @Override
    public void leaveState() {
        if ( currentState != -1 )
            subStates.get(currentState).leaveState();
    }

    @Override
    public void enterState() {
        enterHighestPriorityState();
    }

    public String getCurrentStateName() {
        if ( currentState == -1 ) return null;
        return subStates.get(currentState).getName();
    }
}

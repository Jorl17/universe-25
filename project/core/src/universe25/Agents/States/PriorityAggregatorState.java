package universe25.Agents.States;

import universe25.Agents.Agent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 08/08/15.
 */
public class PriorityAggregatorState<T extends Agent> extends CompositeStateWithPriority<T> {
    private int currentState;

    public PriorityAggregatorState(T agent, String name) {
        super(agent, name);
        currentState = -1;
    }

    public PriorityAggregatorState(T agent, String name, StateWithPriority... states) {
        super(agent, name, states);
    }

    private void enterHighestPriorityState() {
        int highPrio = findHighestPriorityState();
        if ( highPrio == -1 ) {
            assert( currentState == -1 );
            return;
        }

        if ( highPrio == currentState ) return;

        if ( currentState != -1 )
            getSubStates().get(currentState).leaveState();
        currentState = highPrio;
        getSubStates().get(currentState).enterState();
    }

    @Override
    public String update() {
        updateStatePriorities();
        //System.out.println(subStates);
        enterHighestPriorityState();
        if ( currentState != -1 )
            return getSubStates().get(currentState).update();
        return null;
    }

    @Override
    public void leaveState() {
        if ( currentState != -1 )
            getSubStates().get(currentState).leaveState();
    }

    @Override
    public void enterState() {
        enterHighestPriorityState();
    }

    public String getCurrentStateName() {
        if ( currentState == -1 ) return null;
        return getSubStates().get(currentState).getName();
    }
}

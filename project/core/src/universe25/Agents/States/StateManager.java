package universe25.Agents.States;

import universe25.Agents.Agent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 08/08/15.
 */
public class StateManager {
    private final ArrayList<State> states = new ArrayList<>();
    private int currState;
    private Agent agent;

    public StateManager(Agent agent, State... states) {
        this.agent = agent;
        addStates(states);
        currState = 0;
        this.states.get(currState).enterState();
    }

    public StateManager(Agent agent) {
        this.agent = agent;
        currState = -1;
    }

    private int findState(String state) {
        return state.indexOf(state); //FIXME: Will this always work? references and stuff...
    }

    public boolean update() {
        if ( states.isEmpty() ) return false;

        String newState = states.get(currState).update();
        if ( newState != null ) {
            changeState(newState);
            return true;
        }
        return false;
    }

    public void changeState(String newState) {
        states.get(currState).leaveState();
        currState = findState(newState);
        assert ( currState > 0 );
        assert ( currState < states.size() );
        states.get(currState).enterState();
    }



    public void addState(State state) {
        states.add(state);
        if ( currState == -1 ) {
            currState = 0;
            state.enterState();
        }
    }

    public void addStates(State... states) {
        assert( states.length > 0) ;
        Collections.addAll(this.states, states);
        if ( currState == -1 ) {
            currState = 0;
            states[0].enterState();
        }
    }
}

package universe25.Agents.States;

import universe25.Agents.Agent;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

/**
 * Created by jorl17 on 18/08/15.
 */
public class SequentialStatesWithPriority<T extends Agent> extends CompositeStateWithPriority<T> {

    public enum RestartMode { REENTER_LAST_STATE, REENTER_FIRST_STATE_CHECK_CONDITIONS_FIRST};
    private int currentState;
    private ArrayList<BooleanSupplier> conditions;
    private boolean highestPrioDecidedByChildren;
    private boolean loop;
    private RestartMode restartMode;

    public SequentialStatesWithPriority(T agent, String name, int priorityWhenToggled, boolean highestPrioDecidedByChildren, boolean loop, RestartMode restartMode) {
        super(agent, name, priorityWhenToggled);
        this.highestPrioDecidedByChildren = highestPrioDecidedByChildren;
        this.loop = loop;
        this.restartMode = restartMode;
        this.conditions = new ArrayList<>();
    }

    public void addState(StateWithPriority state, BooleanSupplier enterCondition) {
        if ( numStates() == 0 ) assert ( enterCondition == null);
        addState(state);
        conditions.add(enterCondition);
    }

    @Override
    public void updatePriority() {
        if ( highestPrioDecidedByChildren && currentState != -1)
            setPriority(getSubStates().get(currentState).getPriority());
    }

    @Override
    public String update() {
        if ( currentState != -1 ) {
            if ( conditions.get(currentState).getAsBoolean() )
                getSubStates().get(currentState).leaveState();

            if ( ++currentState > numStates() ) {
                if (!loop) return null;
                else currentState = 0;
            }

            getSubStates().get(currentState).enterState();
        }

        return null;
    }

    @Override
    public void leaveState() {
        if ( currentState != -1 ) {
            getSubStates().get(currentState).leaveState();
            if ( restartMode == RestartMode.REENTER_FIRST_STATE_CHECK_CONDITIONS_FIRST )
                currentState = 0;
        }
    }

    private int getFirstUnmetCondition() {
        for (int i = 0; i < conditions.size(); i++)
            if ( !conditions.get(i).getAsBoolean() )
                return i;

        return -1;
    }

    @Override
    public void enterState() {
        if (currentState != -1 && restartMode == RestartMode.REENTER_FIRST_STATE_CHECK_CONDITIONS_FIRST) {
            int stateNumber = getFirstUnmetCondition()-1;
            if ( stateNumber < 0 ) {
                // All conditions were met ...
                if ( loop ) currentState = 0;
                else currentState = -1;
            }
        }

        if ( currentState != -1 ) {
            getSubStates().get(currentState).enterState();
        }
    }
}

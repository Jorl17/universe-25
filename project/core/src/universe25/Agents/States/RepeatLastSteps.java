package universe25.Agents.States;

import universe25.Agents.Agent;

/**
 * Created by jorl17 on 18/08/15.
 */
public class RepeatLastSteps<T extends Agent> extends DoMoveSequence<T> {
    private int numSteps;
    public RepeatLastSteps(T agent, String name, int priority, int numSteps, boolean restartIfLeftUnfinished) {
        super(agent, name, priority, null, restartIfLeftUnfinished);
        this.numSteps = numSteps;
    }

    @Override
    public void enterState() {
        super.enterState();
        if ( getMoveSequence() == null || doRestartIfLeftUnfinished())
            setMoveSequence(agent.getMovesMemory().last(numSteps).reverse().cpy());
    }

    @Override
    public void leaveState() {
        super.leaveState();
        if ( doRestartIfLeftUnfinished() )
            setMoveSequence(null);
    }
}

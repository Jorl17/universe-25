package universe25.Agents.States;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;
import universe25.GameLogic.Movement.MoveSequence.MoveSequence;

/**
 * Created by jorl17 on 12/08/15.
 */
public class DoMoveSequence<T extends Agent> extends StateWithPriority<T> {
    private MoveSequence moveSequence;
    private int currentMove;

    private boolean restartIfLeftUnfinished;
    public DoMoveSequence(T agent, String name, int priority, MoveSequence moveSequence, boolean restartIfLeftUnfinished) {
        super(agent, name, priority);
        this.moveSequence = moveSequence;
        this.restartIfLeftUnfinished = restartIfLeftUnfinished;
        this.currentMove = 0;
    }

    public DoMoveSequence(T agent, String name, MoveSequence moveSequence, boolean restartIfLeftUnfinished) {
        super(agent, name);
        this.moveSequence = moveSequence;
        this.restartIfLeftUnfinished = restartIfLeftUnfinished;
        this.currentMove = 0;
    }

    @Override
    public String update() {
        if ( currentMove  == this.moveSequence.numMoves()) {
            makeUnreachable();
            return null;
        }
        Vector2 pos = agent.getPosition();
        Vector2 destination = this.moveSequence.getMoveAt(currentMove);
        if ( pos.epsilonEquals(pos, 1E-1f) ) {
            if (++currentMove == this.moveSequence.numMoves()) {
                makeUnreachable();
                return null; // All done!
            }
            else
                destination = this.moveSequence.getMoveAt(currentMove);
        }

        agent.getGoalMovement().setGoal(destination);

        return null;
    }

    @Override
    public void leaveState() {
    }

    @Override
    public void enterState() {
    }

    @Override
    public void updatePriority() {

    }
}

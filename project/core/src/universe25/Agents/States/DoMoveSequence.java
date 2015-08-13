package universe25.Agents.States;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Agent;
import universe25.GameLogic.Movement.MoveSequence.MoveSequence;
import universe25.GameLogic.Movement.Pathfinding.GridCell;

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
        System.out.println();
        System.out.println(this.moveSequence.numMoves());
        System.out.println(currentMove);
        if ( this.moveSequence.numMoves() == 0 ) {
            makeUnreachable();
            return null;
        }
        if ( currentMove  == this.moveSequence.numMoves()) {
            makeUnreachable();
            agent.getGoalMovement().clearGoals();
            return null;
        }
        Vector2 pos = agent.getPosition();
        Vector2 destination = this.moveSequence.getMoveAt(currentMove);
        GridCell posGrid = agent.getWorld().getWorldObjectsLayer().getCell(pos.x,pos.y);
        GridCell destGrid = agent.getWorld().getWorldObjectsLayer().getCell(destination.x,destination.y);

        float cellSize = agent.getWorld().getWorldObjectsLayer().getCellSize();
        if ( //posGrid.equals(destGrid)
        pos.epsilonEquals(destination, cellSize *1.5f)
        ) {
            if (++currentMove == this.moveSequence.numMoves()) {
                makeUnreachable();
                return null; // All done!
            }
            else
                destination = this.moveSequence.getMoveAt(currentMove);
        }

        agent.getGoalMovement().setGoal(destination.cpy().add((float) Math.random() * cellSize * 2 - cellSize,
                (float) Math.random() * cellSize * 2 - cellSize));

        return null;
    }

    @Override
    public void leaveState() {
        if ( restartIfLeftUnfinished && currentMove  == this.moveSequence.numMoves()-1)
            currentMove = 0;
    }

    @Override
    public void enterState() {
    }

    @Override
    public void updatePriority() {

    }
}

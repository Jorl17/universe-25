package universe25.GameLogic.Movement.Pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;

/**
 * Created by jorl17 on 13/08/15.
 */
public class CellConnection implements Connection<Cell> {
    private Cell from, to;
    public CellConnection(Cell from, Cell to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public float getCost() {
        return 1; //FIXME: Not dealing with diagonals on purpose
    }

    @Override
    public Cell getFromNode() {
        return from;
    }

    @Override
    public Cell getToNode() {
        return to;
    }
}

package universe25.GameLogic.Movement.Pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;

/**
 * Created by jorl17 on 13/08/15.
 */
public class CellConnection implements Connection<GridCell> {
    private GridCell from, to;
    public CellConnection(GridCell from, GridCell to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public float getCost() {
        return 1; //FIXME: Not dealing with diagonals on purpose
    }

    @Override
    public GridCell getFromNode() {
        return from;
    }

    @Override
    public GridCell getToNode() {
        return to;
    }
}

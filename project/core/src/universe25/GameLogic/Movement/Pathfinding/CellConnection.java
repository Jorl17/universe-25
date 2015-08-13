package universe25.GameLogic.Movement.Pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;

/**
 * Created by jorl17 on 13/08/15.
 */
public class CellConnection implements Connection<GridCell> {
    private GridCell from, to;
    private float cost;
    public CellConnection(GridCell from, GridCell to, float cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    @Override
    public float getCost() {
        return cost;
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

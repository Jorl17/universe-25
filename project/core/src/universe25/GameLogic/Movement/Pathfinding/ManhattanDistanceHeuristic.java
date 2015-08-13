package universe25.GameLogic.Movement.Pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;

/**
 * Created by jorl17 on 13/08/15.
 */
public class ManhattanDistanceHeuristic implements Heuristic<Cell> {

    @Override
    public float estimate(Cell node, Cell endNode) {
        return Math.abs(endNode.getRow() - node.getRow()) + Math.abs(endNode.getCol() - node.getCol());
        //return (float) Math.sqrt((endNode.getRow() - node.getRow())*(endNode.getRow() - node.getRow()) + (endNode.getCol() - node.getCol())*(endNode.getCol() - node.getCol()));
    }
}

package universe25.GameLogic.Movement.Pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import universe25.GameLogic.Movement.MoveSequence.MoveSequence;
import universe25.Worlds.GridLayers.GridMapLayer;

/**
 * Created by jorl17 on 13/08/15.
 */
public class PathFinder {
    private GridMapLayer layer;
    private IndexedAStarPathFinder<GridCell> pathFinder;
    private GridMapLayerIndexedGraph graph;

    public PathFinder(GridMapLayer layer) {
        this.layer = layer;
        this.graph = new GridMapLayerIndexedGraph(layer);
        this.pathFinder = new IndexedAStarPathFinder<>(graph, false);
    }

    /*public DefaultGraphPath<Cell> getPath(Cell from, Cell to) {
        DefaultGraphPath<Cell> out = new DefaultGraphPath<>();
        pathFinder.searchNodePath(from, to, new ManhattanDistanceHeuristic(), out);
        return out;
    }*/

    public DefaultGraphPath<GridCell> getPath(Vector2 pos1, Vector2 pos2) {
        DefaultGraphPath<GridCell> out = new DefaultGraphPath<>();
        GridCell cell = layer.getCell(pos1.x, pos1.y);
        GridCell cell2 = layer.getCell(pos2.x, pos2.y);

        System.out.println(pathFinder.searchNodePath(cell, cell2, new ManhattanDistanceHeuristic(), out));
        return out;
    }

    public MoveSequence getPathMoveSequence(Vector2 pos1, Vector2 pos2) {
        DefaultGraphPath<GridCell> path = getPath(pos1, pos2);
        MoveSequence moveSequence = new MoveSequence();
        for ( GridCell c : path )
            moveSequence.addMove(layer.getCellCentre(c.getCol(), c.getRow()));
        System.out.println(moveSequence);
        return moveSequence;
    }
}

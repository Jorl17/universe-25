package universe25.GameLogic.Movement.Pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.DefaultIndexedGraph;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import universe25.GameLogic.Movement.MoveSequence.MoveSequence;
import universe25.Worlds.GridLayers.GridMapLayer;

import java.util.Vector;

/**
 * Created by jorl17 on 13/08/15.
 */
public class PathFinder {
    private GridMapLayer layer;
    private IndexedAStarPathFinder<Cell> pathFinder;
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

    public DefaultGraphPath<Cell> getPath(Vector2 pos1, Vector2 pos2) {
        DefaultGraphPath<Cell> out = new DefaultGraphPath<>();
        int[] cell = layer.getCell(pos1.x, pos1.y);
        int[] cell2 = layer.getCell(pos2.x, pos2.y);
        Cell[][] graphCells = layer.getGraphCells();
        System.out.println(pathFinder.searchNodePath(graphCells[cell[0]][cell[1]], graphCells[cell2[0]][cell2[1]], new ManhattanDistanceHeuristic(), out));
        return out;
    }

    public MoveSequence getPathMoveSequence(Vector2 pos1, Vector2 pos2) {
        DefaultGraphPath<Cell> path = getPath(pos1, pos2);
        MoveSequence moveSequence = new MoveSequence();
        for ( Cell c : path )
            moveSequence.addMove(layer.getCellCentre(c.getCol(), c.getRow()));
        System.out.println(moveSequence);
        return moveSequence;
    }
}

package universe25.Worlds.GridLayers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import javafx.geometry.BoundingBox;
import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.Objects.WorldObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jorl17 on 11/08/15.
 */
public class WorldObjectsLayer extends GridMapLayer<ArrayList> {
    public WorldObjectsLayer(float gridWidth, float gridHeight, float cellSize, String name, Color drawColor, boolean drawLayer) {
        super(ArrayList.class, gridWidth, gridHeight, cellSize, name, drawColor, drawLayer);
        this.nextCells = this.cells;
    }

    public WorldObjectsLayer(float cellSize, int nRows, int nCols, String name) {
        super(ArrayList.class, cellSize, nRows, nCols, name);
        this.nextCells = this.cells;
    }

    @Override
    public void onTickFinished() {
        assert ( false ) ; // YOU SHOULD NOT BE CALLING ONTICKFINISHED FOR A WORLDOBJECTSLAYER
    }

    public void addWorldObject(WorldObject o) {
        int[] minMaxRowCol = new int[4];
        Vector2[] boundingBoxPoints = o.getBoundingBoxPoints();
        getMinMaxRowCols(boundingBoxPoints, minMaxRowCol);
        int minCol = minMaxRowCol[0];
        int maxCol = minMaxRowCol[1];
        int minRow = minMaxRowCol[2];
        int maxRow = minMaxRowCol[3];

        BoundingBox bb = o.getBoundingBox();
        for (int i = minRow; i <= maxRow; i++)
            for (int j = minCol; j <= maxCol; j++) {
                BoundingBox cellBB = getCellBoundingBox(j,i);
                if (bb.intersects(cellBB) || bb.contains(cellBB) || cellBB.contains(bb)) {
                    getValueAtCell(j, i).add(o);
                }
            }
    }

    @Override
    protected void drawCell(Batch batch, int col, int row) {
        if ( !getValueAtCell(col,row).isEmpty() ) {
            getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
            getShapeRenderer().setColor(getDrawColor());
            getShapeRenderer().rect(col * cellSize, row * cellSize, cellSize, cellSize);
            getShapeRenderer().end();
        }
    }

    private boolean rayToCellHasIntersection(Vector2 position, GridCell cell, Set<WorldObject> objectsToTest ) {
        Vector2 cellCentre = cell.getCentre();
        for ( WorldObject object : objectsToTest )
            if ( Intersector.intersectSegmentPolygon(position, cellCentre, object.getBoundingBoxAsPolygon()) )
                return true;

        return false;
    }

    public ArrayList<GridCell> removeInvisibleCells(Vector2 position, ArrayList<GridCell> tmpCellsInFov) {
        ArrayList<GridCell> ret = new ArrayList<>();
        Set<WorldObject> objectsToTest = new HashSet<>();
        for (GridCell cell : tmpCellsInFov) {
            ArrayList<WorldObject> valueAtCell = getValueAtCell(cell);
            if (!valueAtCell.isEmpty()) {
                objectsToTest.addAll(valueAtCell);
                ret.add( cell );
            }
        }

        for (int i = 0; i < tmpCellsInFov.size(); i++) {
            GridCell cell = tmpCellsInFov.get(i);
            if ( rayToCellHasIntersection(position, cell, objectsToTest) ) {
                tmpCellsInFov.remove(i);
                i--;
            }
        }

        return ret;
    }
}

package universe25.Worlds.GridLayers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import javafx.geometry.BoundingBox;
import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.Objects.WorldObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jorl17 on 11/08/15.
 */
public class WorldObjectsLayer extends GridMapLayer<ArrayList> {
    private float[][] occlusionPercentages;
    public WorldObjectsLayer(float gridWidth, float gridHeight, float cellSize, String name, Color drawColor, boolean drawLayer) {
        super(ArrayList.class, gridWidth, gridHeight, cellSize, name, drawColor, drawLayer);
        this.nextCells = this.cells;
        createOcclusionPercentagesArray();
    }

    public WorldObjectsLayer(float cellSize, int nRows, int nCols, String name) {
        super(ArrayList.class, cellSize, nRows, nCols, name);
        this.nextCells = this.cells;
        createOcclusionPercentagesArray();
    }

    public void createOcclusionPercentagesArray() {
        this.occlusionPercentages = new float[nRows][nCols];
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
                    recalculateOcclusionPercentage(j,i);
                }
            }
    }

    @Override
    protected void drawCell(Batch batch, int col, int row) {
        if ( !getValueAtCell(col,row).isEmpty() ) {
            getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
            getShapeRenderer().setColor(getDrawColor().cpy().sub(0.0f,0.0f,0.0f,1- getOcclusionPercentage(col, row)));
            getShapeRenderer().rect(col * cellSize, row * cellSize, cellSize, cellSize);
            getShapeRenderer().end();

            /*for (WorldObject o : (ArrayList<WorldObject>)getValueAtCell(col, row)) {
                BoundingBox b= o.getBoundingBox();
                getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
                getShapeRenderer().setColor(Color.YELLOW.cpy().sub(0.0f,0.0f,0.0f,0.95f));
                getShapeRenderer().rect((float)b.getMinX(), (float)b.getMinY(), (float)b.getWidth(), (float)b.getHeight());
                getShapeRenderer().end();
            }*/

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

    public boolean hasObjects(int col, int row) {
        return !getValueAtCell(col, row).isEmpty();
    }

    public boolean hasObjects(GridCell cell) {
        return !getValueAtCell(cell).isEmpty();
    }

    public float getOcclusionPercentage(int col, int row) {
        return occlusionPercentages[row][col];
    }
    public float getOcclusionPercentage(GridCell c) {
        return getOcclusionPercentage(c.getCol(), c.getRow());
    }
    public float getOcclusionPercentage(float x, float y) {
        return occlusionPercentages[((int) (y / cellSize))][ (int) (x / cellSize)];
    }

    public void recalculateOcclusionPercentage(int col, int row) {
        ArrayList<WorldObject> objectsAtCell = getValueAtCell(col, row);
        BoundingBox cellBB = getCellBoundingBox(col,row);
        occlusionPercentages[row][col] = 0;
        for ( WorldObject o : objectsAtCell) {
            BoundingBox objBB = o.getBoundingBox();
            occlusionPercentages[row][col] +=
                    ((Math.min(cellBB.getMaxX(), objBB.getMaxX())) - (Math.max(cellBB.getMinX(), objBB.getMinX()))) *
                        ((Math.min(cellBB.getMaxY(), objBB.getMaxY())) - (Math.max(cellBB.getMinY(), objBB.getMinY())))
                    / (cellSize * cellSize) ;
        }

        if ( occlusionPercentages[row][col] > 1.0f ) occlusionPercentages[row][col] = 1.0f;

    }

    @Override
    public float getMoveCost(int col, int row) {
        return getOcclusionPercentage(col, row) < 0.30f ? 1 : -1; //FIXME Make this more flexible
    }
}

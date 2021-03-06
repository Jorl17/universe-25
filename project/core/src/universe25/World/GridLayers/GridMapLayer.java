package universe25.World.GridLayers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import javafx.geometry.BoundingBox;
import javafx.scene.control.Cell;
import universe25.Agents.ValuePositionPair;
import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.Utils.Scene2DShapeRenderer;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jorl17 on 07/08/15.
 */
public abstract class GridMapLayer<T> extends Actor {
    private final ShapeRenderer shapeRenderer;
    protected float gridWidth, gridHeight;
    protected float cellSize;
    protected int nRows, nCols;
    protected T[][] cells;
    protected T[][] nextCells;
    private Class<? extends T> cls;
    private final String name;
    private Color drawColor;
    private boolean drawLayer;

    private Vector2[][] cellCentres;

    protected GridCell[][] graphCells;

    public GridMapLayer(Class<? extends T> cls, float gridWidth, float gridHeight, float cellSize, String name, Color drawColor, boolean drawLayer) {
        this.cls = cls;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellSize = cellSize;
        nCols = Math.round(gridWidth / cellSize);
        nRows = Math.round(gridHeight / cellSize);
        this.name = name;
        this.drawColor = drawColor;
        this.drawLayer = drawLayer;

        shapeRenderer = new ShapeRenderer();

        createCellArray();
    }

    public GridMapLayer(Class<? extends T> cls, float cellSize, int nRows, int nCols, String name) {
        this.cls = cls;
        this.cellSize = cellSize;
        this.nRows = nRows;
        this.nCols = nCols;
        this.gridWidth = this.nCols * cellSize;
        this.gridHeight = this.nRows * cellSize;
        this.name = name;

        shapeRenderer = Scene2DShapeRenderer.getShapeRenderer();

        createCellArray();
    }

    private void createCellArray() {
        this.cells = createCells();
        this.nextCells = createCells();

        this.cellCentres = new Vector2[nRows][nCols];
        for (int i = 0; i < cellCentres.length; i++)
            for (int j = 0; j < cellCentres[0].length; j++)
                this.cellCentres[i][j] = new Vector2(j * cellSize + cellSize*0.5f, i * cellSize+ cellSize*0.5f);

        this.graphCells = new GridCell[nRows][nCols];
        for (int i = 0; i < graphCells.length; i++)
            for (int j = 0; j < graphCells[0].length; j++)
                this.graphCells[i][j] = new GridCell(this, j, i);

    }

    private T[][] createCells() {
        T[][] ret = (T[][]) Array.newInstance(cls, nRows, nCols);
        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                //FIXME: So hacky...
                try {
                    ret[i][j] = cls.newInstance();
                } catch (InstantiationException e) {
                    try {
                        ret[i][j] = (T) new Float(0);
                    } catch (Exception e2) {
                        ret[i][j] = (T) new Integer(0);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

        return ret;
    }

    public String getName() {
        return name;
    }

    public T getValueAt(float x, float y) {
        if ( x > gridWidth || x < 0 ) return null;
        if ( y > gridHeight|| y < 0 ) return null;

        return cells[(int) (y / cellSize)][(int) (x / cellSize)];
    }

    public T getValueAt(Vector2 pos) {
        return getValueAt(pos.x,pos.y);
    }

    public T getValueAtCell(int col, int row, T[][] cells) {
        if ( col < 0 || col > nCols ) return null;
        if ( row < 0 || row > nRows ) return null;

        return cells[row][col];
    }

    public T getValueAtCell(int col, int row) {
        return getValueAtCell(col, row, this.cells);
    }

    public void setValueAt(float x, float y, T val) {
        if ( x > gridWidth || x < 0 ) return;
        if ( y > gridHeight|| y < 0 ) return;

        nextCells[(int) (y / cellSize)][(int) (x / cellSize)] = val;
    }

    public void setValueAtCell(int col, int row, T val) {
        nextCells[row][col] = val;
    }

    public void setValueAtCell(GridCell cell, T val) {
        setValueAtCell(cell.getCol() ,cell.getRow(), val);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if ( drawLayer ) {
            batch.end();

            drawCellGrids(batch);
            drawCellBodies(batch);

            batch.begin();
        }
    }

    public void drawCellBodies(Batch batch, ArrayList<GridCell> cells, Color c) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        getShapeRenderer().setColor(c);
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        for ( GridCell cell : cells )
                drawCellBody(batch, cell.getCol(), cell.getRow());

        getShapeRenderer().end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    protected void drawCellBodies(Batch batch) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                drawCellBody(batch, j, i);

        getShapeRenderer().end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    protected void drawCellBody(Batch batch, int j, int i) {
        // Empty
    }

    protected void drawCellGrids(Batch batch) {
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(drawColor);
        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                drawCellGrid(batch, j, i);
        getShapeRenderer().end();
    }

    protected void drawCellGrid(Batch batch, int col, int row) {
        shapeRenderer.rect(col*cellSize, row*cellSize, cellSize, cellSize);
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    private void getMinMaxRowColsTriangle(Vector2[] triangle, int[]outColsRows) {
        int p0Col = (int) (triangle[0].x / cellSize), p0Row = (int) (triangle[0].y / cellSize),
                p1Col = (int) (triangle[1].x / cellSize), p1Row = (int) (triangle[1].y / cellSize),
                p2Col = (int) (triangle[2].x / cellSize), p2Row = (int) (triangle[2].y / cellSize);
        int minRow, minCol, maxRow, maxCol;
        minCol = Integer.min(p0Col, Integer.min(p1Col, p2Col ));
        maxCol = Integer.max(p0Col, Integer.max(p1Col, p2Col));
        minRow = Integer.min(p0Row, Integer.min(p1Row, p2Row ));
        maxRow = Integer.max(p0Row, Integer.max(p1Row, p2Row));

        minRow = Integer.max(0, minRow);
        minCol = Integer.max(0, minCol);
        maxRow = Integer.min(nRows-1, maxRow);
        maxCol = Integer.min(nCols-1, maxCol);

        outColsRows[0] = minCol;
        outColsRows[1] = maxCol;
        outColsRows[2] = minRow;
        outColsRows[3] = maxRow;
    }

    public ArrayList<GridCell> getGridCellsRectangle(int firstCol, int firstRow, int w, int h ) {
        ArrayList<GridCell> ret = new ArrayList<>();
        int startCol = Integer.max(firstCol, 0);
        int startRow = Integer.max(firstRow, 0);
        int lastCol = Integer.min(firstCol + w, nCols - 1);
        int lastRow = Integer.min(firstRow + h, nRows - 1);

        for (int i = startRow; i <= lastRow; i++)
            for (int j = startCol; j <= lastCol; j++)
                ret.add(getCellAt(j, i));

        //System.out.println(ret);
        return ret;
    }

    public void getMinMaxRowCols(Vector2[] points, int[] outColsRows ) {
        assert ( points.length > 0 );
        if ( points.length == 3 ) {
            getMinMaxRowColsTriangle(points, outColsRows);
        } else {
            int minCol = (int) (points[0].x / cellSize),
                    minRow = (int) (points[0].y / cellSize),
                    maxCol = minCol,
                    maxRow = minRow;

            int col, row;
            for (int i = 1; i < points.length; i++) {
                col = (int) (points[i].x / cellSize);
                row = (int) (points[i].y / cellSize);
                if (col < minCol) minCol = col;
                if (col > maxCol) maxCol = col;
                if (row < minRow) minRow = row;
                if (row > maxRow) maxRow = row;
            }

            outColsRows[0] = Integer.max(minCol, 0);
            outColsRows[1] = Integer.min(maxCol,nCols-1);
            outColsRows[2] = Integer.max(minRow,0);
            outColsRows[3] = Integer.min(maxRow,nRows-1);
        }
    }

    public ArrayList<GridCell> getCellsWithinTriangle(Vector2[] triangle) {
        int[] minMaxRowCol = new int[4];
        getMinMaxRowCols(triangle, minMaxRowCol);
        int minCol = minMaxRowCol[0];
        int maxCol = minMaxRowCol[1];
        int minRow = minMaxRowCol[2];
        int maxRow = minMaxRowCol[3];

        // Now search in those
        ArrayList<GridCell> ret = new ArrayList<>();
        for (int i = minRow; i <= maxRow; i++)
            for (int j = minCol; j <= maxCol; j++)
                if (Intersector.isPointInTriangle(cellCentres[i][j], triangle[0], triangle[1], triangle[2]))
                    ret.add(graphCells[i][j]);


        return ret;
    }

    public Vector2[][] getCellCentres() {
        return cellCentres;
    }

    public BoundingBox getCellBoundingBox(int col, int row) {
        Vector2 centre = cellCentres[row][col];
        float halfCellSize = cellSize/2;
        return new BoundingBox(centre.x-halfCellSize, centre.y-halfCellSize, cellSize, cellSize);
    }

    public Vector2 getCellCentre(int col, int row) {
        return cellCentres[row][col].cpy();
    }

    public ValuePositionPair<T> getCellCentreAndValue(int col, int row) {
        return new ValuePositionPair<T>(getValueAtCell(col, row), cellCentres[row][col].cpy());
    }

    public boolean isPointInCell(Vector2 pos, int col, int row) {
        /*
        Vector2 topLeft = new Vector2(col * cellSize, row * cellSize),
                topRight = new Vector2(col * cellSize, (row+1) * cellSize),
                bottomRight = new Vector2((col+1) * cellSize, (row+1) * cellSize),
                bottomLeft = new Vector2((col+1) * cellSize, row * cellSize);

        com.badlogic.gdx.utils.Array<Vector2> vector2s = new com.badlogic.gdx.utils.Array<>();
        vector2s.add(topLeft); vector2s.add(topRight); vector2s.add(bottomRight); vector2s.add(bottomLeft);
        return Intersector.isPointInPolygon(vector2s, pos);*/

        return getCellAt(col, row).equals(getCell(pos.x,pos.y));
        /*
        if ( pos.x > topRight.x || pos.x < topLeft.x ) return false;
        if ( pos.y > topRight.y || pos.y < bottomRight.y ) return false;

        return true;*/
    }

    public T[][] cloneCells() {
        T[][] ret = (T[][]) Array.newInstance(cls, nRows, nCols);
        for (int i = 0; i < nRows; i++)
            System.arraycopy(cells[i], 0, ret[i], 0, nCols);
        return ret;
    }

    public void onTickFinished() {
        for (int i = 0; i < nRows; i++)
            System.arraycopy(nextCells[i], 0, cells[i], 0, nCols);
    }

    public void setDrawLayer(boolean drawLayer) {
        this.drawLayer = drawLayer;
    }

    public void toggleDrawLayer() {
        drawLayer = !drawLayer;
    }

    protected Color getDrawColor() {
        return drawColor;
    }


    public GridCell getCell(float x, float y) {
        if ( x > gridWidth || x < 0 ) return null;
        if ( y > gridHeight|| y < 0 ) return null;
        return this.graphCells[(int) (y / cellSize)][(int) (x / cellSize)];
    }

    public int getIndex(int col, int row) {
        return row*getNumCols() + col;
    }

    public float getCellSize() {
        return cellSize;
    }

    public int getNumCols() {
        return nCols;
    }

    public int getNumRows() {
        return nRows;
    }

    public GridCell[][] getGraphCells() {
        return graphCells;
    }

    public Vector2 getCellCentre(GridCell gridCell) {
        return getCellCentre(gridCell.getCol(), gridCell.getRow());
    }

    public boolean isPointInCell(Vector2 pos, GridCell cell) {
        return isPointInCell(pos, cell.getCol(), cell.getRow());
    }

    public T getValueAtCell(GridCell cell) {
        return getValueAtCell(cell.getCol(), cell.getRow());
    }


    public GridCell getCellAt(int col, int row) {
        if ( col < 0 || col >= nCols) return null;
        if ( row < 0 || row >= nRows) return null;
        return graphCells[row][col];
    }

    public ValuePositionPair<T> getCellCentreAndValue(GridCell cell) {
        return getCellCentreAndValue(cell.getCol(), cell.getRow());
    }

    public boolean cellInCells(GridCell cell, ArrayList<GridCell> cells, boolean fastCompare) {
        if ( fastCompare )
            return cells.contains(cell);
        else
            for ( GridCell c : cells )
                if ( c.equals(cell) ) return true;

        return false;
    }

    public boolean cellInCells(GridCell cell, ArrayList<GridCell> cells) {
        return cellInCells(cell, cells, false);
    }

    public abstract float getMoveCost(int col, int row);

    public float getMoveCost(GridCell cell) {
        return getMoveCost(cell.getCol(), cell.getRow());
    }
}

package universe25.Worlds.GridLayers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import javafx.geometry.BoundingBox;
import universe25.Agents.ValuePositionPair;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jorl17 on 07/08/15.
 */
public class GridMapLayer<T> extends Actor {
    protected float gridWidth, gridHeight;
    protected float cellSize;
    protected int nRows, nCols;
    protected T[][] cells;
    protected T[][] nextCells;
    private Class<? extends T> cls;
    private final String name;
    private Color drawColor;
    private boolean drawLayer;

    //FIXME: The first of these could probably be made static. They both probably could
    private ShapeRenderer shapeRenderer;
    private boolean projectionMatrixSet;

    private Vector2[][] cellCentres;

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
        projectionMatrixSet = false;

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

        shapeRenderer = new ShapeRenderer();
        projectionMatrixSet = false;

        createCellArray();
    }

    private void createCellArray() {
        this.cells = createCells();
        this.nextCells = createCells();

        this.cellCentres = new Vector2[nRows][nCols];
        for (int i = 0; i < cellCentres.length; i++)
            for (int j = 0; j < cellCentres[0].length; j++)
                this.cellCentres[i][j] = new Vector2(j * cellSize + cellSize*0.5f, i * cellSize+ cellSize*0.5f);

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

    public T getValueAtCell(int col, int row,T[][] cells) {
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

    protected void drawCell(Batch batch, int col, int row) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(drawColor);
        shapeRenderer.rect(col*cellSize, row*cellSize, cellSize, cellSize);
        shapeRenderer.end();

    }

    // Always do batch.end() before!!
    public void drawCell(Batch batch, int col, int row, Color c) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(c);
        shapeRenderer.rect(col*cellSize, row*cellSize, cellSize, cellSize);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if ( drawLayer ) {
            batch.end();
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            if (!projectionMatrixSet) {
                shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
                projectionMatrixSet = true;
            }

            for (int i = 0; i < nRows; i++)
                for (int j = 0; j < nCols; j++)
                    drawCell(batch, j, i);

            Gdx.gl.glDisable(GL20.GL_BLEND);
            batch.begin();
        }
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

    public ArrayList<int[]> getCellsWithinTriangle(Vector2[] triangle) {
        int[] minMaxRowCol = new int[4];
        getMinMaxRowCols(triangle, minMaxRowCol);
        int minCol = minMaxRowCol[0];
        int maxCol = minMaxRowCol[1];
        int minRow = minMaxRowCol[2];
        int maxRow = minMaxRowCol[3];

        // Now search in those
        ArrayList<int[]> ret = new ArrayList<>();
        for (int i = minRow; i <= maxRow; i++)
            for (int j = minCol; j <= maxCol; j++)
                if (Intersector.isPointInTriangle(cellCentres[i][j], triangle[0], triangle[1], triangle[2]))
                    ret.add(new int[] { i, j});


        return ret;
    }

    public Vector2[][] getCellCentres() {
        return cellCentres;
    }

    public BoundingBox getCellBoundingBox(int col, int row) {
        Vector2 centre = cellCentres[row][col];
        float halfCellSize = cellSize/2;
        return new BoundingBox(centre.x-halfCellSize, centre.y-halfCellSize, centre.x+halfCellSize, centre.y+halfCellSize);
    }

    public Vector2 getCellCentre(int col, int row) {
        return cellCentres[row][col].cpy();
    }

    public ValuePositionPair<T> getCellCentreAndValue(int col, int row) {
        return new ValuePositionPair<T>(getValueAtCell(col, row), cellCentres[row][col].cpy());
    }

    public boolean isPointInCell(Vector2 pos, int col, int row) {
        Vector2 topLeft = new Vector2(col * cellSize, row * cellSize),
                topRight = new Vector2(col * cellSize, (row+1) * cellSize),
                bottomRight = new Vector2((col+1) * cellSize, (row+1) * cellSize),
                bottomLeft = new Vector2((col+1) * cellSize, row * cellSize);

        com.badlogic.gdx.utils.Array<Vector2> vector2s = new com.badlogic.gdx.utils.Array<>();
        vector2s.add(topLeft); vector2s.add(topRight); vector2s.add(bottomRight); vector2s.add(bottomLeft);
        return Intersector.isPointInPolygon(vector2s, pos);/*
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


    public int[] getCell(float x, float y) {
        return new int[] {(int) (y / cellSize), (int) (x / cellSize)};
    }
}

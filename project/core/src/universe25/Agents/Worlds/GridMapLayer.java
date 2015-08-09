package universe25.Agents.Worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import universe25.Agents.ValuePositionPair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jorl17 on 07/08/15.
 */
public class GridMapLayer<T> extends Actor {
    protected float gridWidth, gridHeight;
    protected float cellSize;
    protected int nRows, nCols;
    protected T[][] cells;
    private Class<? extends T> cls;
    private final String name;

    //FIXME: The first of these could probably be made static. They both probably could
    private ShapeRenderer shapeRenderer;
    private boolean projectionMatrixSet;

    private Vector2[][] cellCentres;

    public GridMapLayer(Class<? extends T> cls, float gridWidth, float gridHeight, float cellSize, String name) {
        this.cls = cls;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellSize = cellSize;
        nCols = Math.round(gridWidth / cellSize);
        nRows = Math.round(gridHeight / cellSize);
        this.name = name;

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
        this.cells = (T[][]) Array.newInstance(cls, nRows, nCols);
        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                try {
                    this.cells[i][j] = cls.newInstance();
                } catch (InstantiationException e) {
                    //e.printStackTrace();
                    //FIXME: So hacky...
                    this.cells[i][j] = (T) new Float(0);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

        this.cellCentres = new Vector2[nRows][nCols];
        for (int i = 0; i < cellCentres.length; i++)
            for (int j = 0; j < cellCentres[0].length; j++)
                this.cellCentres[i][j] = new Vector2(j * cellSize + cellSize*0.5f, i * cellSize+ cellSize*0.5f);

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

    public T getValueAtCell(int col, int row) {
        if ( col < 0 || col > nCols ) return null;
        if ( row < 0 || row > nRows ) return null;

        return cells[row][col];
    }

    public void setValueAt(float x, float y, T val) {
        if ( x > gridWidth || x < 0 ) return;
        if ( y > gridHeight|| y < 0 ) return;

        cells[(int) (y / cellSize)][(int) (x / cellSize)] = val;
    }

    public void setValueAtCell(int col, int row, T val) {
        cells[row][col] = val;
    }

    protected void drawCell(Batch batch, int col, int row) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.YELLOW);
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
        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        if(!projectionMatrixSet){
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            projectionMatrixSet = true;
        }

        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                drawCell(batch, j,i);

        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin();
    }

    protected ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public ArrayList<int[]> getCellsWithinTriangle(Vector2[] triangle) {

        // First get the "rectangular region" that contains this triangle
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
}

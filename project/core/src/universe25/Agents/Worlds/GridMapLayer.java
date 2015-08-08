package universe25.Agents.Worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.lang.reflect.Array;
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
                    this.cells[i][j] = (T) new Float(0);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
    }

    public String getName() {
        return name;
    }

    public T getValueAt(float x, float y) {
        if ( x > gridWidth || x < 0 ) return null;
        if ( y > gridHeight|| y < 0 ) return null;

        return cells[(int) (y / cellSize)][(int) (x / cellSize)];
    }

    public T getValueAtCell(int x, int y) {
        if ( x < 0 || x > nCols ) return null;
        if ( y < 0 || y > nRows ) return null;

        return cells[y][x];
    }

    public void setValueAt(float x, float y, T val) {
        if ( x > gridWidth || x < 0 ) return;
        if ( y > gridHeight|| y < 0 ) return;

        cells[(int) (y / cellSize)][(int) (x / cellSize)] = val;
    }

    public void setValueAtCell(int x, int y, T val) {
        cells[y][x] = val;
    }

    protected void drawCell(Batch batch, int col, int row) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(col*cellSize, row*cellSize, cellSize, cellSize);
        shapeRenderer.end();

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
}

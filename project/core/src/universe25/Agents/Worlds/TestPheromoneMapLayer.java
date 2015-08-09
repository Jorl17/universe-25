package universe25.Agents.Worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by jorl17 on 08/08/15.
 */
public class TestPheromoneMapLayer extends FloatLayer {

    public TestPheromoneMapLayer(float gridWidth, float gridHeight, float cellSize, String name, float pheromoneMax, Color c) {
        super(gridWidth, gridHeight, cellSize, name, pheromoneMax, c);
    }

    public TestPheromoneMapLayer(float cellSize, int nRows, int nCols, String name, float pheromoneMax, Color c) {
        super(cellSize, nRows, nCols, name, pheromoneMax, c);
    }

    public void clearPheromoneAt(float x, float y) {
        clearValueAt(x,y);
    }

    public void increasePheromoneAt(float x, float y, float amnt) {
        increaseValueAt(x,y,amnt);
    }

    public void decreasePheromoneAt(float x, float y, float amnt) {
        decreaseValueAt(x, y, amnt);
    }

    public void decreasePheromoneAtCell(int col, int row, float amnt) {
        decreaseValueAtCell(col,row,amnt);
    }

    public void evaporate(float rate) {
        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                decreasePheromoneAtCell(j, i, rate);
    }

    /*@Override
    protected void drawCell(Batch batch, int col, int row) {
        float valueAtCell = getValueAtCell(col, row);
        if ( valueAtCell > 0 ) {
            getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
            getShapeRenderer().setColor(drawColor);
            getShapeRenderer().rect(col * cellSize, row * cellSize, cellSize, cellSize);
            getShapeRenderer().end();
            Color cpy = drawColor.cpy();

            cpy.a = normalizeValue(valueAtCell);
            getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
            getShapeRenderer().setColor(cpy);
            getShapeRenderer().rect(col * cellSize, row * cellSize, cellSize, cellSize);
            getShapeRenderer().end();
        }
    }*/

    public float getMaxPheromone() { return getMaxValue(); }
}

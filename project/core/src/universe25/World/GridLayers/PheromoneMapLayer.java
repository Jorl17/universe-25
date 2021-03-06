package universe25.World.GridLayers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import universe25.Utils.RandomUtils;

/**
 * Created by jorl17 on 08/08/15.
 */
public class PheromoneMapLayer extends FloatLayer {

    public PheromoneMapLayer(float gridWidth, float gridHeight, float cellSize, String name, float pheromoneMax, Color c) {
        super(gridWidth, gridHeight, cellSize, name, pheromoneMax, c);
    }

    public PheromoneMapLayer(float cellSize, int nRows, int nCols, String name, float pheromoneMax, Color c) {
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

    public void increasePheromoneAtCell(int col, int row, float amnt) {
        increaseValueAtCell(col, row, amnt);
    }

    public void evaporate(float rate) {
        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                decreasePheromoneAtCell(j, i, rate);
    }

    public void spread(float rate, float spreadProbability) {
        Float[][] tmpCells = cloneCells();
        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                for (int k = i - 1; k <= i + 1; k++)
                    for (int l = j - 1; l <= j + 1; l++)
                        if (RandomUtils.coin(spreadProbability) )
                            if (k >= 0 && k < nRows && l >= 0 && l < nCols && !(k == i && l == j)) {
                                increasePheromoneAtCell(l, k, rate * getValueAtCell(j, i, tmpCells));
                                decreasePheromoneAtCell(j, i, .9f * rate * getValueAtCell(j, i, tmpCells));
                            }
    }

    /*
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

    public void setPheromoneAt(float x, float y, float val) {
        setValueAt(0,0,val);
    }
}

package universe25.Agents.Worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by jorl17 on 08/08/15.
 */
public class TestPheromoneMapLayer extends GridMapLayer<Float> {
    private float pheromoneMax;

    public TestPheromoneMapLayer(float gridWidth, float gridHeight, float cellSize, String name, float pheromoneMax) {
        super(Float.class, gridWidth, gridHeight, cellSize, name);
        this.pheromoneMax = pheromoneMax;
    }

    public TestPheromoneMapLayer(float cellSize, int nRows, int nCols, String name, float pheromoneMax) {
        super(Float.class, cellSize, nRows, nCols, name);
        this.pheromoneMax = pheromoneMax;
    }

    private float normalizePheromone(float val) {
        return val/pheromoneMax;
    }

    public void clearPheromoneAt(float x, float y) {
        setValueAt(x,y,0.0f);
    }

    public void increasePheromoneAt(float x, float y, float amnt) {
        float newVal = Float.min(getValueAt(x, y) + amnt, pheromoneMax);
        setValueAt(x,y,newVal);
    }

    public void decreasePheromoneAt(float x, float y, float amnt) {
        float newVal = Float.max(getValueAt(x,y) - amnt, 0);
        setValueAt(x,y,newVal);
    }


    @Override
    protected void drawCell(Batch batch, int col, int row) {
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
        getShapeRenderer().setColor(Color.YELLOW);
        getShapeRenderer().rect(col * cellSize, row * cellSize, cellSize, cellSize);
        getShapeRenderer().end();
        Color cpy = Color.YELLOW.cpy();

        cpy.a = normalizePheromone(getValueAtCell(col, row));
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        getShapeRenderer().setColor(cpy);
        getShapeRenderer().rect(col * cellSize, row * cellSize, cellSize, cellSize);
        getShapeRenderer().end();

    }
}

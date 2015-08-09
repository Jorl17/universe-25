package universe25.Agents.Worlds.GridLayers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by jorl17 on 08/08/15.
 */
public class FloatLayer extends GridMapLayer<Float> {
    private float floatMax;

    public FloatLayer(float gridWidth, float gridHeight, float cellSize, String name, float floatMax, Color drawColor) {
        super(Float.class, gridWidth, gridHeight, cellSize, name, drawColor, false);
        this.floatMax = floatMax;
    }

    public FloatLayer(float cellSize, int nRows, int nCols, String name, float floatMax, Color drawColor) {
        super(Float.class, cellSize, nRows, nCols, name, drawColor, false);
        this.floatMax = floatMax;
    }

    protected float normalizeValue(float val) {
        return val/ floatMax;
    }

    public void clearValueAt(float x, float y) {
        setValueAt(x,y,0.0f);
    }

    public void increaseValueAt(float x, float y, float amnt) {
        float newVal = Float.min(getValueAt(x, y) + amnt, floatMax);
        setValueAt(x,y,newVal);
    }

    public void decreaseValueAt(float x, float y, float amnt) {
        float newVal = Float.max(getValueAt(x,y) - amnt, 0);
        setValueAt(x,y,newVal);
    }

    public void decreaseValueAtCell(int col, int row, float amnt) {
        float newVal = Float.max(getValueAtCell(col,row) - amnt, 0);
        setValueAtCell(col,row,newVal);
    }

    public void increaseValueAtCell(int col, int row, float amnt) {
        float newVal = Float.min(getValueAtCell(col, row) + amnt, floatMax);
        setValueAtCell(col,row,newVal);
    }


    @Override
    protected void drawCell(Batch batch, int col, int row) {
        float valueAtCell = getValueAtCell(col, row);
        if ( valueAtCell > 0 ) {
            getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
            getShapeRenderer().setColor(getDrawColor());
            getShapeRenderer().rect(col * cellSize, row * cellSize, cellSize, cellSize);
            getShapeRenderer().end();
            Color cpy = getDrawColor().cpy();

            cpy.a = normalizeValue(valueAtCell);
            getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
            getShapeRenderer().setColor(cpy);
            getShapeRenderer().rect(col * cellSize, row * cellSize, cellSize, cellSize);
            getShapeRenderer().end();
        }
    }

    public float getMaxValue() {
        return floatMax;
    }
}


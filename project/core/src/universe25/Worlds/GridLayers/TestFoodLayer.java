package universe25.Worlds.GridLayers;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by jorl17 on 08/08/15.
 */
public class TestFoodLayer extends FloatLayer {
    public TestFoodLayer(float gridWidth, float gridHeight, float cellSize, String name, float foodMax) {
        super(gridWidth, gridHeight, cellSize, name, foodMax, Color.RED);
        setDrawLayer(true);
    }

    public TestFoodLayer(float cellSize, int nRows, int nCols, String name, float foodMax) {
        super(cellSize, nRows, nCols, name, foodMax, Color.RED);
    }

    public void putFoodAt(float x, float y, float foodValue) {
        setValueAt(x,y,foodValue);
    }

    public float getFoodAt(float x, float y, float foodValue) {
        return getValueAt(x,y);
    }
}

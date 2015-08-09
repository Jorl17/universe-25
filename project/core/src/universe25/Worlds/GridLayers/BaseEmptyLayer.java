package universe25.Worlds.GridLayers;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by jorl17 on 09/08/15.
 */
public class BaseEmptyLayer extends GridMapLayer<Integer> {
    public BaseEmptyLayer(float gridWidth, float gridHeight, float cellSize, String name) {
        super(Integer.class, gridWidth, gridHeight, cellSize, name, Color.GRAY, false);
    }

    public BaseEmptyLayer(float cellSize, int nRows, int nCols, String name) {
        super(Integer.class, cellSize, nRows, nCols, name);
    }
}

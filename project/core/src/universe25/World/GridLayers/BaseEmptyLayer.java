package universe25.World.GridLayers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

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

    @Override
    protected void drawCellBody(Batch batch, int col, int row) {
        getShapeRenderer().rect(col * cellSize, row * cellSize, cellSize, cellSize);
    }

    @Override
    public float getMoveCost(int col, int row) {
        return 1;
    }
}

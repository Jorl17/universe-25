package universe25.Agents.Worlds;

import java.lang.reflect.Array;

/**
 * Created by jorl17 on 07/08/15.
 */
public class GridMapLayer<T> {
    private float gridWidth, gridHeight;
    private float cellSize;
    private int nRows, nCols;
    private T[][] cells;
    private Class<? extends T> cls;
    private final String name;

    public GridMapLayer(Class<? extends T> cls, float gridWidth, float gridHeight, float cellSize, String name) {
        this.cls = cls;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellSize = cellSize;
        nCols = Math.round(gridWidth / cellSize);
        nRows = Math.round(gridHeight / cellSize);
        this.name = name;

        createCellArray();
    }

    public GridMapLayer(Class<? extends T> cls, float cellSize, int nRows, int nCols, String name) {
        this.cellSize = cellSize;
        this.nRows = nRows;
        this.nCols = nCols;
        this.gridWidth = this.nCols * cellSize;
        this.gridHeight = this.nRows * cellSize;
        this.name = name;

        createCellArray();
    }

    private void createCellArray() {
        this.cells = (T[][]) Array.newInstance(cls, 10, 20);
    }

    public String getName() {
        return name;
    }

    public T getValueAt(float x, float y) {
        if ( x > gridWidth || x < 0 ) return null;
        if ( y > gridHeight|| y < 0 ) return null;

        return cells[(int) (x / cellSize)][(int) (y / cellSize)];
    }
}

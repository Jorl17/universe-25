package universe25.World.GridLayers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import universe25.Food.Food;
import universe25.Food.FoodQuantityPair;
import universe25.GameLogic.Movement.Pathfinding.GridCell;

/**
 * Created by jorl17 on 14/08/15.
 */
public class FoodLayer extends GridMapLayer<FoodQuantityPair> {
    private float maxDensity;
    public FoodLayer(float gridWidth, float gridHeight, float cellSize, String name, Color drawColor, boolean drawLayer, float maxDensity) {
        super(FoodQuantityPair.class, gridWidth, gridHeight, cellSize, name, drawColor, drawLayer);
        this.maxDensity = maxDensity;
        assignCellsToFoodQuantityPairs();
        this.nextCells = this.cells;
    }

    public FoodLayer(float cellSize, int nRows, int nCols, String name, float maxDensity) {
        super(FoodQuantityPair.class, cellSize, nRows, nCols, name);
        this.maxDensity = maxDensity;
        assignCellsToFoodQuantityPairs();
    }

    private void assignCellsToFoodQuantityPairs() {
        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                getValueAtCell(j, i).setCell(getCellAt(j, i));
    }

    public Food getFoodSource(int col, int row) {
        return getValueAtCell(col, row).getSource();
    }

    public Food getFoodSource(GridCell cell) {
        return getValueAtCell(cell).getSource();
    }

    public boolean isFoodSource(int col, int row) {
        return getFoodSource(col, row) != null;
    }

    public boolean isFoodSource(GridCell cell) {
        return getFoodSource(cell) != null;
    }

    public void putFoodSource(int col, int row, Food source, float quantity) {
        FoodQuantityPair valueAtCell = getValueAtCell(col, row);
        valueAtCell.setSource(source);
        valueAtCell.setAmount(quantity);
        //System.out.println("Put " + source + " at " + "(" + row + ", " + col + ") -> ");
    }

    public void putFoodSource(GridCell cell, Food source, float quantity) {
        putFoodSource(cell.getCol(), cell.getRow(), source, quantity);
    }

    public float decreaseQuantityAtCell(int col, int row, float quantity) {
        FoodQuantityPair valueAtCell = getValueAtCell(col, row);
        assert ( valueAtCell.getSource() != null );

        if ( quantity < valueAtCell.getAmount() ) {
            valueAtCell.decrementAmount(quantity);
            return quantity;
        } else {
            valueAtCell.notifySourceFoodEnded();
            valueAtCell.setSource(null);
            float amount = valueAtCell.getAmount();
            valueAtCell.setAmount(0);
            return amount;
        }
    }

    public float decreaseQuantityAt(float x, float y, float quantity) {
        if ( x < 0 || x > gridWidth ) return 0;
        if ( y < 0 || y > gridWidth ) return 0;

        return decreaseQuantityAtCell((int) (x / cellSize), (int) (y / cellSize), quantity);
    }

    protected float normalizeValue(float val) {
        return val/ maxDensity;
    }

    @Override
    protected void drawCellBody(Batch batch, int col, int row) {
        //System.out.println(getValueAtCell(col, row));
        float valueAtCell = getValueAtCell(col, row).getAmount();
        if ( valueAtCell > 0 ) {
            Color cpy = getDrawColor().cpy();

            cpy.a = normalizeValue(valueAtCell);
            getShapeRenderer().setColor(cpy);
            getShapeRenderer().rect(col * cellSize, row * cellSize, cellSize, cellSize);
        }
    }

    @Override
    protected void drawCellGrid(Batch batch, int col, int row) {
        //Empty
    }

    @Override
    public float getMoveCost(int col, int row) {
        return 0;
    }

}

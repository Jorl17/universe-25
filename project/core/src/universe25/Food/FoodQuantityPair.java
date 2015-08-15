package universe25.Food;

import universe25.GameLogic.Movement.Pathfinding.GridCell;

/**
 * Created by jorl17 on 14/08/15.
 */
public class FoodQuantityPair {
    private GridCell cell;
    private Food source;
    private float amount;

    public FoodQuantityPair() {
    }

    public FoodQuantityPair(Food source, float amount, GridCell cell) {
        this.source = source;
        this.amount = amount;
        this.cell = cell;
    }

    public Food getSource() {
        return source;
    }

    public float getAmount() {
        return amount;
    }

    public void setSource(Food source) {
        this.source = source;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float decrementAmount(float amnt) {
        amount -= amnt;
        assert ( amount >= 0);
        return amount;
    }

    public void notifySourceFoodEnded() {
        source.onFoodEnded(cell);
    }

    public void setCell(GridCell cell) {
        this.cell = cell;
    }

    public GridCell getCell() {
        return cell;
    }

    public boolean hasFood() {
        if ( source == null ) assert (amount == 0); //FIXME: Remove in release
        return source != null;
    }

    @Override
    public String toString() {
        return "FoodQuantityPair{" +
                "cell=" + cell +
                ", source=" + source +
                ", amount=" + amount +
                '}';
    }
}

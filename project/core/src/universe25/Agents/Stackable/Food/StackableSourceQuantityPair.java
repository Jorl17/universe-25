package universe25.Agents.Stackable.Food;

import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.Agents.Stackable.Stackable;

/**
 * Created by jorl17 on 14/08/15.
 */
public class StackableSourceQuantityPair {
    private GridCell cell;
    private Stackable source;
    private float amount;

    public StackableSourceQuantityPair() {
    }

    public StackableSourceQuantityPair(Stackable source, float amount, GridCell cell) {
        this.amount = amount;
        this.cell = cell;
    }

    public Stackable getSource() {
        return source;
    }

    public float getAmount() {
        return amount;
    }

    public void setSource(Stackable source) {
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

    public void setCell(GridCell cell) {
        this.cell = cell;
    }

    public GridCell getCell() {
        return cell;
    }

    public boolean hasStackables() {
        if ( source == null ) assert (amount == 0); //FIXME: Remove in release
        return source != null;
    }

    public void notifyStackEnded() {
        //FIXME
        getSource().onStackEnded(getCell());
    }
}

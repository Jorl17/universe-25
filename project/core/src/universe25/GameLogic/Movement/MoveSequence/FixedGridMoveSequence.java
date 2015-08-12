package universe25.GameLogic.Movement.MoveSequence;

import universe25.Worlds.GridLayers.GridMapLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 11/08/15.
 */
public class FixedGridMoveSequence extends GridMoveSequence {
    private int maxSize;
    private boolean discardFromBeginning;

    public FixedGridMoveSequence(int maxSize, boolean discardFromBeginning) {
        super();
        this.maxSize = maxSize;
        this.discardFromBeginning = discardFromBeginning;
    }

    public FixedGridMoveSequence(int maxSize) {
        this(maxSize, true);
    }

    public FixedGridMoveSequence(GridMapLayer grid, int maxSize, boolean discardFromBeginning) {
        super(grid);
        this.maxSize = maxSize;
        this.discardFromBeginning = discardFromBeginning;
        forceSize();
    }

    public FixedGridMoveSequence(GridMapLayer grid, int maxSize) {
        this(grid, maxSize, true);
    }

    public FixedGridMoveSequence(GridMapLayer grid, ArrayList<int[]> cells, int maxSize, boolean discardFromBeginning) {
        super(grid, cells);
        this.maxSize = maxSize;
        this.discardFromBeginning = discardFromBeginning;
        forceSize();
    }

    public FixedGridMoveSequence(GridMapLayer grid, ArrayList<int[]> cells, int maxSize) {
        this(grid, cells, maxSize, true);
    }

    private void forceSize() {
        int diff = getMoves().size() - maxSize;
        if ( getMoves().size() > 0 ) {
            if (discardFromBeginning)
                while (diff-- > 0)
                    getMoves().remove(0);
            else
                while (diff-- > 0)
                    getMoves().remove(diff);
        }
    }

    @Override
    public void addMove(int[] cell) {
        if ( !discardFromBeginning && getMoves().size() == maxSize) return; //Already full
        super.addMove(cell);
        forceSize();
    }

    public int getMaxMoves() {
        return maxSize;
    }
}
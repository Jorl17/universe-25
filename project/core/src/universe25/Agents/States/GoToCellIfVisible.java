package universe25.Agents.States;

import universe25.Agents.Agent;
import universe25.Agents.ValuePositionPair;
import universe25.GameLogic.Movement.Pathfinding.GridCell;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Created by jorl17 on 19/08/15.
 */
public class GoToCellIfVisible<T extends Agent> extends GoToCells<T> {
    private ArrayList<ValuePositionPair<Float>> cellCentreAndValue;
    private Supplier<GridCell> gridCellSupplier;
    public GoToCellIfVisible(T agent, int priority, String name, Supplier<GridCell> gridCellSupplier) {
        super(agent, priority, name);
        this.gridCellSupplier = gridCellSupplier;
        this.cellCentreAndValue = new ArrayList<>();
    }

    public GoToCellIfVisible(T agent, int priority, String name, GridCell cell) {
        super(agent, priority, name);
        this.gridCellSupplier = () -> cell;
        this.cellCentreAndValue = new ArrayList<>();
    }

    @Override
    protected boolean areThereCellsToGoTo() {
        return agent.isVisible(gridCellSupplier.get());
    }

    @Override
    protected ArrayList<ValuePositionPair<Float>> getCenterOfCellsToGoTo() {
        GridCell cell = gridCellSupplier.get();
        this.cellCentreAndValue.clear();
        this.cellCentreAndValue.add(new ValuePositionPair<>(1.0f, cell.getCentre()));
        return cellCentreAndValue;
    }


}

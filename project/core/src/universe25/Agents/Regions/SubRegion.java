package universe25.Agents.Regions;

import universe25.GameLogic.Movement.Pathfinding.GridCell;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jorl17 on 17/08/15.
 */
public class SubRegion {
    private Region parentRegion;
    private ArrayList<GridCell> cells;
    private static Random random = new Random();

    public SubRegion(Region parentRegion) {
        this.parentRegion = parentRegion;
        this.cells = new ArrayList<>();
    }

    public boolean randomlyExpand() {
        ArrayList<GridCell> candidateNeighbours;
        if ( cells.isEmpty() ) {
            candidateNeighbours = parentRegion.getCells();
        } else {
            // Pick a random neighbour
            candidateNeighbours = new ArrayList<>();
            for ( GridCell cell : cells )
                for (int i : new int[]{-1, 1})
                    for (int j : new int[]{-1, 1}) {
                        GridCell candidate = parentRegion.getRegionsLayer().getCellAt(cell.getCol()+i, cell.getRow()+j);
                        if ( parentRegion.hasCell(candidate) && cells.indexOf(candidate) == -1 )
                            candidateNeighbours.add(candidate);
                    }
        }

        if ( candidateNeighbours.isEmpty() ) {
            System.out.println("Couldn't expand StackRegion in Hive!!!");
            return false;
        } else {
            cells.add(candidateNeighbours.get(random.nextInt(candidateNeighbours.size())));
            return true;
        }
    }

    public ArrayList<GridCell> getCells() {
        return cells;
    }
}

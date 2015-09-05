package universe25.Agents.Regions;

import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Species;
import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.Utils.RandomUtils;
import universe25.World.World;

/**
 * Created by jorl17 on 17/08/15.
 */
public class Hive<S extends Species> extends Region {
    private World world;
    private int startCol, startRow, width, height;
    private FoodStack foodStackRegion;

    public Hive(World world, S regionSpecies, int startCol, int startRow, int width, int height) {
        super(world.getRegionsLayer(), regionSpecies, world.getRegionsLayer().getGridCellsRectangle(startCol, startRow, width, height));
        this.world = world;
        this.startCol = startCol;
        this.startRow = startRow;
        this.width = width;
        this.height = height;
        this.foodStackRegion = new FoodStack(world, this, world.getStacksLayer());
        foodStackRegion.randomlyExpand();
    }

    public Hive(World world, S regionSpecies, int width, int height) {
        super(world.getRegionsLayer(), regionSpecies, null);
        this.world = world;
        int startCol, startRow;
        boolean obstacles;
        do {
            obstacles = false;
            startCol = RandomUtils.rand(width, world.getRegionsLayer().getNumCols() - width);
            startRow = RandomUtils.rand(height, world.getRegionsLayer().getNumRows() - height);
            for ( int i = startCol; i < startCol+width; i++)
                for ( int j = startRow; j < startRow+height; j++ ) {
                    GridCell cell = world.getAgentObjectsLayer().getCellAt(i, j);
                    if ( cell != null && world.getAgentObjectsLayer().hasObjects(cell) )
                        obstacles = true;
                }
        } while ( obstacles );
        addCells(world.getRegionsLayer().getGridCellsRectangle(startCol, startRow, width, height));
        this.startCol = startCol;
        this.startRow = startRow;
        this.width = width;
        this.height = height;
        this.foodStackRegion = new FoodStack(world, this, world.getStacksLayer());
        foodStackRegion.randomlyExpand();
    }

    public Vector2 getCenter() {
        int endCol = startCol + width;
        int endRow = startRow + height;
        return getRegionsLayer().getCellCentre(endCol, endRow).add(getRegionsLayer().getCellCentre(startCol, startRow)).scl(0.5f);
    }

    public FoodStack getFoodStackRegion() {
        return foodStackRegion;
    }
}

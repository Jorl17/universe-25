package universe25.Agents.Regions;

import universe25.Agents.SimplisticAnt.SimplisticAnt;
import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.World.GridLayers.RegionsLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 17/08/15.
 */
public class SimplisticAntHive extends Region {
    public SimplisticAntHive() {
    }

    public SimplisticAntHive(RegionsLayer regionsLayer, ArrayList<GridCell> cells) {
        super(regionsLayer, SimplisticAnt.getSpecies(), cells);
    }
}

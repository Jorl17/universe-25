package universe25.Agents.Regions;

import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.World.GridLayers.PheromoneMapLayer;
import universe25.World.GridLayers.RegionsLayer;
import universe25.World.World;

import java.util.ArrayList;

/**
 * Created by jorl17 on 17/08/15.
 */
public class Region {
    private RegionsLayer regionsLayer;
    private String regionSpecies;
    private ArrayList<GridCell> cells;

    public Region() {
        this.cells = new ArrayList<>();
    }

    public Region(RegionsLayer regionsLayer, String regionSpecies, ArrayList<GridCell> cells) {
        this.regionsLayer = regionsLayer;
        this.regionSpecies = regionSpecies;
        this.cells = new ArrayList<>();
        addCells(cells);
    }

    public void removeCell(GridCell cell) {
        //FIXME: prolly slow
        for ( GridCell c: this.cells )
            if ( c.equals(cell) ) {
                this.cells.remove(c);
            }

        assert ( false ); //SHOULD FIND IT!
    }

    public void removeCells(ArrayList<GridCell> cells) {
        for ( GridCell cell : cells )
            removeCell(cell);
    }

    public void addCell(GridCell cell) {
        this.cells.add(cell);
        if ( regionsLayer != null && regionsLayer.getValueAtCell(cell) != this )
            regionsLayer.setValueAtCell(cell, this);
    }

    public void addCells(ArrayList<GridCell> cell) {
        this.cells.addAll(cells);
    }


    public boolean isEmptyRegion() {
        return regionSpecies == null;
    }

    public void setRegionsLayer(RegionsLayer regionsLayer) {
        this.regionsLayer = regionsLayer;
    }

    public RegionsLayer getRegionsLayer() {
        return regionsLayer;
    }

    public String getRegionSpecies() {
        return regionSpecies;
    }

    public ArrayList<GridCell> getCells() {
        return cells;
    }

    public void setRegionSpecies(String regionSpecies) {
        this.regionSpecies = regionSpecies;
    }

    public void markAsFreeRegion() {
        // Note that we intend to have ONE single static region that has all free cells...so this is redundant I guess
        setRegionSpecies("FreeRegion");
    }
}

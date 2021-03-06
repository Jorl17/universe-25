package universe25.Agents.SimplisticAnt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import universe25.Agents.Pheromones.*;
import universe25.Agents.Regions.Hive;
import universe25.Agents.Regions.Region;
import universe25.Agents.SpeciesAgent;
import universe25.Agents.Stackable.Food.StackableSourceQuantityPair;
import universe25.Agents.Stackable.StackableUtils;
import universe25.Agents.ValuePositionPair;
import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.World.GridLayers.RegionsLayer;
import universe25.World.GridLayers.StackablesLayer;

import java.util.ArrayList;

/**
 * Created by jorl17 on 06/08/15.
 */
public abstract class SimplisticAnt extends SpeciesAgent {
    private static final Texture texture = new Texture("ant.png");

    protected final Pheromone foodPheromone;
    protected final Pheromone pathPheronome;
    protected final Pheromone foodImmediancyPheromone;
    protected final Pheromone hivePheromone;

    protected PheromoneController
            foodPheromoneController,
            pathPheronomeController,
            foodImmediancyPheromoneController,
            hivePheromoneController;

    protected SimplisticAnt(SimplisticAntSpecies species, SimplisticAntSpeciesParameters parameters) {
        super(texture, false, species, parameters);
        setSize(8,8);
        setOriginX(4);
        setOriginY(4);
        foodPheromone = species.getFoodPheromone();
        pathPheronome = species.getPathPheromone();
        foodImmediancyPheromone = species.getFoodImmediancyPheromone();
        hivePheromone = species.getHivePheromone();
        //setBoundingBoxThreshold(1.0f);
        foodPheromoneController = new AlternativeOrderedPheromoneController(
                                    new ConditionalIncreasePheromoneController<>(this, foodPheromone, (float)getParameter("foodPheromoneIncreaseWhenSeeingFood").get(),
                                            SimplisticAnt::areThereCellsWithFood),
                                   /* new ConditionalIncreasePheromoneController(this, foodPheromone, foodPheromoneIncreaseWhenSeeingFoodPheromone,
                                    (agent) -> ((SimplisticAnt)agent).areThereCellsWithPheromone(foodPheromone)*/
                                    new ProportionalPheromoneController(this, foodPheromone, 0.20f, 1, -1, true, 100)
                                 );

        pathPheronomeController = new ConditionalPheromoneController<>(this,new AlternativeOrderedPheromoneController( new ConditionalIncreasePheromoneController<>(this, pathPheronome, (float)getParameter("pathPheromoneIncrease").get(),
                (agent) -> !agent.areThereCellsWithPheromone(pathPheronome)),
                new ProportionalPheromoneController(this, pathPheronome, (float)getParameter("pathPheromoneIncrease").get(), 10.0f, 50, true, 100)),
                        (agent) -> isOutsideHive());
                /*new ConditionalIncreasePheromoneController<>(this, pathPheronome,
                        (float)getParameter("pathPheromoneIncrease").get(),
                        (agent) -> isOutsideHive());*/

        foodImmediancyPheromoneController = new ConditionalIncreasePheromoneController<>(this, foodImmediancyPheromone, 1,
                SimplisticAnt::areThereCellsWithFood);

        hivePheromoneController = new AlternativeOrderedPheromoneController(
                new ConditionalIncreasePheromoneController<>(this, hivePheromone,
                        (float)getParameter("hivePheromoneIncreaseWhenSeeingHive").get(),
                        (agent) -> isOutsideHive() && areThereHiveCells()),
                new ConditionalPheromoneController<>(this,
                        new ProportionalPheromoneController(this, hivePheromone, 0.3f, 2, 65, true, 100),
                        (agent) -> isOutsideHive() )

        );

        prepareStates();
    }

    protected abstract void prepareStates();

    @Override
    public void update() {
        if ( foodPheromoneController != null)
            foodPheromoneController.update();
        if ( pathPheronomeController != null)
            pathPheronomeController.update();
        if ( foodImmediancyPheromoneController != null)
            foodImmediancyPheromoneController.update();

        if ( hivePheromoneController != null)
            hivePheromoneController.update();
    }

    public boolean areThereCellsWithPheromone(Pheromone pheromoneType) {
        return areThereCellsWithValueAtFloatLayer(pheromoneType.getWorldLayer());
    }

    public boolean areThereCellsWithFood() {
        StackablesLayer layer = getWorld().getStacksLayer();
        if ( getCellsInFov() != null ) {
            for ( GridCell cell : getCellsInFov()) {
                StackableSourceQuantityPair quantityPair = layer.getValueAtCell(cell);
                if ( StackableUtils.hasFoodAndNotAntPoison(quantityPair)) return true;
            }
        }

        return false;
    }

    public boolean areThereCellsWithAntPoison() {
        StackablesLayer layer = getWorld().getStacksLayer();
        if ( getCellsInFov() != null ) {
            for ( GridCell cell : getCellsInFov()) {
                StackableSourceQuantityPair quantityPair = layer.getValueAtCell(cell);
                if (StackableUtils.hasAntPoison(quantityPair)) return true;
            }
        }

        return false;
    }

    public boolean areThereHiveCells() {
        RegionsLayer layer = getWorld().getRegionsLayer();

        if ( getCellsInFov() != null ) {
            for ( GridCell cell : getCellsInFov()) {
                Region r = layer.getValueAtCell(cell);
                if ( r instanceof Hive ) {
                    if ( r.ownedBy(this) )
                        return true;
                }
            }
        }

        return false;
    }

    public boolean isOutsideHive() {
        RegionsLayer layer = getWorld().getRegionsLayer();
        Vector2 position = getPosition();
        Region ourCell = layer.getValueAt(position.x, position.y);
        return !(ourCell instanceof Hive && ourCell.ownedBy(this));
    }

    public ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithPheromone(Pheromone pheromoneType) {
        return getCenterOfCellsInFieldOfViewWithValueForSomeFloatLayer(pheromoneType.getWorldLayer());
    }

    public ArrayList<ValuePositionPair<Region>> getHiveCellsInFieldOfView() {
        RegionsLayer layer = getWorld().getRegionsLayer();
        ArrayList<ValuePositionPair<Region>> ret = new ArrayList<>();

        if (getCellsInFov() != null ) {
            for ( GridCell cell : getCellsInFov()) {
                ValuePositionPair<Region> cellCentreAndValue = layer.getCellCentreAndValue(cell);
                if ( cellCentreAndValue.getValue() instanceof Hive &&  cellCentreAndValue.getValue().ownedBy(this) )
                    ret.add(cellCentreAndValue);
            }
        }

        return ret;
    }
}

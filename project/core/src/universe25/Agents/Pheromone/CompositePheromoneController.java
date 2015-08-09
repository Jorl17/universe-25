package universe25.Agents.Pheromone;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 09/08/15.
 */
public abstract class CompositePheromoneController implements PheromoneController {
    private ArrayList<PheromoneController> controllers;

    public CompositePheromoneController() {
        this.controllers = new ArrayList<>();
    }

    public CompositePheromoneController(PheromoneController... controllers) {
        this.controllers = new ArrayList<>();
        addControllers(controllers);
    }
    public void addController(PheromoneController controller) {
        this.controllers.add(controller);
    }

    public void addControllers(PheromoneController... controllers) {
        Collections.addAll(this.controllers, controllers);
    }

    public void addControllerAtIndex(int index, PheromoneController controller) {
        this.controllers.add(index, controller);
    }

    protected ArrayList<PheromoneController> getControllers() {
        return controllers;
    }
}

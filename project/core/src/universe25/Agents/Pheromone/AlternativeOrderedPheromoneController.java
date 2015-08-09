package universe25.Agents.Pheromone;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 09/08/15.
 */
public class AlternativeOrderedPheromoneController implements PheromoneController {
    ArrayList<PheromoneController> controllers;
    public AlternativeOrderedPheromoneController(PheromoneController... controllers) {
        this.controllers = new ArrayList<>();
        Collections.addAll(this.controllers, controllers);
    }

    @Override
    public boolean update() {
        for ( PheromoneController controller: this.controllers )
            if ( controller.update() )
                return true;

        return false;
    }
}

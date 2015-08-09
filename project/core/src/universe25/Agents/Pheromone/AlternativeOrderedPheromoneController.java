package universe25.Agents.Pheromone;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 09/08/15.
 */
public class AlternativeOrderedPheromoneController extends CompositePheromoneController {
    public AlternativeOrderedPheromoneController() {
        super();
    }
    public AlternativeOrderedPheromoneController(PheromoneController... controllers) {
        super(controllers);
    }

    @Override
    public boolean update() {
        for ( PheromoneController controller: getControllers() )
            if ( controller.update() )
                return true;

        return false;
    }
}

package universe25.Agents;

import com.badlogic.gdx.graphics.Texture;
import universe25.Agents.States.Wander;

/**
 * Created by jorl17 on 06/08/15.
 */
public class SimplisticAnt extends Agent {
    public SimplisticAnt() {
        super(new Texture("ant.png"));
        states.addState(new Wander(this, 100, 80));
    }


    @Override
    public void agentUpdate() {

    }

}

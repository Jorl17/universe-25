package universe25.GameLogic;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by jorl17 on 06/08/15.
 */
public class SimplisticAnt extends Agent {
    public SimplisticAnt() {
        super(new Texture("ant.png"));
    }

    @Override
    public void update() {
        wander();
    }

    private void wander() {
        //FIXME
    }
}

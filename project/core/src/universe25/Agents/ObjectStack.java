package universe25.Agents;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import universe25.World.World;

/**
 * Created by jorl17 on 14/08/15.
 */
public class ObjectStack extends Group {
    public ObjectStack() {
    }

    public void add(Actor a) {
        super.addActor(a);
    }

    public void onAddedToWorld(World w) {
        w.addActor(this);
    }

    public boolean isEmpty() {
        return getChildren().size == 0;
    }
}

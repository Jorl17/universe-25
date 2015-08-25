package universe25.Objects;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by jorl17 on 25/08/15.
 */
public class Dirt extends WorldObject {
    private float amount;
    public Dirt(float amount) {
        super(new Texture("dirt.png"), false);
        this.amount = amount;
    }
}

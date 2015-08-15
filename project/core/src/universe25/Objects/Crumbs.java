package universe25.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;
import universe25.Food.Food;

/**
 * Created by jorl17 on 14/08/15.
 */
public class Crumbs extends  WorldObject {
    private Food source;
    private float foodAmount;
    public Crumbs(Food source, float foodAmount) {
        super(new Texture("crumbs.gif"));
        setSize(4,4);
        setOrigin(Align.center);
        this.source = source;
        this.foodAmount = foodAmount;
    }
}

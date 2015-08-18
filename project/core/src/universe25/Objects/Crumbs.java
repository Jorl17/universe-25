package universe25.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;
import universe25.Agents.Stackable.Food.Food;
import universe25.Utils.RandomUtils;

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
        setRotation(RandomUtils.rand(0, 360.0f));
        this.source = source;
        this.foodAmount = foodAmount;
    }
}

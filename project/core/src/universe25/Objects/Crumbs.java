package universe25.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;
import universe25.Agents.Stackable.Food.Food;
import universe25.Utils.RandomUtils;

/**
 * Created by jorl17 on 14/08/15.
 */
public class Crumbs extends  FoodBits {
    public Crumbs(Food source, float foodAmount) {
        super(new Texture("crumbs.gif"), source, foodAmount);
    }

    @Override
    public FoodBits cpy() {
        Crumbs crumbs = new Crumbs(this.getSource(), this.getFoodAmount());
        crumbs.setPosition(getParent().getX(), getParent().getY()); //FIXME: This is so ridiculously hacked
        return crumbs;
    }
}

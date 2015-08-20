package universe25.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import universe25.Agents.Stackable.Food.Food;
import universe25.Utils.RandomUtils;

/**
 * Created by jorl17 on 14/08/15.
 */
public abstract class FoodBits extends  WorldObject {
    private Food source;
    private float foodAmount;
    public FoodBits(Texture texture, Food source, float foodAmount) {
        super(texture, false);
        setSize(4,4);
        setOrigin(Align.center);
        setRotation(RandomUtils.rand(0, 360.0f));
        this.source = source;
        this.foodAmount = foodAmount;
    }

    public Food getSource() {
        return source;
    }

    public float getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(float foodAmount) {
        this.foodAmount = foodAmount;
    }

    public abstract FoodBits cpy();
}

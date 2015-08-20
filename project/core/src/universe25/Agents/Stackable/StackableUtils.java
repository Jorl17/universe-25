package universe25.Agents.Stackable;

import universe25.Agents.Stackable.Food.AntPoison;
import universe25.Agents.Stackable.Food.Food;
import universe25.Agents.Stackable.Food.FoodDeposit;
import universe25.Agents.Stackable.Food.StackableSourceQuantityPair;

/**
 * Created by jorl17 on 17/08/15.
 */
public class StackableUtils {

    private static boolean isFoodDeposit(Stackable s) {
        return s instanceof FoodDeposit;
    }

    public static boolean isFood(Stackable s) {
        return s instanceof Food && !isFoodDeposit(s);
    }

    public static boolean isAntPoison(Stackable s) {
        return s instanceof AntPoison;
    }

    public static boolean isFoodAndNotAntPoison(Stackable s) {
        return isFood(s) && ! (s instanceof AntPoison);
    }

    public static boolean hasFood(StackableSourceQuantityPair s) {
        return s.hasStackables() && isFood(s.getSource());
    }


    public static boolean hasFoodAndNotAntPoison(StackableSourceQuantityPair s) {
        return s.hasStackables() && isFoodAndNotAntPoison(s.getSource()) ;
    }

    public static boolean hasAntPoison(StackableSourceQuantityPair s) {
        return s.hasStackables() && isAntPoison(s.getSource()) ;
    }

    public static boolean isFoodAndNotAntPoison(StackableSourceQuantityPair s) {
        return s.hasStackables() && isFoodAndNotAntPoison(s.getSource()) ;
    }
}

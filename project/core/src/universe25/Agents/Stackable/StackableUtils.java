package universe25.Agents.Stackable;

import universe25.Agents.Stackable.Food.AntPoison;
import universe25.Agents.Stackable.Food.Food;
import universe25.Agents.Stackable.Food.StackableSourceQuantityPair;

/**
 * Created by jorl17 on 17/08/15.
 */
public class StackableUtils {
    public static boolean isFood(Stackable s) {
        return s instanceof Food;
    }

    public static boolean isAntPoison(Stackable s) {
        return s instanceof AntPoison;
    }

    public static boolean isFoodAndNotAntPoison(Stackable s) {
        return s instanceof Food && ! (s instanceof AntPoison);
    }

    public static boolean hasFood(StackableSourceQuantityPair s) {
        return s.hasStackables() && isFood(s.getSource()) ;
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

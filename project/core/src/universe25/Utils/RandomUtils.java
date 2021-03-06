package universe25.Utils;

import java.util.Random;

/**
 * Created by jorl17 on 18/08/15.
 */
public class RandomUtils {
    private static Random random = new Random();
    public static int rand(int minInc, int maxExcl) {
        return random.nextInt((maxExcl - minInc)) + minInc;
    }

    public static boolean coin(float prob) {
        return Math.random() <= prob;
    }

    public static boolean coin() {
        return coin(0.5f);
    }

    public static float rand(float minInc, float maxExcl) {
        return random.nextFloat() * (maxExcl - minInc) + minInc;
    }
}

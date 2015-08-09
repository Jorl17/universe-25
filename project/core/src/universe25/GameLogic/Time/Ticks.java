package universe25.GameLogic.Time;

/**
 * Created by jorl17 on 09/08/15.
 */
public class Ticks {
    private static long ticks = 0;

    public static long increaseTicks(int amount) {
        return (ticks += amount);
    }

    public static long increaseTicks() {
        return increaseTicks(1);
    }

    public static long getTicks() {
        return ticks;
    }

    public static long ticksSince(long ticks) {
        return Ticks.ticks - ticks;
    }
}

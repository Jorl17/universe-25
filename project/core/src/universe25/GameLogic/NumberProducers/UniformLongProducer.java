package universe25.GameLogic.NumberProducers;

import java.util.Random;

/**
 * Created by jorl17 on 09/08/15.
 */
public class UniformLongProducer implements NumberProducer<Long> {
    private static Random random = new Random();
    private int intervalLinclusive, intervalHexclusive;
    @Override
    public Long produce() {
        return ((long)(random.nextDouble()*(intervalHexclusive-intervalLinclusive)));
    }
}

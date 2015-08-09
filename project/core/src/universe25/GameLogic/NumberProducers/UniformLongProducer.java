package universe25.GameLogic.NumberProducers;

import java.util.Random;

/**
 * Created by jorl17 on 09/08/15.
 */
public class UniformLongProducer implements NumberProducer<Long> {
    private static Random random = new Random();
    private long intervalLinclusive, intervalHexclusive;

    public UniformLongProducer(long intervalLinclusive, long intervalHexclusive) {
        this.intervalLinclusive = intervalLinclusive;
        this.intervalHexclusive = intervalHexclusive;
    }

    @Override
    public Long produce() {
        //FIXME This sounds so ugly
        return ((long)(random.nextDouble()*(intervalHexclusive-intervalLinclusive)));
    }
}

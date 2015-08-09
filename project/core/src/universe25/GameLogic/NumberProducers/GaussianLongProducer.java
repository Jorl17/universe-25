package universe25.GameLogic.NumberProducers;

import java.util.Random;

/**
 * Created by jorl17 on 09/08/15.
 */
public class GaussianLongProducer implements NumberProducer<Long> {
    private static Random random = new Random();
    private long mean, std;
    private boolean clampToZero;

    public GaussianLongProducer(long mean, long std, boolean clampToZero) {
        this.mean = mean;
        this.std = std;
        this.clampToZero = clampToZero;
    }

    public GaussianLongProducer(long mean, long std) {
        this(mean,std,true);
    }

    @Override
    public Long produce() {
        //FIXME This sounds so ugly
        long l = (long) (random.nextGaussian() * std + mean);
        return clampToZero ? Long.max(l, 0) : l;
    }
}

package universe25.GameLogic.NumberProducers;

import java.util.Random;

/**
 * Created by jorl17 on 09/08/15.
 */
public class GaussianIntegerProducer implements NumberProducer<Integer> {
    private static Random random = new Random();
    private int mean, std;
    private boolean clampToZero;

    public GaussianIntegerProducer(int mean, int std, boolean clampToZero) {
        this.mean = mean;
        this.std = std;
        this.clampToZero = clampToZero;
    }

    public GaussianIntegerProducer(int mean, int std) {
        this(mean,std,true);
    }

    @Override
    public Integer produce() {
        //FIXME This sounds so ugly
        int l = (int) (random.nextGaussian() * std + mean);
        return clampToZero ? Integer.max(l, 0) : l;
    }
}

package universe25.GameLogic.NumberProducers;

import java.util.Random;

/**
 * Created by jorl17 on 09/08/15.
 */
public class GaussianFloatProducer implements NumberProducer<Float> {
    private static Random random = new Random();
    private float mean, std;
    private boolean clampToZero;

    public GaussianFloatProducer(float mean, float std, boolean clampToZero) {
        this.mean = mean;
        this.std = std;
        this.clampToZero = clampToZero;
    }

    public GaussianFloatProducer(float mean, float std) {
        this(mean,std,true);
    }

    @Override
    public Float produce() {
        //FIXME This sounds so ugly
        float l = (float) (random.nextGaussian() * std + mean);
        return clampToZero ? Float.max(l, 0) : l;
    }
}

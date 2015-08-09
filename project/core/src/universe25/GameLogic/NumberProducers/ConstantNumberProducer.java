package universe25.GameLogic.NumberProducers;

/**
 * Created by jorl17 on 09/08/15.
 */
public class ConstantNumberProducer<T> implements NumberProducer<T> {
    T val;
    public ConstantNumberProducer(T val) {
        this.val = val;
    }

    @Override
    public T produce() {
        return val;
    }
}

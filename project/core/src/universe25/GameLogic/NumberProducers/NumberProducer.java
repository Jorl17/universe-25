package universe25.GameLogic.NumberProducers;

/**
 * Created by jorl17 on 09/08/15.
 */
@FunctionalInterface
public interface NumberProducer<T> {
    T produce();
}

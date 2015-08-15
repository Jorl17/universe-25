package universe25.Agents;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by jorl17 on 08/08/15.
 */
public class ValuePositionPair<T> {
    private T value;
    private Vector2 position;

    public ValuePositionPair(T value, Vector2 position) {
        this.value = value;
        this.position = position;
    }

    public T getValue() {
        return value;
    }

    public Vector2 getPosition() {
        return position.cpy();
    }
}

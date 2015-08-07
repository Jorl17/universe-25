package universe25.GameLogic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import javafx.geometry.BoundingBox;
import universe25.GameLogic.Movement.MovableImage;

import java.util.ArrayList;

/**
 * Created by jorl17 on 06/08/15.
 */
public abstract class Agent extends MovableImage implements Disposable {
    private final Texture texture;
    private BoundingBox boundingBox;
    private boolean collidedWithWorld;
    private ArrayList<Agent> collidedAgents;

    protected Agent(Texture texture) {
        super(texture);
        this.texture = texture;
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
        collidedAgents = new ArrayList<Agent>();
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        boundingBox = new BoundingBox(x, y, width, height);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        boundingBox = new BoundingBox(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        boundingBox = new BoundingBox(getX(), getY(), getWidth(), getHeight());
    }

    private void cleanupCollisions() {
        this.collidedWithWorld = false;
        clearCollisionsWithAgents();
    }

    public abstract void update();

    @Override
    public void act(float delta) {
        super.act(delta);
        update();
        cleanupCollisions();
    }

    public boolean interesects(Agent o) {
        return boundingBox.intersects(o.boundingBox);
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public void setCollidedWithWorld(boolean collidedWithWorld) {
        this.collidedWithWorld = collidedWithWorld;
    }

    public void addCollisionWithAgent(Agent o) {
        collidedAgents.add(o);
    }

    public void clearCollisionsWithAgents() {
        collidedAgents.clear();
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }
}

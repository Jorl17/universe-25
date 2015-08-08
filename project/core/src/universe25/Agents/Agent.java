package universe25.Agents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import javafx.geometry.BoundingBox;
import universe25.Agents.States.StateManager;
import universe25.Agents.Worlds.World;
import universe25.GameLogic.Movement.MovableImage;

import java.util.ArrayList;

/**
 * Created by jorl17 on 06/08/15.
 */
public abstract class Agent extends MovableImage implements Disposable {
    private final Texture texture;
    protected BoundingBox boundingBox;
    protected boolean collidedWithWorld;
    protected ArrayList<Agent> collidedAgents;
    protected StateManager states;

    //FIXME: Temporary
    private ShapeRenderer shapeRenderer;

    protected Agent(Texture texture) {
        super(texture);
        this.texture = texture;
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
        collidedAgents = new ArrayList<Agent>();
        states = new StateManager(this);
        shapeRenderer = new ShapeRenderer();
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

    public void update() {
        states.update();
        agentUpdate();
    }

    public abstract void agentUpdate();

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
        return new Vector2(getX(Align.center), getY(Align.center));
    }

    public World getWorld() {
        return (World)getStage();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();
        Vector2 facing = getFacingDirection().scl(50);
        Vector2 pos = getPosition();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.line(pos.x, pos.y, pos.x + facing.x, pos.y + facing.y );
        shapeRenderer.end();
        batch.begin();
    }
}

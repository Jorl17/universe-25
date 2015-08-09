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
import universe25.Agents.Worlds.FloatLayer;
import universe25.Agents.Worlds.World;
import universe25.GameLogic.Movement.MovableImage;
import universe25.GameLogic.Movement.WeightedGoal;

import java.util.ArrayList;

/**
 * Created by jorl17 on 06/08/15.
 */
public abstract class Agent extends MovableImage implements Disposable {
    private final Texture texture;
    protected BoundingBox boundingBox;
    public static final int COLLIDED_TOP = 1, COLLIDED_LEFT = 2, COLLIDED_RIGHT = 4, COLLIDED_BOTTOM = 8;
    protected int collisionsWithWorld;
    protected ArrayList<Agent> collidedAgents;
    protected StateManager states;

    //FIXME: Temporary
    private ShapeRenderer shapeRenderer;

    private FieldOfView fieldOfView;
    private ArrayList<int[]> tmpCellsInFov;
    final boolean debugDrawFov, debugDrawCellsUnderFov, debugDrawGoals, debugDrawfacing;
    private boolean shouldDisposeTexture;

    protected Agent(Texture texture, boolean shouldDisposeTexture, float fov, float seeDistance, float speed) {
        this(texture, shouldDisposeTexture, fov, seeDistance, speed, false, false, false, false);
    }

    protected Agent(Texture texture, boolean shouldDisposeTexture, float fov, float seeDistance, float speed, boolean debugDrawFov, boolean debugDrawCellsUnderFov, boolean debugDrawGoals, boolean debugDrawfacing) {
        super(texture, speed);
        this.texture = texture;
        this.shouldDisposeTexture = shouldDisposeTexture;
        this.debugDrawFov = debugDrawFov;
        this.debugDrawCellsUnderFov = debugDrawCellsUnderFov;
        this.debugDrawGoals = debugDrawGoals;
        this.debugDrawfacing = debugDrawfacing;
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
        collidedAgents = new ArrayList<Agent>();
        states = new StateManager(this);
        shapeRenderer = new ShapeRenderer();
        fieldOfView = new FieldOfView(this, fov, seeDistance);
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
        this.collisionsWithWorld = 0;
        clearCollisionsWithAgents();
    }

    public abstract void update();

    private void updateCellsInFov() {
        FloatLayer firstFloatLayer = (FloatLayer)getWorld().getGridLayers().get("TestPheromoneLayer");
        tmpCellsInFov = firstFloatLayer.getCellsWithinTriangle(fieldOfView.getFovTriangle());
        Vector2 pos = getPosition();
        for (int i = 0; i < tmpCellsInFov.size(); i++) {
            int[] cell = tmpCellsInFov.get(i);
            if ( firstFloatLayer.isPointInCell(pos, cell[1], cell[0])) {
                tmpCellsInFov.remove(i);
                break;
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        fieldOfView.update();
        updateCellsInFov();
        states.update();

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
        if (shouldDisposeTexture)
            texture.dispose();
    }

    public void addCollisionWithWorld(int collision) {
        this.collisionsWithWorld |= collision;
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


    protected boolean areThereCellsWithValueAtFloatLayer(String layerName) {
        FloatLayer testLayer = (FloatLayer) getWorld().getGridLayers().get(layerName);
        if ( tmpCellsInFov != null ) {
            for ( int[] cell : tmpCellsInFov) {
                int row = cell[0], col = cell[1];
                ValuePositionPair<Float> cellCentreAndValue = testLayer.getCellCentreAndValue(col, row);
                if ( cellCentreAndValue.getValue() > 0 ) return true;
            }
        }

        return false;
    }

    protected ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithValueForSomeFloatLayer(String layername) {
        ArrayList<ValuePositionPair<Float>> ret = new ArrayList<>();
        FloatLayer testLayer = (FloatLayer) getWorld().getGridLayers().get(layername);
        if (tmpCellsInFov != null ) {
            for ( int[] cell : tmpCellsInFov) {
                int row = cell[0], col = cell[1];
                ValuePositionPair<Float> cellCentreAndValue = testLayer.getCellCentreAndValue(col, row);
                if ( cellCentreAndValue.getValue() > 0 ) ret.add(cellCentreAndValue);
            }
        }

        return ret;
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);


        batch.end();
        Vector2 facing = getFacingDirection().scl(50);
        Vector2 pos = getPosition();

        if ( debugDrawfacing ) {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.line(pos.x, pos.y, pos.x + facing.x, pos.y + facing.y);
            shapeRenderer.end();
        }

        if ( debugDrawGoals ) {
            ArrayList<WeightedGoal> goals = getGoalMovement().getGoals();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (WeightedGoal g : goals) {
                shapeRenderer.setColor(Color.NAVY);
                shapeRenderer.line(pos.x, pos.y, g.getGoal().x, g.getGoal().y);
            }
            shapeRenderer.end();
        }


        if ( debugDrawCellsUnderFov && tmpCellsInFov != null ) {
            Color c = new Color(0.3f,0.3f,0.3f,0.5f);
            for (int[] cell : tmpCellsInFov)
                // cell[1] has col, cell[0] has row
                getWorld().getGridLayers().get("TestPheromoneLayer").drawCell(batch, cell[1], cell[0], c);
        }

        batch.begin();

        if ( debugDrawFov )
            fieldOfView.draw(batch);


    }

    public void setPosition(Vector2 pos) {
        setPosition(pos.x, pos.y);
    }

    public int getCollisionsWithWorld() {
        return collisionsWithWorld;
    }
}

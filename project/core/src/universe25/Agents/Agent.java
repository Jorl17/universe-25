package universe25.Agents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import universe25.Agents.States.DoMoveSequence;
import universe25.Agents.States.StateManager;
import universe25.GameLogic.Movement.GoalMovement;
import universe25.GameLogic.Movement.MoveSequence.FixedGridMoveSequence;
import universe25.GameLogic.Movement.MoveSequence.GridMoveSequence;
import universe25.Objects.WorldObject;
import universe25.Worlds.GridLayers.BaseEmptyLayer;
import universe25.Worlds.GridLayers.FloatLayer;
import universe25.Worlds.World;
import universe25.GameLogic.Movement.MovableImage;
import universe25.GameLogic.Movement.WeightedGoal;

import java.util.ArrayList;

/**
 * Created by jorl17 on 06/08/15.
 */
public abstract class Agent extends MovableImage implements Disposable {
    private final Texture texture;
    public static final int COLLIDED_TOP = 1, COLLIDED_LEFT = 2, COLLIDED_RIGHT = 4, COLLIDED_BOTTOM = 8;
    protected int collisionsWithWorld;
    protected ArrayList<Agent> collidedAgents;
    protected ArrayList<WorldObject> collidedObjects;
    protected StateManager states;

    //FIXME: Temporary
    private ShapeRenderer shapeRenderer;

    private FieldOfView fieldOfView;
    private ArrayList<int[]> tmpCellsInFov;
    boolean debugDrawFov, debugDrawCellsUnderFov, debugDrawGoals, debugDrawfacing;
    private boolean shouldDisposeTexture;
    private Vector2 runawayFromObjectsVector;
    private ArrayList<int[]> cellsWithObjects;

    private FixedGridMoveSequence movesMemory;

    protected Agent(Texture texture, boolean shouldDisposeTexture, float fov, float seeDistance, float speed, int movesMemorySize) {
        this(texture, shouldDisposeTexture, fov, seeDistance, speed, movesMemorySize, false, false, false, false);
    }

    protected Agent(Texture texture, boolean shouldDisposeTexture, float fov, float seeDistance, float speed, int movesMemorySize, boolean debugDrawFov, boolean debugDrawCellsUnderFov, boolean debugDrawGoals, boolean debugDrawfacing) {
        super(texture, speed);
        this.texture = texture;
        this.shouldDisposeTexture = shouldDisposeTexture;
        this.debugDrawFov = debugDrawFov;
        this.debugDrawCellsUnderFov = debugDrawCellsUnderFov;
        this.debugDrawGoals = debugDrawGoals;
        this.debugDrawfacing = debugDrawfacing;
        collidedAgents = new ArrayList<>();
        collidedObjects = new ArrayList<>();
        states = new StateManager(this);
        shapeRenderer = new ShapeRenderer();
        fieldOfView = new FieldOfView(this, fov, seeDistance);
        this.runawayFromObjectsVector  = new Vector2(0, 0);
        this.movesMemory = new FixedGridMoveSequence(movesMemorySize);
    }



    private void cleanupCollisions() {
        clearCollisionsWithWorld();
        clearCollisionsWithAgents();
        clearCollisionsWithObjects();
    }

    private void clearCollisionsWithObjects() {
        collidedObjects.clear();
    }

    public abstract void update();

    public void updateCellsInFov() {
        BaseEmptyLayer firstFloatLayer = getWorld().getBaseLayer();
        tmpCellsInFov = firstFloatLayer.getCellsWithinTriangle(fieldOfView.getFovTriangle());
        Vector2 pos = getPosition();

        // Remove the "origin" from it all
        for (int i = 0; i < tmpCellsInFov.size(); i++) {
            int[] cell = tmpCellsInFov.get(i);
            if ( firstFloatLayer.isPointInCell(pos, cell[1], cell[0])) {
                tmpCellsInFov.remove(i);
                break;
            }
        }

        // Remove invisible cells (raycasting)
        cellsWithObjects = getWorld().getWorldObjectsLayer().removeInvisibleCells(getPosition(), tmpCellsInFov);
    }

    public void onAddedToWorld() {
            this.movesMemory.setGrid(getWorld().getBaseLayer());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateMovesMemory();
        updateFov();
        updateCellsInFov();
        states.update();

        update();

        runAwayFromCollidingObjects();

        cleanupCollisions();
        cellsWithObjects.clear();
    }

    private void updateMovesMemory() {
        Vector2 pos = getPosition();
        int[] cell = getWorld().getBaseLayer().getCell(pos.x, pos.y);

        int[] lastCell = movesMemory.getLastCell();
        if (lastCell == null || lastCell[0] != cell[0] ||  lastCell[1] != cell[1] )
            movesMemory.addMove(cell);
    }

    private void runAwayFromCollidingObjects() {
        GoalMovement goalMovement = getGoalMovement();

        // First remove the goal, as there may actually be no object anymore
        goalMovement.removeGoalIfExists(runawayFromObjectsVector);

        // Now find the direction and scale it to hell
        if ( !collidedObjects.isEmpty() ) {
            runawayFromObjectsVector.set(getPosition());
            for (WorldObject o : collidedObjects)
                runawayFromObjectsVector.add(getPosition().sub(o.getPosition()).scl(500));
            goalMovement.addGoal(new WeightedGoal(runawayFromObjectsVector, goalMovement.getHighestWeight()));
        }
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

    public void addCollisionWithWorldObject(WorldObject o) {
        collidedObjects.add(o);
    }

    public void clearCollisionsWithAgents() {
        collidedAgents.clear();
    }

    public World getWorld() {
        return (World)getStage();
    }


    protected boolean areThereCellsWithValueAtFloatLayer(FloatLayer layer) {
        if ( tmpCellsInFov != null ) {
            for ( int[] cell : tmpCellsInFov) {
                int row = cell[0], col = cell[1];
                ValuePositionPair<Float> cellCentreAndValue = layer.getCellCentreAndValue(col, row);
                if ( cellCentreAndValue.getValue() > 0 ) return true;
            }
        }

        return false;
    }
    protected boolean areThereCellsWithValueAtFloatLayer(String layerName) {
        return areThereCellsWithValueAtFloatLayer((FloatLayer) getWorld().getGridLayers().get(layerName));

    }

    public ArrayList<Vector2> getCenterOfCellsInFieldOfView() {
        ArrayList<Vector2> ret = new ArrayList<>();

        if (tmpCellsInFov != null )
            for (int[] cell : tmpCellsInFov) {
            int row = cell[0], col = cell[1];
            ret.add(getWorld().getBaseLayer().getCellCentre(col, row));
        }

        return ret;
    }

    public ArrayList<Vector2> getCenterOfCellsWithOjects() {
        ArrayList<Vector2> ret = new ArrayList<>();

        if (cellsWithObjects != null )
            for (int[] cell : cellsWithObjects) {
                int row = cell[0], col = cell[1];
                ret.add(getWorld().getBaseLayer().getCellCentre(col, row));
            }

        return ret;
    }

    protected ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithValueForSomeFloatLayer(FloatLayer layer) {
        ArrayList<ValuePositionPair<Float>> ret = new ArrayList<>();

        if (tmpCellsInFov != null ) {
            for ( int[] cell : tmpCellsInFov) {
                int row = cell[0], col = cell[1];
                ValuePositionPair<Float> cellCentreAndValue = layer.getCellCentreAndValue(col, row);
                if ( cellCentreAndValue.getValue() > 0 ) ret.add(cellCentreAndValue);
            }
        }

        return ret;
    }

    protected ArrayList<ValuePositionPair<Float>> getCenterOfCellsInFieldOfViewWithValueForSomeFloatLayer(String layername) {
        return getCenterOfCellsInFieldOfViewWithValueForSomeFloatLayer((FloatLayer)getWorld().getGridLayers().get(layername));
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
            getWorld().getBaseLayer().getShapeRenderer().setProjectionMatrix(batch.getProjectionMatrix());
            for (int[] cell : tmpCellsInFov)
                // cell[1] has col, cell[0] has row
                getWorld().getBaseLayer().drawCell(batch, cell[1], cell[0], c);
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

    public ArrayList<WorldObject> getCollidedObjects() {
        return collidedObjects;
    }

    public void setDebugDrawFov(boolean debugDrawFov) {
        this.debugDrawFov = debugDrawFov;
    }

    public void setDebugDrawCellsUnderFov(boolean debugDrawCellsUnderFov) {
        this.debugDrawCellsUnderFov = debugDrawCellsUnderFov;
    }

    public void setDebugDrawGoals(boolean debugDrawGoals) {
        this.debugDrawGoals = debugDrawGoals;
    }

    public void setDebugDrawfacing(boolean debugDrawfacing) {
        this.debugDrawfacing = debugDrawfacing;
    }

    public void toggleDebugDrawFov() {
        debugDrawFov = !debugDrawFov;
    }

    public void toggleDebugDrawCellsUnderFov() {
        debugDrawCellsUnderFov = !debugDrawCellsUnderFov;
    }

    public void toggleDebugDrawGoals() {
        debugDrawGoals = !debugDrawGoals;
    }

    public void toggleDebugDrawfacing() {
        debugDrawfacing = !debugDrawfacing;
    }

    public void clearCollisionsWithWorld() {
        collisionsWithWorld = 0;
    }

    public void updateFov() {
        fieldOfView.update();
    }

    public FixedGridMoveSequence getMovesMemory() {
        return movesMemory;
    }
}

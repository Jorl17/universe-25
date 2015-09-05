package universe25.World;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import javafx.geometry.BoundingBox;
import universe25.Agents.Agent;
import universe25.Agents.Pheromones.Pheromone;
import universe25.Agents.Species;
import universe25.Agents.Stackable.Food.Bread;
import universe25.GameLogic.Movement.Pathfinding.GridCell;
import universe25.GameLogic.Time.Ticks;
import universe25.Objects.Stone;
import universe25.Objects.Wall;
import universe25.Objects.Wall2;
import universe25.Objects.WorldObject;
import universe25.Utils.RandomUtils;
import universe25.Utils.Scene2DShapeRenderer;
import universe25.World.GridLayers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jorl17 on 06/08/15.
 */
public class World extends Stage {
    private static final float TILE_SIZE = 8.0f;
    private BoundingBox worldBoundingBox;
    private Map<String, GridMapLayer> gridLayers;
    private ObjectAgentsLayer objectsAndAgentsLayer;
    private StackablesLayer stacksLayer;
    private RegionsLayer regionsLayer;
    private DirtLayer    dirtLayer;

    public World(Viewport viewport) {
        super(viewport);
        create();
        Scene2DShapeRenderer.updateProjectionMatrix(getBatch());
    }

    public void create() {
        this.worldBoundingBox = new BoundingBox(0,0,getWidth(), getHeight());
        Background background = new Background(Math.round (getHeight() / TILE_SIZE), Math.round(getWidth() / TILE_SIZE), TILE_SIZE);
        this.addActor(background);

        createLayersAndAddPheromones();
    }

    private void createLayersAndAddPheromones() {
        this.gridLayers = new HashMap<>();
        BaseEmptyLayer baseLayer = new BaseEmptyLayer(getWidth(), getHeight(), TILE_SIZE, "BaseLayer");
        stacksLayer = new StackablesLayer(getWidth(), getHeight(), TILE_SIZE, "Stackslayer", Color.MAROON, true, 100);
        objectsAndAgentsLayer = new ObjectAgentsLayer(getWidth(), getHeight(), TILE_SIZE, "ObjectsLayer", Color.BLACK, false);
        regionsLayer = new RegionsLayer(getWidth(), getHeight(), TILE_SIZE, "RegionsLayer", Color.BLUE, true);
        dirtLayer = new DirtLayer(getWidth(), getHeight(), TILE_SIZE, "DirtLayer", 100.0f, Color.YELLOW.cpy().mul(0.5f, 0.5f, 0.5f, 1.0f), this);

        addGridLayer(baseLayer);
        addPheromones();
        addGridLayer(regionsLayer);
        addGridLayer(dirtLayer);
        addGridLayer(stacksLayer);


        for (int i = 0; i < 60; i++) {
            Stone stone = new Stone();
            Vector2 pos = randomPosition();
            stone.setPosition(pos.x, pos.y);
            addActor(stone);
        }


        for (int i = 0; i < 15; i++) {
            Wall wall = new Wall();
            Vector2 pos = randomPosition();
            wall.setPosition(pos.x, pos.y);
            addActor(wall);
        }

        for (int i = 0; i < 20; i++) {
            Wall2 wall = new Wall2();
            Vector2 pos = randomPosition();
            wall.setPosition(pos.x, pos.y);
            addActor(wall);
        }

        ArrayList<GridCell> breadCells = new ArrayList<>();
        for(;;) {
            Vector2 pos = randomPosition();
            if (!(hit(pos.x, pos.y, false) instanceof WorldObject)) {
                GridCell firstCell = stacksLayer.getCell(pos.x, pos.y);
                for (int row = 0; row < 5; row++)
                    for (int col = 0; col < 5; col++) {
                        GridCell cell = stacksLayer.getCellAt(firstCell.getCol()+col, firstCell.getRow()+row);
                        if (cell != null)
                            breadCells.add(cell);
                    }

                break;
            }
        }
        new Bread(stacksLayer, breadCells).putInLayer();
        /*
        ArrayList<GridCell> antCells = new ArrayList<>();
        for(;;) {
            Vector2 pos = randomPosition();
            if (!(hit(pos.x, pos.y, false) instanceof WorldObject)) {
                GridCell firstCell = stacksLayer.getCell(pos.x, pos.y);
                for (int row = 0; row < 5; row++)
                    for (int col = 0; col < 5; col++) {
                        GridCell cell = stacksLayer.getCellAt(firstCell.getCol()+col, firstCell.getRow()+row);
                        if (cell != null)
                            antCells.add(cell);
                    }

                break;
            }
        }
        new AntPoison(stacksLayer, antCells).putInLayer();

        antCells.clear();
        for(;;) {
            Vector2 pos = randomPosition();
            if (!(hit(pos.x, pos.y, false) instanceof WorldObject)) {
                GridCell firstCell = stacksLayer.getCell(pos.x, pos.y);
                for (int row = 0; row < 5; row++)
                    for (int col = 0; col < 5; col++) {
                        GridCell cell = stacksLayer.getCellAt(firstCell.getCol()+col, firstCell.getRow()+row);
                        if (cell != null)
                            antCells.add(cell);
                    }

                break;
            }
        }
        new AntPoison(stacksLayer, antCells).putInLayer();
        */
        addGridLayer(objectsAndAgentsLayer);
    }

    @Override
    public void addActor(Actor actor) {
        super.addActor(actor);
        if ( actor instanceof  WorldObject )
            getAgentObjectsLayer().add((WorldObject) actor);
        else if ( actor instanceof  Agent ) {
            getAgentObjectsLayer().add((Agent) actor);
            ((Agent) actor).onAddedToWorld();
        }

        if ( getAgentObjectsLayer() != null )
            getAgentObjectsLayer().toFront();
    }

    private  void addGridLayer(GridMapLayer layer) {
        this.gridLayers.put(layer.getName(), layer);
        addActor(layer);
    }

    public void addPheromones() {
        ArrayList<Pheromone> pheromones = getPheromones();
        for ( Pheromone pheromone : pheromones ) {
            System.out.println("World registering " + pheromone);
            pheromone.registerWithWorld(this);
        }
    }

    @Override
    public void act(float deltaTime) {
        Ticks.increaseTicks();

        checkCollisions();

        for (Pheromone p: getPheromones()) {
            p.evaporate();
            //p.spread();
        }

        super.act(deltaTime);

        for ( GridMapLayer l: gridLayers.values() ) {
            l.onTickFinished();
        }

    }

    private void checkCollisions() {
        collideWithWorld();
        collideAgentsWithObjectsAndThemselves();
    }

    private void collideAgentsWithObjectsAndThemselves() {
        Array<Actor> actors = getActors();
        for (int i = 0; i <  actors.size ; i++ )
            if (actors.get(i) instanceof Agent) {
                for (int j = i + 1; j < actors.size; j++) {
                    if (actors.get(j) instanceof Agent) {
                        if (((Agent) actors.get(i)).interesects((Agent) actors.get(j))) {
                            ((Agent) actors.get(i)).addCollisionWithAgent((Agent) actors.get(j));
                            ((Agent) actors.get(j)).addCollisionWithAgent((Agent) actors.get(i));
                        }
                    }
                    else if (actors.get(j) instanceof WorldObject) {
                        Vector2 pos = ((Agent) actors.get(i)).getPosition();
                        float occlusionPercentage = getAgentObjectsLayer().getOcclusionPercentage(pos.x, pos.y);
                        if (((Agent) actors.get(i)).interesects((WorldObject) actors.get(j)) &&
                                occlusionPercentage > 0.25) {
                            resolveAgentObjectCollision((WorldObject)actors.get(j), (Agent)actors.get(i));
                        }
                    }
                }
            } else if ( actors.get(i) instanceof WorldObject) {
                for (int j = i+1; j < actors.size; j++)
                    if ( actors.get(j) instanceof Agent) {
                        Vector2 pos = ((Agent) actors.get(j)).getPosition();
                        float occlusionPercentage = getAgentObjectsLayer().getOcclusionPercentage(pos.x, pos.y);
                        if (((WorldObject) actors.get(i)).interesects((Agent) actors.get(j)) &&
                                occlusionPercentage > 0.30) {
                            resolveAgentObjectCollision((WorldObject) actors.get(i), (Agent) actors.get(j));
                        }
                    }
            }


    }

    private void resolveAgentObjectCollision(WorldObject worldObject, Agent agent) {
        agent.addCollisionWithWorldObject(worldObject);
        Vector2 line = agent.getPosition().sub(worldObject.getPosition()).nor().scl(agent.getSpeed()/2);
        float occlusionPercentage;

        if ( false )
        do {
            agent.moveBy(line.x,line.y);
            Vector2 pos = agent.getPosition();
            if ( getAgentObjectsLayer().getCell(pos.x, pos.y) == null )
                occlusionPercentage = 1.0f;
            else
                occlusionPercentage = getAgentObjectsLayer().getOcclusionPercentage(pos.x, pos.y);
        } while ( agent.interesects(worldObject) && occlusionPercentage > 0.30 );

        agent.clearCollisionsWithWorld();
        if ( collideAgentWithWorld(agent) != 0 ) ;
        /*    do {
                agent.moveBy(-line.x,-line.y);
            } while ( agent.interesects(worldObject) );*/

        /*if ( collideAgentWithWorld(agent) != 0 )
            System.out.println("Expect shit!");*/
    }

    private void collideWithWorld() {
        for ( Actor actor : getActors() )
            if ( actor instanceof Agent )
                collideAgentWithWorld((Agent)actor);
    }

    public int collideAgentWithWorld(Agent agent) {
        BoundingBox agentbb = agent.getBoundingBox();

        int collisions = 0;

        if ( agentbb.getMinX() <= worldBoundingBox.getMinX()  ) {
            agent.setX((float) worldBoundingBox.getMinX()  + agent.getBoundingBoxThreshold());
            agent.addCollisionWithWorld(Agent.COLLIDED_LEFT);
        } else if ( agentbb.getMaxX() >= worldBoundingBox.getMaxX() ) {
            agent.setX((float) (worldBoundingBox.getMaxX() -  agentbb.getWidth()  - agent.getBoundingBoxThreshold()));
            agent.addCollisionWithWorld(Agent.COLLIDED_RIGHT);
        }

        if ( agentbb.getMinY() <= worldBoundingBox.getMinY()  ) {
            agent.setY((float) worldBoundingBox.getMinY() + agent.getBoundingBoxThreshold());
            agent.addCollisionWithWorld(Agent.COLLIDED_BOTTOM);
        } else if ( agentbb.getMaxY() >= worldBoundingBox.getMaxY() ) {
            agent.setY((float) (worldBoundingBox.getMaxY() - agentbb.getHeight() - agent.getBoundingBoxThreshold()));
            agent.addCollisionWithWorld(Agent.COLLIDED_TOP);
        }

        return agent.getCollisionsWithWorld();
    }

    @Override
    public void dispose() {
        for ( Actor actor : getActors() )
            if ( actor instanceof Disposable )
                ((Disposable) actor).dispose();
        super.dispose();
    }

    public Map<String, GridMapLayer> getGridLayers() {
        return gridLayers;
    }

    public BaseEmptyLayer getBaseLayer() {
        return (BaseEmptyLayer) gridLayers.get("BaseLayer");
    }

    public ObjectAgentsLayer getAgentObjectsLayer() {
        return objectsAndAgentsLayer;//return (WorldObjectsLayer) gridLayers.get("ObjectsLayer");
    }

    public Vector2 randomPosition() {
        return new Vector2(RandomUtils.rand(0, getWidth()), RandomUtils.rand(0, getHeight()));
    }

    public ArrayList<Agent> getAllAgents() {
        Array<Actor> actors = getActors();
        ArrayList<Agent> ret = new ArrayList<>();
        for ( Actor a : actors )
            if ( a instanceof Agent )
                ret.add((Agent) a);

        return ret;
    }

    public void registerPheromoneLayer(String worldLayerName, float pheromoneLayerMax, Color color) {
        PheromoneMapLayer layer = new PheromoneMapLayer(getWidth(), getHeight(), TILE_SIZE, worldLayerName, pheromoneLayerMax, color);
        addGridLayer(layer);
    }

    public ArrayList<Pheromone> getPheromones() {
        return Species.getAllPheromones();
    }

    public static float getTileSize() {
        return TILE_SIZE;
    }

    public StackablesLayer getStacksLayer() {
        return stacksLayer;
    }

    public ArrayList<WorldObject> getAllObjects() {
        Array<Actor> actors = getActors();
        ArrayList<WorldObject> ret = new ArrayList<>();
        for (Actor a : actors)
            if (a instanceof WorldObject)
                ret.add((WorldObject) a);

        return ret;
    }

    public RegionsLayer getRegionsLayer() {
        return regionsLayer;
    }

    public DirtLayer getDirtLayer() {
        return dirtLayer;
    }
}

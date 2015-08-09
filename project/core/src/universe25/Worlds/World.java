package universe25.Worlds;

import com.badlogic.gdx.Gdx;
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
import universe25.Agents.SpeciesAgent;
import universe25.GameLogic.Time.Ticks;
import universe25.Worlds.GridLayers.BaseEmptyLayer;
import universe25.Worlds.GridLayers.GridMapLayer;
import universe25.Worlds.GridLayers.TestFoodLayer;
import universe25.Worlds.GridLayers.PheromoneMapLayer;

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

    public World(Viewport viewport) {
        super(viewport);
        create();
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
        TestFoodLayer foodLayer = new TestFoodLayer(getWidth(), getHeight(), TILE_SIZE, "FoodLayer", 100);

        addGridLayer(baseLayer);
        addPheromones();
        addGridLayer(foodLayer);

        for (int i =0; i < 2 ; i++) {
            Vector2 pos = randomPosition();
            foodLayer.putFoodAt(pos.x, pos.y, 100);
        }
    }

    private  void addGridLayer(GridMapLayer layer) {
        this.gridLayers.put(layer.getName(), layer);
        addActor(layer);
    }

    public void addPheromones() {
        ArrayList<Pheromone> pheromones = SpeciesAgent.getPheromones();
        for ( Pheromone pheromone : pheromones ) {
            System.out.println("World registering " + pheromone);
            pheromone.registerWithWorld(this);
        }
    }

    @Override
    public void act(float deltaTime) {
        Ticks.increaseTicks();
        System.out.println(Gdx.graphics.getFramesPerSecond());
        checkCollisions();

        for (Pheromone p: getPheromones())
                p.evaporate();

        super.act(deltaTime);
    }

    private void checkCollisions() {
        collideWithWorld();
        collideAgents();
    }

    private void collideAgents() {
        Array<Actor> actors = getActors();
        for (int i = 0; i <  actors.size ; i++ )
            if (actors.get(i) instanceof Agent)
                for (int j = i+1; j < actors.size; j++)
                    if ( actors.get(j) instanceof Agent)
                        if ( ((Agent) actors.get(i)).interesects((Agent) actors.get(j)) ) {
                            ((Agent) actors.get(i)).addCollisionWithAgent((Agent) actors.get(j));
                            ((Agent) actors.get(j)).addCollisionWithAgent((Agent) actors.get(i));
                        }


    }

    private void collideWithWorld() {
        for ( Actor actor : getActors() )
            if ( actor instanceof Agent )
                collideAgentWithWorld((Agent)actor);
    }

    private void collideAgentWithWorld(Agent agent) {
        BoundingBox agentbb = agent.getBoundingBox();

        if ( agentbb.getMinX() <= worldBoundingBox.getMinX()  ) {
            agent.setX((float) worldBoundingBox.getMinX());
            agent.addCollisionWithWorld(Agent.COLLIDED_LEFT);
        } else if ( agentbb.getMaxX() >= worldBoundingBox.getMaxX() ) {
            agent.setX((float) (worldBoundingBox.getMaxX() -  agentbb.getWidth()));
            agent.addCollisionWithWorld(Agent.COLLIDED_RIGHT);
        }

        if ( agentbb.getMinY() <= worldBoundingBox.getMinY()  ) {
            agent.setY((float) worldBoundingBox.getMinY());
            agent.addCollisionWithWorld(Agent.COLLIDED_BOTTOM);
        } else if ( agentbb.getMaxY() >= worldBoundingBox.getMaxY() ) {
            agent.setY((float) (worldBoundingBox.getMaxY() - agentbb.getHeight()));
            agent.addCollisionWithWorld(Agent.COLLIDED_TOP);
        }
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

    public Vector2 randomPosition() {
        return new Vector2((float)Math.random()*getWidth(), (float)Math.random()*getHeight());
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
        return SpeciesAgent.getPheromones();
    }

    public static float getTileSize() {
        return TILE_SIZE;
    }
}

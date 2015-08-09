package universe25.Agents.Worlds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import javafx.geometry.BoundingBox;
import universe25.Agents.Agent;
import universe25.Agents.Worlds.GridLayers.GridMapLayer;
import universe25.Agents.Worlds.GridLayers.TestFoodLayer;
import universe25.Agents.Worlds.GridLayers.TestPheromoneMapLayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jorl17 on 06/08/15.
 */
public class World extends Stage {
    private static final float TILE_SIZE = 16.0f;
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
        createGridLayers();
    }

    private void createGridLayers() {
        this.gridLayers = new HashMap<>();
        TestPheromoneMapLayer layer = new TestPheromoneMapLayer(getWidth(), getHeight(), TILE_SIZE, "TestPheromoneLayer", 500, Color.YELLOW);
        TestPheromoneMapLayer foodPheromoneLayer = new TestPheromoneMapLayer(getWidth(), getHeight(), TILE_SIZE, "FoodPheromoneLayer", 500, Color.CYAN);
        TestFoodLayer foodLayer = new TestFoodLayer(getWidth(), getHeight(), TILE_SIZE, "FoodLayer", 100);

        addGridLayer(layer);
        addGridLayer(foodPheromoneLayer);
        addGridLayer(foodLayer);
        //for (int i = 0; i < 10; i++)
        //    layer.increasePheromoneAt((float)Math.random()*getWidth(),(float)Math.random()*getHeight(),(float)Math.random()*50+50);
        /*layer.increasePheromoneAt(150,150,100);
        layer.increasePheromoneAt(300,5,100);
        layer.increasePheromoneAt(5,300,100);*/
        //layer.increasePheromoneAt(5,300,100);
        /*foodLayer.putFoodAt(250, 250, 100);

        foodLayer.putFoodAt(450, 150, 100);
        foodLayer.putFoodAt(350, 300, 100);*/
        for (int i =0; i < 1 ; i++) {
            Vector2 pos = randomPosition();
            foodLayer.putFoodAt(pos.x, pos.y, 100);
        }
    }

    private  void addGridLayer(GridMapLayer layer) {
        this.gridLayers.put(layer.getName(), layer);
        addActor(layer);
    }

    @Override
    public void act(float deltaTime) {
        checkCollisions();
        /*((TestPheromoneMapLayer)this.gridLayers.get("TestPheromoneLayer")).evaporate(0.8f);
        ((TestPheromoneMapLayer)this.gridLayers.get("FoodPheromoneLayer")).evaporate(0.5f);*/
        ((TestPheromoneMapLayer)this.gridLayers.get("TestPheromoneLayer")).evaporate(0.2f);
        ((TestPheromoneMapLayer)this.gridLayers.get("FoodPheromoneLayer")).evaporate(0.05f);
        ((TestPheromoneMapLayer)this.gridLayers.get("FoodPheromoneLayer")).spread(0.002f, 0.1f);
        ((TestPheromoneMapLayer)this.gridLayers.get("TestPheromoneLayer")).spread(0.002f, 0.1f);
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

    public Vector2 randomPosition() {
        return new Vector2((float)Math.random()*getWidth(), (float)Math.random()*getHeight());
    }
}

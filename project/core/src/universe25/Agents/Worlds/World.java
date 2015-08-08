package universe25.Agents.Worlds;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import javafx.geometry.BoundingBox;
import universe25.Agents.Agent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jorl17 on 06/08/15.
 */
public class World extends Stage {
    private static final float TILE_SIZE = 32.0f;
    private BoundingBox worldBoundingBox;
    private Map<String, GridMapLayer> gridLayers;

    public World(Viewport viewport) {
        super(viewport);
        create();
    }

    public void create() {
        this.worldBoundingBox = new BoundingBox(0,0,getWidth(), getHeight());
        Background background = new Background(Math.round (getHeight() / TILE_SIZE), Math.round(getWidth() / TILE_SIZE), 32);
        this.addActor(background);
        createGridLayers();
    }

    private void createGridLayers() {
        this.gridLayers = new HashMap<>();
        TestPheromoneMapLayer layer = new TestPheromoneMapLayer(getWidth(), getHeight(), TILE_SIZE, "TestLayer", 100);
        this.gridLayers.put(layer.getName(), layer);
        layer.increasePheromoneAt(50,50,10);
        layer.increasePheromoneAt(150,150,100);
        addActor(layer);
    }

    @Override
    public void act(float deltaTime) {
        checkCollisions();
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
            agent.setCollidedWithWorld(true);
        } else if ( agentbb.getMaxX() >= worldBoundingBox.getMaxX() ) {
            agent.setX((float) (worldBoundingBox.getMaxX() -  agentbb.getWidth()));
            agent.setCollidedWithWorld(true);
        }

        if ( agentbb.getMinY() <= worldBoundingBox.getMinY()  ) {
            agent.setY((float) worldBoundingBox.getMinY());
            agent.setCollidedWithWorld(true);
        } else if ( agentbb.getMaxY() >= worldBoundingBox.getMaxY() ) {
            agent.setY((float) (worldBoundingBox.getMaxY() - agentbb.getHeight()));
            agent.setCollidedWithWorld(true);
        }
    }

    @Override
    public void dispose() {
        for ( Actor actor : getActors() )
            if ( actor instanceof Disposable )
                ((Disposable) actor).dispose();
        super.dispose();
    }
}

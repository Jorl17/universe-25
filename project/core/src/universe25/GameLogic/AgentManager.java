package universe25.GameLogic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import javafx.geometry.BoundingBox;
import universe25.Background;

import java.util.ArrayList;

/**
 * Created by jorl17 on 06/08/15.
 */
public class AgentManager extends Stage{
    private static final float TILE_SIZE = 32.0f;
    private BoundingBox worldBoundingBox;

    public AgentManager(Viewport viewport) {
        super(viewport);
        create();
    }

    public void create() {
        this.worldBoundingBox = new BoundingBox(0,0,getWidth(), getHeight());
        Background background = new Background(Math.round (getHeight() / TILE_SIZE), Math.round(getWidth() / TILE_SIZE), 32);
        this.addActor(background);
    }

    @Override
    public void act() {
        checkCollisions();
        super.act();
    }

    private void checkCollisions() {
        collideWithWorld();
        collideAgents();
    }

    private void collideAgents() {
        Array<Actor> actors = getActors();
        for (int i = 0; i <  actors.size ; i++ )
            if (actors.get(i) instanceof  Agent)
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
        } else if ( agentbb.getMinX() + agentbb.getWidth() >= worldBoundingBox.getMinX() + worldBoundingBox.getWidth() ) {
            agent.setX((float) (worldBoundingBox.getMinX() + worldBoundingBox.getWidth()));
            agent.setCollidedWithWorld(true);
        }

        if ( agentbb.getMinY() <= worldBoundingBox.getMinY()  ) {
            agent.setY((float) worldBoundingBox.getMinY());
            agent.setCollidedWithWorld(true);
        } else if ( agentbb.getMinY() + agentbb.getHeight() >= worldBoundingBox.getMinY() + worldBoundingBox.getHeight() ) {
            agent.setY((float) (worldBoundingBox.getMinY() + worldBoundingBox.getHeight()));
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

package universe25;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import universe25.Agents.Agent;
import universe25.Agents.Regions.Hive;
import universe25.Agents.SimplisticAnt.PheromoneFollowingAnt;
import universe25.Agents.SimplisticAnt.SimplisticAnt;
import universe25.Agents.SimplisticAnt.SimplisticAntSpecies;
import universe25.GameLogic.Movement.MoveSequence.MoveSequence;
import universe25.GameLogic.Movement.Pathfinding.PathFinder;
import universe25.Objects.Stone;
import universe25.Objects.WorldObject;
import universe25.Utils.RandomUtils;
import universe25.World.World;
import universe25.GameLogic.Movement.GoalMovement;
import universe25.GameLogic.Movement.WeightedGoal;

import java.util.Random;

public class Main extends ApplicationAdapter {
    private World stage;

    @Override
    public void create () {

        SimplisticAntSpecies species = new SimplisticAntSpecies("Species1");

        stage = new World(new FitViewport(640, 480));
        Gdx.input.setInputProcessor(stage);


        Hive<SimplisticAntSpecies> hive = new Hive<>(stage.getRegionsLayer(), species, 10, 10);
        hive.putInLayer();

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("x, y" + x + " " + y);
                Vector2 coord = new Vector2(x, y);//stage.screenToStageCoordinates(new Vector2(x, y));
                System.out.println("coord=" + coord);
                Actor hitActor = stage.hit(coord.x, coord.y, false);
                if (hitActor != null)
                    Gdx.app.log("info", "hit " + hitActor.getName());
                //return super.touchDown(event, x, y, pointer, button);
                return false;
            }



            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if ( keycode == Input.Keys.Q ) {
                    stage.getAllAgents().forEach(Agent::toggleDebugDrawCellsUnderFov);
                } else if ( keycode == Input.Keys.W ) {
                    stage.getAllAgents().forEach(Agent::toggleDebugDrawfacing);
                } else if ( keycode == Input.Keys.E ) {
                    stage.getAllAgents().forEach(Agent::toggleDebugDrawFov);
                } else if ( keycode == Input.Keys.R ) {
                    stage.getAllAgents().forEach(Agent::toggleDebugDrawGoals);
                } else if ( keycode == Input.Keys.A ) {
                    species.getPathPheromone().getWorldLayer().toggleDrawLayer();
                } else if ( keycode == Input.Keys.S ) {
                    species.getFoodPheromone().getWorldLayer().toggleDrawLayer();
                } else if ( keycode == Input.Keys.D ) {
                    species.getFoodImmediancyPheromone().getWorldLayer().toggleDrawLayer();
                } else if ( keycode == Input.Keys.F ) {
                    species.getHivePheromone().getWorldLayer().toggleDrawLayer();
                }
                else if ( keycode == Input.Keys.J ) {
                    stage.getStacksLayer().toggleDrawLayer();
                } else if ( keycode == Input.Keys.K ) {
                    stage.getRegionsLayer().toggleDrawLayer();
                } else if ( keycode == Input.Keys.L ) {
                    stage.getWorldObjectsLayer().toggleDrawLayer();
                }

                return true;
            }


        });

        /*for (int i = 0; i < 100; i++) {
            PheromoneFollowingAnt ant = new PheromoneFollowingAnt();
            Vector2 p;
            do {
                p = stage.randomPosition();
                ant.setPosition(p);
            } while ( stage.hit(p.x, p.y, false) instanceof WorldObject );
            stage.addActor(ant);
        }*/



        Vector2 p = hive.getHiveCenter();
        /*do {
            p = stage.randomPosition();
        } while ( stage.hit(p.x, p.y, false) instanceof WorldObject );*/
        for (int i = 0; i < 100; i++) {
            SimplisticAnt ant = species.newIndividual();
            ant.setPosition(p.x,p.y);
            ant.setRotation(RandomUtils.rand(0, 360));
            stage.addActor(ant);
        }

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if ( button == Input.Buttons.LEFT ) {
                    Stone s = new Stone();
                    s.setPosition(x-s.getWidth()/2,y-s.getHeight()/2);
                    stage.addActor(s);
                } else {
                    PathFinder pathFinder = new PathFinder(stage.getWorldObjectsLayer());
                    for (Agent agent : stage.getAllAgents()) {
                        MoveSequence pathMoveSequence = pathFinder.getPathMoveSequence(agent.getPosition(), new Vector2(x, y));
                        agent.testDoMoveSequence(pathMoveSequence);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0xBC, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

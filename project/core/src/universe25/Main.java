package universe25;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import universe25.Agents.Agent;
import universe25.Agents.PheromoneFollowingAnt;
import universe25.Agents.ScouterAnt;
import universe25.Agents.Worlds.World;
import universe25.GameLogic.Movement.GoalMovement;
import universe25.GameLogic.Movement.WeightedGoal;
import universe25.Agents.SimplisticAnt;

public class Main extends ApplicationAdapter {
    private World stage;

    @Override
    public void create () {
        stage = new World(new FitViewport(640, 480));
        Gdx.input.setInputProcessor(stage);

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
                if (keycode == Input.Keys.DOWN) {
                    Agent agent = (Agent) stage.getActors().get(2);
                    GoalMovement goalMovement = agent.getGoalMovement();
                    if (!goalMovement.hasGoals())
                        goalMovement.setGoal(new Vector2(250, 250));
                    /*
                    Seek<Vector2> seek =
                            new Seek<Vector2>(agent, new SteerableScene2DLocation(50, 50));
                    Seek<Vector2> seek2 =
                            new Seek<Vector2>(agent, new SteerableScene2DLocation(450, 450));
                    BlendedSteering<Vector2> vector2BlendedSteering = new BlendedSteering<Vector2>(agent);
                    vector2BlendedSteering.add(new BlendedSteering.BehaviorAndWeight<Vector2>(seek, 10));
                    vector2BlendedSteering.add(new BlendedSteering.BehaviorAndWeight<Vector2>(seek2, 30));
                    agent.setSteeringBehavior(vector2BlendedSteering);*/

                    /*agent.setPosition(200,200);
                    Wander<Vector2> wander =
                            new Wander<Vector2>(agent).setFaceEnabled(false).setWanderOffset(1.50f).setWanderRadius(2.0f).setWanderRate((float) (Math.PI/6.0f));
                    agent.setSteeringBehavior(wander);*/

                    //agent.addAction(Actions.moveBy(0, -20.0f, 0.5f));
                } else if ( keycode == Input.Keys.UP ) {
                    Agent agent = (Agent) stage.getActors().get(2);
                    GoalMovement goalMovement = agent.getGoalMovement();
                    goalMovement.setWeightedGoals(new WeightedGoal(new Vector2(250,250), 1.0f), new WeightedGoal(new Vector2(100,10), 0.5f));
                } else if ( keycode == Input.Keys.LEFT ) {
                    Agent agent = (Agent) stage.getActors().get(2);
                    agent.rotateBy(30);
                }

                return true;
            }


        });

        PheromoneFollowingAnt simplisticAnt = new PheromoneFollowingAnt();
        //simplisticAnt.setPosition(250,250);

        simplisticAnt.setPosition(250, 250);
        simplisticAnt.setRotation(90);
        //stage.addActor(simplisticAnt);
        for (int i = 0; i < 200; i++) {
            PheromoneFollowingAnt ant = new PheromoneFollowingAnt();
            ant.setPosition(stage.randomPosition());
            stage.addActor(ant);
        }

        for (int i = 0; i < 3; i++) {
            ScouterAnt ant = new ScouterAnt();
            ant.setPosition(stage.randomPosition());
            stage.addActor(ant);
        }

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

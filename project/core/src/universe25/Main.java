package universe25;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;

    @Override
    public void create () {
        stage = new Stage(new FitViewport(640, 480));
        Gdx.input.setInputProcessor(stage);

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("x, y" + x + " " + y);
                Vector2 coord = new Vector2(x,y);//stage.screenToStageCoordinates(new Vector2(x, y));
                System.out.println("coord=" + coord);
                Actor hitActor = stage.hit(coord.x, coord.y, false);
                if ( hitActor != null )
                    Gdx.app.log("info", "hit " + hitActor.getName());
                //return super.touchDown(event, x, y, pointer, button);
                return false;
            }
        });


        Background background = new Background((int)Math.round (stage.getHeight() / 32.0), (int)Math.round(stage.getWidth() / 32.0), 32);
        //background.setPosition(0, stage.getHeight());
        stage.addActor(background);
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

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
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    class MyActor extends Image {

        public MyActor() {
            super(new Texture("badlogic.jpg"));
            setBounds(getX(), getY(), getWidth(), getHeight());
            setTouchable(Touchable.enabled);

            addListener(new InputListener() {
                @Override
                public boolean keyDown(InputEvent event, int keycode) {
                    if (keycode == Input.Keys.RIGHT) {
                        ColorAction c = new ColorAction();
                        c.setEndColor(Color.BLUE);
                        c.setDuration(5.0f);
                        ColorAction c2 = new ColorAction();
                        c2.setEndColor(getColor());
                        c2.setDuration(5.0f);
                        RepeatAction forever = Actions.forever(Actions.sequence(c, c2));
                        MyActor.this.addAction(forever);
                    } else if (keycode == Input.Keys.LEFT) {
                        ((RepeatAction) getActions().first()).finish();
                    }
                    return true;
                }
            });

        }

        @Override
        public void act(float delta) {
            super.act(delta);
        }
    }

    @Override
    public void create () {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        MyActor actor = new MyActor();
        stage.addActor(actor);
        stage.setKeyboardFocus(actor);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0xBC, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}

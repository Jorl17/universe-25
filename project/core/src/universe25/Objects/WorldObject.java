package universe25.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import universe25.GameLogic.Movement.BoundingBoxImage;

/**
 * Created by jorl17 on 10/08/15.
 */
public class WorldObject extends BoundingBoxImage implements Disposable {
    private Texture texture;
    public WorldObject(Texture texture) {
        super(texture);
        this.texture = texture;
    }

    @Override
    public void dispose() {
        if ( texture != null )
            texture.dispose();
    }
}

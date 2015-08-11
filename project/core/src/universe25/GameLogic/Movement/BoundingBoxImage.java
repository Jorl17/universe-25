package universe25.GameLogic.Movement;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import javafx.geometry.BoundingBox;

/**
 * Created by jorl17 on 10/08/15.
 */
public class BoundingBoxImage extends Image{
    protected BoundingBox boundingBox;

    public BoundingBoxImage(NinePatch patch) {
        super(patch);
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    public BoundingBoxImage(TextureRegion region) {
        super(region);
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    public BoundingBoxImage(Texture texture) {
        super(texture);
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    public BoundingBoxImage(Skin skin, String drawableName) {
        super(skin, drawableName);
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    public BoundingBoxImage(Drawable drawable) {
        super(drawable);
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    public BoundingBoxImage(Drawable drawable, Scaling scaling) {
        super(drawable, scaling);
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    public BoundingBoxImage(Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        boundingBox = new BoundingBox(x, y, width, height);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        boundingBox = new BoundingBox(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        boundingBox = new BoundingBox(getX(), getY(), getWidth(), getHeight());
    }

    public boolean interesects(BoundingBoxImage o) {
        return boundingBox.intersects(o.boundingBox);
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public Vector2 getPosition() {
        return new Vector2(getX(Align.center), getY(Align.center));
    }

    public Vector2[] getBoundingBoxPoints() {
        Vector2[] ret = new Vector2[4];
        ret[0] = new Vector2((float)boundingBox.getMinX(),(float)boundingBox.getMinY());
        ret[1] = new Vector2((float)boundingBox.getMinX(),(float)boundingBox.getMaxY());
        ret[2] = new Vector2((float)boundingBox.getMaxX(),(float)boundingBox.getMaxY());
        ret[3] = new Vector2((float)boundingBox.getMaxX(),(float)boundingBox.getMinY());

        return ret;
    }


    public Polygon getBoundingBoxAsPolygon() {
        return new Polygon(new float[]{
                (float) boundingBox.getMinX(), (float) boundingBox.getMinY(),
                (float) boundingBox.getMinX(), (float) boundingBox.getMaxY(),
                (float) boundingBox.getMaxX(), (float) boundingBox.getMaxY(),
                (float) boundingBox.getMaxX(), (float) boundingBox.getMinY()
        });
    }
}

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
import universe25.Objects.WorldObject;

/**
 * Created by jorl17 on 10/08/15.
 */
public class BoundingBoxImage extends Image{
    protected BoundingBox boundingBox;
    private float boundingBoxThreshold = 0.0f;
    private boolean opaque;

    public BoundingBoxImage(NinePatch patch) {
        this(patch, true);
    }

    public BoundingBoxImage(NinePatch patch, boolean opaque) {
        super(patch);
        this.opaque = opaque;
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    public BoundingBoxImage(TextureRegion region) {
        this(region, true);
    }

    public BoundingBoxImage(TextureRegion region, boolean opaque) {
        super(region);
        this.opaque = opaque;
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    public BoundingBoxImage(Texture texture) {
        this(texture, true);
    }

    public BoundingBoxImage(Texture texture, boolean opaque) {
        super(texture);
        this.opaque = opaque;
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    public BoundingBoxImage(Skin skin, String drawableName) {
        this(skin, drawableName, true);
    }

    public BoundingBoxImage(Skin skin, String drawableName, boolean opaque) {
        super(skin, drawableName);
        this.opaque = opaque;
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    public BoundingBoxImage(Drawable drawable) {
        this(drawable, true);
    }

    public BoundingBoxImage(Drawable drawable, boolean opaque) {
        super(drawable);
        this.opaque = opaque;
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    public BoundingBoxImage(Drawable drawable, Scaling scaling, boolean opaque) {
        super(drawable, scaling);
        this.opaque = opaque;
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    public BoundingBoxImage(Drawable drawable, Scaling scaling, int align) {
        this(drawable, scaling, align, true);
    }

    public BoundingBoxImage(Drawable drawable, Scaling scaling, int align, boolean opaque) {
        super(drawable, scaling, align);
        this.opaque = opaque;
        setBounds(getX(), getY(), getWidth(), getHeight());
        setTouchable(Touchable.enabled);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        boundingBox = new BoundingBox(getX()+boundingBoxThreshold, getY()+boundingBoxThreshold,
                                      getWidth()-boundingBoxThreshold, getHeight()-boundingBoxThreshold);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        boundingBox = new BoundingBox(getX()+boundingBoxThreshold, getY()+boundingBoxThreshold,
                getWidth()-boundingBoxThreshold, getHeight()-boundingBoxThreshold);
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        boundingBox = new BoundingBox(getX()+boundingBoxThreshold, getY()+boundingBoxThreshold,
                getWidth()-boundingBoxThreshold, getHeight()-boundingBoxThreshold);
    }

    public boolean interesects(BoundingBoxImage o) {
        return isOpaque() && o.isOpaque() && boundingBox.intersects(o.boundingBox);
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

    public void setBoundingBoxThreshold(float boundingBoxThreshold) {
        this.boundingBoxThreshold = boundingBoxThreshold;
        boundingBox = new BoundingBox(getX()+boundingBoxThreshold, getY()+boundingBoxThreshold,
                getWidth()-boundingBoxThreshold, getHeight()-boundingBoxThreshold);
    }

    public float getBoundingBoxThreshold() {
        return boundingBoxThreshold;
    }

    public boolean isOpaque() {
        return opaque;
    }
}

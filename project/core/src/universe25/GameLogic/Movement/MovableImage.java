package universe25.GameLogic.Movement;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import universe25.SteerableImage.Scene2dSteeringUtils;

/**
 * Created by jorl17 on 07/08/15.
 */
public class MovableImage extends Image {
    private final GoalMovement goalMovement;
    private static final float DEFAULT_SPEED = 1.0f;
    private float speed = DEFAULT_SPEED;

    public MovableImage() {
        goalMovement = new GoalMovement(this);
    }

    public MovableImage(NinePatch patch) {
        super(patch);
        goalMovement = new GoalMovement(this);
    }

    public MovableImage(TextureRegion region) {
        super(region);
        goalMovement = new GoalMovement(this);
    }

    public MovableImage(Texture texture) {
        super(texture);
        goalMovement = new GoalMovement(this);
    }

    public MovableImage(Skin skin, String drawableName) {
        super(skin, drawableName);
        goalMovement = new GoalMovement(this);
    }

    public MovableImage(Drawable drawable) {
        super(drawable);
        goalMovement = new GoalMovement(this);
    }

    public MovableImage(Drawable drawable, Scaling scaling) {
        super(drawable, scaling);
        goalMovement = new GoalMovement(this);
    }

    public MovableImage(Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
        goalMovement = new GoalMovement(this);
    }

    private void move() {
        goalMovement.updateGoalDirection();
        Vector2 movementSpeedVector = goalMovement.getMovementSpeedVector(speed);
        moveBy(movementSpeedVector.x, movementSpeedVector.y);

        setRotation(-(float) (MathUtils.radiansToDegrees*Math.atan(movementSpeedVector.x / movementSpeedVector.y)));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        move();
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public GoalMovement getGoalMovement() {
        return goalMovement;
    }
}

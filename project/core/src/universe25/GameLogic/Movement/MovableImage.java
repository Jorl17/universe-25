package universe25.GameLogic.Movement;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

/**
 * Created by jorl17 on 07/08/15.
 */
public class MovableImage extends BoundingBoxImage {
    private final GoalMovement goalMovement;
    private float speed;
    private Vector2 lastMove;

    public MovableImage(NinePatch patch, float speed) {
        super(patch);
        setOrigin(getX(Align.center), getY(Align.center));
        goalMovement = new GoalMovement(this);
        this.speed = speed;
    }

    public MovableImage(TextureRegion region, float speed) {
        super(region);
        setOrigin(getX(Align.center), getY(Align.center));
        goalMovement = new GoalMovement(this);
        this.speed = speed;
    }

    public MovableImage(Texture texture, float speed) {
        super(texture);
        setOrigin(getX(Align.center), getY(Align.center));
        goalMovement = new GoalMovement(this);
        this.speed = speed;
    }

    public MovableImage(Skin skin, String drawableName, float speed) {
        super(skin, drawableName);
        setOrigin(getX(Align.center), getY(Align.center));
        goalMovement = new GoalMovement(this);
        this.speed = speed;
    }

    public MovableImage(Drawable drawable, float speed) {
        super(drawable);
        setOrigin(getX(Align.center), getY(Align.center));
        goalMovement = new GoalMovement(this);
        this.speed = speed;
    }

    public MovableImage(Drawable drawable, Scaling scaling, float speed) {
        super(drawable, scaling, true);
        setOrigin(getX(Align.center), getY(Align.center));
        goalMovement = new GoalMovement(this);
        this.speed = speed;
    }

    public MovableImage(Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
        setOrigin(getX(Align.center), getY(Align.center));
        goalMovement = new GoalMovement(this);
    }

    private void move() {
        goalMovement.updateGoalDirection();
        Vector2 movementSpeedVector = goalMovement.getMovementSpeedVector(speed);
        lastMove = movementSpeedVector;
        if ( !movementSpeedVector.isZero() ) {

            moveBy(movementSpeedVector.x, movementSpeedVector.y);

            //setRotation((float) (MathUtils.radiansToDegrees * Math.atan(movementSpeedVector.y / movementSpeedVector.x)));
            setRotation(MathUtils.radiansToDegrees * (float) Math.atan2(movementSpeedVector.y, movementSpeedVector.x));
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        move();
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public GoalMovement getGoalMovement() {
        return goalMovement;
    }

    public Vector2 getFacingDirection() {
        float rotation = getRotation();
        if ( Float.isNaN(rotation) ) rotation = 0;
        return new Vector2(1,0).rotate(rotation);

    }

    public Vector2 getLastMove() {
        return lastMove;
    }
}

package universe25.GameLogic.Movement.deprecated.SteerableImage;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

/**
 * Created by jorl17 on 06/08/15.
 */
public class SteerableImage extends Image implements Steerable<Vector2> {

    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());

    private Vector2 position;  // like scene2d centerX and centerY, but we need a vector to implement Steerable
    private Vector2 linearVelocity;
    private float angularVelocity;
    private float boundingRadius;
    private boolean tagged;

    private float maxLinearSpeed = 100;
    private float maxLinearAcceleration = 200;
    private float maxAngularSpeed = 5;
    private float maxAngularAcceleration = 10;

    private boolean independentFacing;

    private SteeringBehavior<Vector2> steeringBehavior;

    public SteerableImage(Texture texture, boolean independentFacing) {
        super(texture);
        this.independentFacing = independentFacing;
        this.position = new Vector2();
        this.linearVelocity = new Vector2();
        this.setBounds(0, 0, getWidth(), getHeight());
        this.boundingRadius = (getWidth() + getHeight()) / 4f;
        this.setOrigin(getWidth() * .5f, getHeight() * .5f);
    }

    public SteerableImage(Texture texture) {
        this(texture, false);
    }

    public SteerableImage(float x, float y, boolean independentFacing) {
        this.independentFacing = independentFacing;
        this.position = new Vector2();
        this.linearVelocity = new Vector2();
        this.setBounds(0, 0, getWidth(), getHeight());
        this.boundingRadius = (getWidth() + getHeight()) / 4f;
        this.setOrigin(getWidth() * .5f, getHeight() * .5f);
        setX(x);
        setY(y);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        position.set(getX(Align.center), getY(Align.center));
        //System.out.println("hehe");
    }

    public SteerableImage(float x, float y) {
        this(x,y,false);
    }

    @Override
    public Vector2 getPosition () {
        return position;
    }

    @Override
    public float getOrientation () {
        return getRotation() * MathUtils.degreesToRadians;
    }

    /*@Override
    public void setOrientation (float orientation) {
        setRotation(orientation * MathUtils.radiansToDegrees);
    }*/

    @Override
    public Vector2 getLinearVelocity () {
        return linearVelocity;
    }

    @Override
    public float getAngularVelocity () {
        return angularVelocity;
    }

    public void setAngularVelocity (float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    @Override
    public float getBoundingRadius () {
        return boundingRadius;
    }

    @Override
    public boolean isTagged () {
        return tagged;
    }

    @Override
    public void setTagged (boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public Vector2 newVector() {
        return new Vector2();
    }

    @Override
    public float vectorToAngle (Vector2 vector) {
        return Scene2dSteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector (Vector2 outVector, float angle) {
        return Scene2dSteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public float getMaxLinearSpeed () {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed (float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration () {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration (float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed () {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed (float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration () {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration (float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }


    public float getZeroLinearSpeedThreshold () {
        return 0.01f;
    }

    /*
    public void setZeroLinearSpeedThreshold (float value) {
        throw new UnsupportedOperationException();
    }*/

    public boolean isIndependentFacing () {
        return independentFacing;
    }

    public void setIndependentFacing (boolean independentFacing) {
        this.independentFacing = independentFacing;
    }

    public SteeringBehavior<Vector2> getSteeringBehavior () {
        return steeringBehavior;
    }

    public void setSteeringBehavior (SteeringBehavior<Vector2> steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }

    @Override
    public void act (float delta) {
        position.set(getX(Align.center), getY(Align.center));

        if (steeringBehavior != null) {

            // Calculate steering acceleration
            steeringBehavior.calculateSteering(steeringOutput);

			/*
			 * Here you might want to add a motor control layer filtering steering accelerations.
			 *
			 * For instance, a car in a driving game has physical constraints on its movement: it cannot turn while stationary; the
			 * faster it moves, the slower it can turn (without going into a skid); it can brake much more quickly than it can
			 * accelerate; and it only moves in the direction it is facing (ignoring power slides).
			 */

            // Apply steering acceleration
            applySteering(steeringOutput, delta);

            //wrapAround(position, getParent().getWidth(), getParent().getHeight());
            setPosition(position.x, position.y, Align.center);
        }
        super.act(delta);
    }

    // the display area is considered to wrap around from top to bottom
    // and from left to right
    protected static void wrapAround (Vector2 pos, float maxX, float maxY) {
        System.out.println("pos="+pos + ", " + "maxX=" + maxX + ", maxY=" + maxY);
        if (pos.x > maxX) pos.x = 0.0f;

        if (pos.x < 0) pos.x = maxX;

        if (pos.y < 0) pos.y = maxY;

        if (pos.y > maxY) pos.y = 0.0f;
    }

    private void applySteering (SteeringAcceleration<Vector2> steering, float time) {
        // Update position and linear velocity. Velocity is trimmed to maximum speed
        position.mulAdd(linearVelocity, time);
        linearVelocity.mulAdd(steering.linear, time).limit(getMaxLinearSpeed());

        // Update orientation and angular velocity
        if (independentFacing) {
            setRotation(getRotation() + (angularVelocity * time) * MathUtils.radiansToDegrees);
            angularVelocity += steering.angular * time;
        } else {
            // If we haven't got any velocity, then we can do nothing.
            if (!linearVelocity.isZero(getZeroLinearSpeedThreshold())) {
                float newOrientation = vectorToAngle(linearVelocity);
                angularVelocity = (newOrientation - getRotation() * MathUtils.degreesToRadians) * time; // this is superfluous if independentFacing is always true
                setRotation(newOrientation * MathUtils.radiansToDegrees);
            }
        }
    }



}

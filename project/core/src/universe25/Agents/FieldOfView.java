package universe25.Agents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import universe25.GameLogic.Movement.WeightedGoal;

import java.util.ArrayList;

/**
 * Created by jorl17 on 08/08/15.
 */
public class FieldOfView {
    private Agent agent;
    private float halfFov;
    private Vector2 left, right;
    private float seeDist;

    private ShapeRenderer shapeRenderer;

    public FieldOfView(Agent agent, float fov, float seeDist) {
        this.agent = agent;
        this.halfFov = fov/2;
        this.seeDist = seeDist;
        this.left = new Vector2();
        this.right = new Vector2();
        this.shapeRenderer = new ShapeRenderer();
    }

    public void update() {
        Vector2 facingDirection = this.agent.getFacingDirection();
        left.set(facingDirection.rotate(halfFov)).scl(seeDist);
        right.set(left.cpy().rotate(-2 * halfFov));
    }

    public Vector2[]  getFovTriangle() {
        Vector2[] pts = new Vector2[3];
        pts[0] = agent.getPosition();
        pts[1] = agent.getPosition().add(left);
        pts[2] = agent.getPosition().add(right);

        return pts;
    }

    public void draw(Batch batch) {
        Vector2[] tri = getFovTriangle();
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);

        shapeRenderer.line(tri[0].x, tri[0].y, tri[1].x, tri[1].y );
        shapeRenderer.line(tri[0].x, tri[0].y, tri[2].x, tri[2].y );
        shapeRenderer.end();
        batch.begin();
    }
}

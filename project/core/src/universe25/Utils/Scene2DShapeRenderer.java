package universe25.Utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by jorl17 on 14/08/15.
 */
public class Scene2DShapeRenderer {
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    private static boolean projMatrixSet = false;

    public static ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public static void updateProjectionMatrix(Batch batch) {
        if ( !projMatrixSet ) {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            projMatrixSet = true;
        }
    }


}

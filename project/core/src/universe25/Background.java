package universe25;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import javafx.geometry.BoundingBox;

/**
 * Created by jorl17 on 06/08/15.
 */
public class Background extends Group implements Disposable {
    private Texture bgCellTexture;
    private Table grid;
    private int nRows, nCols;
    private float squareSize;

    public Background(int nRows, int nCols, float squareSize) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.squareSize = squareSize;

        //System.out.println("" + nRows*squareSize+ "x" + nCols*squareSize);

        bgCellTexture = new Texture("grass.jpg");
        grid = new Table();
        //setWidth(nCols * squareSize);
        setPosition(0, nRows * squareSize);
        grid.align(Align.topLeft);
        //grid.setFillParent(true);
        for (int i = 0; i < nRows; i++){
            for (int j = 0; j < nCols; j++){
                Image actor = new Image(bgCellTexture);
                grid.add(actor).size(squareSize,squareSize);
            }
            grid.row();
        }

        addActor(grid);
    }

    @Override
    public void dispose() {
        bgCellTexture.dispose();
    }
}

package universe25.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;

/**
 * Created by jorl17 on 14/08/15.
 */
public class Crumbs extends  WorldObject {
    public Crumbs() {
        super(new Texture("crumbs.gif"));
        setSize(4,4);
        setOrigin(Align.center);
    }
}

package nz.co.canadia.horseplays.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public interface FontLoader {
    void loadBigFont(AssetManager manager, int uiHeight);

    BitmapFont getBigFont(AssetManager manager);

    void loadSmallFont(AssetManager manager, int uiHeight);

    BitmapFont getSmallFont(AssetManager manager);
}

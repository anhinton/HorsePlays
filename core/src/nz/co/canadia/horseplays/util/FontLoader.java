package nz.co.canadia.horseplays.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public interface FontLoader {
    void loadBigFont(AssetManager manager);

    BitmapFont getBigFont(AssetManager manager);

    void loadSmallFont(AssetManager manager);

    BitmapFont getSmallFont(AssetManager manager);
}

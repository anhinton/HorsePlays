package nz.co.canadia.horseplays.util;

import com.badlogic.gdx.assets.AssetManager;

public interface FontLoader {
    void loadBigFont(AssetManager manager);

    void loadSmallFont(AssetManager manager);
}

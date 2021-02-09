package nz.co.canadia.horseplays;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import nz.co.canadia.horseplays.util.FontLoader;

public class IOSFontLoader implements FontLoader {
    @Override
    public void loadBigFont(AssetManager manager, int uiHeight) {
        manager.load("fonts/Inconsolata64.fnt", BitmapFont.class);
    }

    @Override
    public BitmapFont getBigFont(AssetManager manager) {
        return manager.get("fonts/Inconsolata64.fnt", BitmapFont.class);
    }

    @Override
    public void loadSmallFont(AssetManager manager, int uiHeight) {
        manager.load("fonts/Podkova24.fnt", BitmapFont.class);
    }

    @Override
    public BitmapFont getSmallFont(AssetManager manager) {
        return manager.get("fonts/Podkova24.fnt", BitmapFont.class);
    }
}

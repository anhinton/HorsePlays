package nz.co.canadia.horseplays;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import nz.co.canadia.horseplays.util.FontLoader;

public class AndroidFontLoader implements FontLoader {
    @Override
    public void loadBigFont(AssetManager manager) {
        manager.load("fonts/Inconsolata64.fnt", BitmapFont.class);
    }

    @Override
    public void loadSmallFont(AssetManager manager) {
        manager.load("fonts/Podkova24.fnt", BitmapFont.class);
    }
}

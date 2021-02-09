package nz.co.canadia.horseplays;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

import nz.co.canadia.horseplays.util.Constants;
import nz.co.canadia.horseplays.util.FontLoader;

public class AndroidFontLoader implements FontLoader {
    @Override
    public void loadBigFont(AssetManager manager) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter bigFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        bigFont.fontFileName = "fonts/Inconsolata-VariableFont_wdth,wght.ttf";
        bigFont.fontParameters.size = Constants.BIG_FONT_SIZE;
        manager.load("fonts/Inconsolata-VariableFont_wdth,wght.ttf", BitmapFont.class, bigFont);
    }

    @Override
    public BitmapFont getBigFont(AssetManager manager) {
        return manager.get("fonts/Inconsolata-VariableFont_wdth,wght.ttf", BitmapFont.class);
    }

    @Override
    public void loadSmallFont(AssetManager manager) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter smallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        smallFont.fontFileName = "fonts/Podkova-VariableFont_wght.ttf";
        smallFont.fontParameters.size = Constants.SMALL_FONT_SIZE;
        manager.load("fonts/Podkova-VariableFont_wght.ttf", BitmapFont.class, smallFont);
    }

    @Override
    public BitmapFont getSmallFont(AssetManager manager) {
        return manager.get("fonts/Podkova-VariableFont_wght.ttf", BitmapFont.class);
    }
}

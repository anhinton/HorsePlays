package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import nz.co.canadia.horseplays.util.Constants;

/**
 * The stage curtains
 */

public class Curtains {
    Texture texture;
    Sprite leftSprite;
    Sprite rightSprite;

    Curtains() {
        texture = new Texture(Gdx.files.internal("graphics/curtain.png"));
        leftSprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(),
                texture.getHeight());
        leftSprite = new Sprite(region);
        leftSprite.setPosition(0 - leftSprite.getWidth() * 7 / 8, 0);
        rightSprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        rightSprite = new Sprite(region);
        rightSprite.setPosition(Constants.APP_WIDTH - rightSprite.getWidth() / 8, 0);
    }

    void draw (SpriteBatch batch) {
        leftSprite.draw(batch);
        rightSprite.draw(batch);
    }

    void dispose () {
        texture.dispose();
    }
}

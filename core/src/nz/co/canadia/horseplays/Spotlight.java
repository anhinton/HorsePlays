package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Lights so we can see our horse actors
 */

public class Spotlight {
    private Texture texture;
    private Sprite sprite;

    Spotlight (float x, float y, boolean flip) {
        texture = new Texture(Gdx.files.internal("graphics/spotlight.png"));
        sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(),
                texture.getHeight());
        sprite = new Sprite(region);
        sprite.flip(flip, false);
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
    }

    void draw (SpriteBatch batch) {
        sprite.draw(batch);
    }

    void dispose () {
        texture.dispose();
    }
}

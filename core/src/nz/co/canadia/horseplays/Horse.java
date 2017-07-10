package nz.co.canadia.horseplays;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Why it's a course, definitely
 */

public class Horse {
    private Sprite sprite;

    Horse (Texture texture, float x, float y, boolean flip) {
        sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(),
                texture.getHeight());
        sprite = new Sprite(region);
        sprite.flip(flip, false);
        sprite.setPosition(x - sprite.getWidth() / 2, y);
    }

    void draw (SpriteBatch batch) {
        sprite.draw(batch);
    }

    void dispose () {
    }

}

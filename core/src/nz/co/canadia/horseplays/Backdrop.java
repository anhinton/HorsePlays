package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The backdrop on my stage
 */

class Backdrop {
    private Texture texture;
    private Sprite sprite;

    Backdrop(float x, float y) {
        texture = new Texture(Gdx.files.internal("graphics/backdrop.png"));
        sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(),
                texture.getHeight());
        sprite = new Sprite(region);
        sprite.setPosition(x - sprite.getWidth() / 2, y);
    }

    void draw (SpriteBatch batch) {
        sprite.draw(batch);
    }

    void dispose () {
        texture.dispose();
    }
}

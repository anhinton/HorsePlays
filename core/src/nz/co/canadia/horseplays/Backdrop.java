package nz.co.canadia.horseplays;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The backdrop on my stage
 */

class Backdrop {
    private Sprite sprite;

    Backdrop(float x, float y, Sprite sprite) {
        this.sprite = sprite;
        this.sprite.setPosition(x - this.sprite.getWidth() / 2, y);
    }

    void draw (SpriteBatch batch) {
        sprite.draw(batch);
    }
}

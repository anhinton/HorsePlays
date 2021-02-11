package nz.co.canadia.horseplays;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Lights so we can see our horse actors
 */

public class Spotlight {
    private Sprite sprite;

    Spotlight(float x, float y, boolean flip, Sprite sprite) {
        this.sprite = sprite;
        this.sprite.flip(flip, false);
        this.sprite.setPosition(x - this.sprite.getWidth() / 2, y - this.sprite.getHeight() / 2);
    }

    void draw (SpriteBatch batch) {
        sprite.draw(batch);
    }
}

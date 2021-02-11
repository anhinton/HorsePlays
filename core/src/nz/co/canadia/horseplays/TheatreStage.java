package nz.co.canadia.horseplays;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A stage on which our horse actors shalle perform
 */

class TheatreStage {
    private Sprite sprite;

    TheatreStage(float x, float y, Sprite sprite) {
        this.sprite = sprite;
        this.sprite.setPosition(x, y);
    }

    void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    float getHeight() {
        return(sprite.getHeight());
    }

}

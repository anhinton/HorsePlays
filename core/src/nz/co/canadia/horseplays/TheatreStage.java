package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import nz.co.canadia.horseplays.util.Constants;

/**
 * A stage on which our horse actors shalle perform
 */

class TheatreStage {
    private Texture texture;
    private Sprite sprite;

    TheatreStage(float x, float y) {
        texture = new Texture(Gdx.files.internal("graphics/stage.png"));
        sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(),
                texture.getHeight());
        sprite = new Sprite(region);
        sprite.setPosition(x, y);
    }

    void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    void dispose () {
        texture.dispose();
    }

    float getHeight() {
        return(sprite.getHeight());
    }

}

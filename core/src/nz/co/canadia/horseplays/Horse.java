package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import nz.co.canadia.horseplays.util.Constants;

/**
 * Why it's a course, definitely
 */

public class Horse {
    private Sprite sprite;

    private Constants.Side side;
    private boolean entering;

    private float target;
    private float changeX;

    Horse (Texture texture, float y, boolean flip, Constants.Side side) {
        sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(),
                texture.getHeight());
        sprite = new Sprite(region);
        sprite.flip(flip, false);

        switch (side) {
            case LEFT:
                sprite.setPosition(0 - sprite.getWidth(), y);
                target = Constants.APP_WIDTH / 4;
                break;
            case RIGHT:
                sprite.setPosition(Constants.APP_WIDTH, y);
                target = Constants.APP_WIDTH * 3 / 4;
                break;
        }

        this.side = side;
        entering = false;
        changeX = 0;
    }

    void draw (SpriteBatch batch) {
        sprite.draw(batch);
    }

    void dispose () {
    }

    void update() {
        if (entering) {
            if (Math.abs(sprite.getX() + sprite.getWidth() / 2 - target) < 5) {
                entering = false;
                changeX = 0;
            } else {
                switch (side) {
                    case LEFT:
                        changeX = Constants.CURTAIN_SPEED;
                        break;
                    case RIGHT:
                        changeX = -Constants.CURTAIN_SPEED;
                        break;
                }
                sprite.setX(sprite.getX() + changeX * Gdx.graphics.getDeltaTime());
            }
        }
    }

    void enter() {
        entering = true;
    }

}

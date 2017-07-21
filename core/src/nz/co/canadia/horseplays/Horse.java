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

class Horse {
    private Sprite sprite;

    private Constants.Side side;
    private boolean moving;

    private float targetX;
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
                sprite.setPosition(-sprite.getWidth(), y);
                break;
            case RIGHT:
                sprite.setPosition(Constants.APP_WIDTH, y);
                break;
        }

        this.side = side;
        targetX = 0;
        moving = false;
        changeX = 0;
    }

    void draw (SpriteBatch batch) {
        sprite.draw(batch);
    }

    void dispose () {
    }

    void update() {
        if (moving) {
            if (Math.abs(sprite.getX() - targetX) < 5) {
                moving = false;
                sprite.setX(targetX);
            } else {
                sprite.setX(sprite.getX() + changeX);
            }
        }
    }

    void enter() {
        moving = true;
        switch (side) {
            case LEFT:
                changeX = Constants.HORSE_SPEED * Gdx.graphics.getDeltaTime();
                targetX = Constants.HORSE_MARK - sprite.getWidth() / 2;
                break;
            case RIGHT:
                changeX = -Constants.HORSE_SPEED * Gdx.graphics.getDeltaTime();
                targetX = Constants.APP_WIDTH - Constants.HORSE_MARK - sprite.getWidth() / 2;
                break;
        }
    }

    void exit() {
        moving = true;
        switch (side) {
            case LEFT:
                changeX = -Constants.HORSE_SPEED * Gdx.graphics.getDeltaTime();
                targetX = Constants.APP_WIDTH - sprite.getWidth() / 2;
                break;
            case RIGHT:
                changeX = Constants.HORSE_SPEED * Gdx.graphics.getDeltaTime();
                targetX = Constants.APP_WIDTH;
                break;
        }
    }

}

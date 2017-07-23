package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import nz.co.canadia.horseplays.util.Constants;

/**
 * Why it's a course, definitely
 */

class Horse {
    private Sprite sprite;
    private Sprite spriteClose;

    private Constants.Side side;
    private boolean moving;

    private float targetX;
    private float changeX;
    private float distanceMoved;
    private float totalChange;

    private SpeechUI speechUI;

    Horse (Texture texture, Texture textureClose, float y, boolean flip, Constants.Side side,
           SpeechUI speechUI) {
        this.speechUI = speechUI;
        // wide shot sprite
//        sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(),
                texture.getHeight());
        sprite = new Sprite(region);
        sprite.flip(flip, false);

        // close-up sprite
//        spriteClose = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion regionClose = new TextureRegion(textureClose, 0, 0, textureClose.getWidth(),
                textureClose.getHeight());
        spriteClose = new Sprite(regionClose);
        spriteClose.flip(flip, false);
        switch (side) {
            case LEFT:
                sprite.setPosition(-sprite.getWidth(), y);
                spriteClose.setPosition(0, 0);
                break;
            case RIGHT:
                sprite.setPosition(Constants.APP_WIDTH, y);
                spriteClose.setPosition(Constants.APP_WIDTH - spriteClose.getWidth(), 0);
                break;
        }

        this.side = side;
        targetX = 0;
        moving = false;
        changeX = 0;
        distanceMoved = 0;
        totalChange = 0;
    }

    boolean isMoving() {
        return moving;
    }

    void draw (SpriteBatch batch, Constants.ZoomLevel currentZoomLevel) {
        switch (currentZoomLevel) {
            case WIDE:
                sprite.draw(batch);
                break;
            case CLOSE:
                spriteClose.draw(batch);
                break;
        }
    }

    void dispose () {
    }

    void update() {
        if (moving) {
            if (distanceMoved >= totalChange) {
                moving = false;
                sprite.setX(targetX);
            } else {
                distanceMoved += Math.abs(changeX);
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
        distanceMoved = 0;
        totalChange = Math.abs(sprite.getX() - targetX);
    }

    void exit() {
        moving = true;
        switch (side) {
            case LEFT:
                changeX = -Constants.HORSE_SPEED * Gdx.graphics.getDeltaTime();
                targetX = -sprite.getWidth();
                break;
            case RIGHT:
                changeX = Constants.HORSE_SPEED * Gdx.graphics.getDeltaTime();
                targetX = Constants.APP_WIDTH;
                break;
        }
        distanceMoved = 0;
        totalChange = Math.abs(sprite.getX() - targetX);
    }

    void speak (boolean speak) {
        speechUI.speak("help me, Ronda");
    }

}

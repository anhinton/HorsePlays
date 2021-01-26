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
    private final Sprite sprite;
    private final Sprite spriteClose;

    private final Constants.HorseSide horseSide;
    private boolean moving;
    private boolean animating;

    private float performingX;
    private float targetX;
    private float direction;
    private float distanceMoved;
    private float totalChange;
    private float changeX;

    Horse (Texture texture, Texture textureClose, float y, boolean flip, Constants.HorseSide horseSide) {
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
        switch (horseSide) {
            case LEFT:
                sprite.setPosition(-sprite.getWidth(), y);
                spriteClose.setPosition(0, 0);
                break;
            case RIGHT:
                sprite.setPosition(Constants.APP_WIDTH, y);
                spriteClose.setPosition(Constants.APP_WIDTH - spriteClose.getWidth(), 0);
                break;
        }

        this.horseSide = horseSide;
        switch (horseSide) {
            case LEFT:
                performingX = Constants.HORSE_MARK - sprite.getWidth() / 2;
                break;
            case RIGHT:
                performingX = Constants.APP_WIDTH - Constants.HORSE_MARK - sprite.getWidth() / 2;
                break;
        }
        targetX = 0;
        moving = false;
        animating = false;
        direction = 0;
        changeX = 0;
        distanceMoved = 0;
        totalChange = 0;
    }

    boolean getAnimating() {
        return animating;
    }

    void startAnimating() {
        this.animating = true;
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

    void update() {
        if (moving) {
            if (distanceMoved >= totalChange) {
                animating = false;
                moving = false;
                sprite.setX(targetX);
            } else {
                changeX = direction * Constants.HORSE_SPEED * Gdx.graphics.getDeltaTime();
                distanceMoved += Math.abs(changeX);
                sprite.setX(sprite.getX() + changeX);
            }
        }
    }

    void enter() {
        animating = true;
        moving = true;
        targetX = performingX;
        switch (horseSide) {
            case LEFT:
                direction = 1;
                break;
            case RIGHT:
                direction = -1;
                break;
        }
        distanceMoved = 0;
        totalChange = Math.abs(sprite.getX() - targetX);
    }

    void exit() {
        animating = true;
        moving = true;
        switch (horseSide) {
            case LEFT:
                direction = -1;
                targetX = -sprite.getWidth();
                break;
            case RIGHT:
                direction = 1;
                targetX = Constants.APP_WIDTH;
                break;
        }
        distanceMoved = 0;
        totalChange = Math.abs(sprite.getX() - targetX);
    }

    public void setPerforming() {
        sprite.setX(performingX);
    }
}

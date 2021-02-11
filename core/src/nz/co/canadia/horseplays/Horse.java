package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nz.co.canadia.horseplays.util.Constants;

/**
 * Why it's a course, definitely
 */

class Horse {
    private final Sprite sprite;
    private final Sprite closeSprite;

    private final Constants.HorseSide horseSide;
    private boolean moving;
    private boolean animating;

    private float performingX;
    private float targetX;
    private float direction;
    private float distanceMoved;
    private float totalChange;
    private float changeX;

    Horse (Sprite sprite, Sprite closeSprite, float y, boolean flip, Constants.HorseSide horseSide) {
        // wide shot sprite
        this.sprite = sprite;
        this.sprite.flip(flip, false);

        // close-up sprite
        this.closeSprite = closeSprite;
        this.closeSprite.flip(flip, false);

        switch (horseSide) {
            case LEFT:
                this.sprite.setPosition(-this.sprite.getWidth(), y);
                this.closeSprite.setPosition(0, 0);
                break;
            case RIGHT:
                this.sprite.setPosition(Constants.APP_WIDTH, y);
                this.closeSprite.setPosition(Constants.APP_WIDTH - this.closeSprite.getWidth(), 0);
                break;
        }

        this.horseSide = horseSide;
        switch (horseSide) {
            case LEFT:
                performingX = Constants.HORSE_MARK - this.sprite.getWidth() / 2;
                break;
            case RIGHT:
                performingX = Constants.APP_WIDTH - Constants.HORSE_MARK - this.sprite.getWidth() / 2;
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
                closeSprite.draw(batch);
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

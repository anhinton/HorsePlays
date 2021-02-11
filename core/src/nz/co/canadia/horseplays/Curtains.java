package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import nz.co.canadia.horseplays.util.Constants;

/**
 * The stage curtains
 */

class Curtains {
    private final Sprite leftSprite;
    private final Sprite rightSprite;

    private float leftChangeX;
    private float rightChangeX;
    private boolean leftMoving;
    private final float leftOpenX;
    private float leftTargetX;
    private float leftDirection;
    private float leftDistanceMoved;
    private float leftTotalDistance;
    private boolean rightMoving;
    private final float rightOpenX;
    private float rightTargetX;
    private float rightDirection;
    private float rightDistanceMoved;
    private float rightTotalDistance;
    private boolean animating;

    Curtains(TextureRegion region) {
        leftSprite = new Sprite(region);
        leftSprite.setPosition(0, 0);
        rightSprite = new Sprite(region);
        rightSprite.setPosition(Constants.APP_WIDTH / 2f, 0);

        leftOpenX = Constants.OPEN_CURTAIN_WIDTH - leftSprite.getWidth();
        rightOpenX = Constants.APP_WIDTH - Constants.OPEN_CURTAIN_WIDTH;

        leftChangeX = 0;
        rightChangeX = 0;
        leftDirection = 0;
        leftMoving = false;
        leftTargetX = Constants.APP_WIDTH / 2f;
        rightDirection = 0;
        rightMoving = false;
        rightTargetX = Constants.APP_WIDTH / 2f;
        rightChangeX = 0;
        animating = false;
    }

    boolean getAnimating() {
        return animating;
    }
    
    void startAnimating() {
        this.animating = true;
    }

    void update() {
        if (leftMoving) {
            // open left curtain
            if (leftDistanceMoved >= leftTotalDistance) {
                leftMoving = false;
                leftSprite.setX(leftTargetX);
            } else {
                leftChangeX = leftDirection * Constants.CURTAIN_SPEED * Gdx.graphics.getDeltaTime();
                leftDistanceMoved += Math.abs(leftChangeX);
                leftSprite.setX(leftSprite.getX() + leftChangeX);
            }
        }
        if (rightMoving) {
            // open right curtain
            if (rightDistanceMoved >= rightTotalDistance) {
                rightMoving = false;
                rightSprite.setX(rightTargetX);
            } else {
                rightChangeX = rightDirection * Constants.CURTAIN_SPEED * Gdx.graphics.getDeltaTime();
                rightDistanceMoved += Math.abs(rightChangeX);
                rightSprite.setX(rightSprite.getX() + rightChangeX);
            }

            if (!leftMoving & !rightMoving) {
                animating = false;
            }
        }
    }

    void draw (SpriteBatch batch) {
        leftSprite.draw(batch);
        rightSprite.draw(batch);
    }

    void open () {
        animating = true;
        leftMoving = true;
        leftTargetX = leftOpenX;
        leftDirection = -1;
        leftDistanceMoved = 0;
        leftTotalDistance = Math.abs(leftSprite.getX() - leftTargetX);
        rightMoving = true;
        rightTargetX = rightOpenX;
        rightDirection = 1;
        rightDistanceMoved = 0;
        rightTotalDistance = Math.abs(rightSprite.getX() - rightTargetX);
    }

    void close () {
        animating = true;
        leftMoving = true;
        leftTargetX = 0;
        leftDirection = 1;
        leftDistanceMoved = 0;
        leftTotalDistance = Math.abs(leftSprite.getX() - leftTargetX);
        rightMoving = true;
        rightTargetX = Constants.APP_WIDTH / 2f;
        rightDirection = -1;
        rightDistanceMoved = 0;
        rightTotalDistance = Math.abs(rightSprite.getX() - rightTargetX);
    }

    public void setOpen() {
        leftSprite.setX(leftOpenX);
        rightSprite.setX(rightOpenX);
    }
}

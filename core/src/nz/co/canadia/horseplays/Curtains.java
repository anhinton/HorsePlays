package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import nz.co.canadia.horseplays.util.Constants;

/**
 * The stage curtains
 */

class Curtains {
    private Texture texture;
    private Sprite leftSprite;
    private Sprite rightSprite;

    private boolean leftMoving;
    private float leftTargetX;
    private float leftChangeX;
    private float leftDistanceMoved;
    private float leftTotalDistance;
    private boolean rightMoving;
    private float rightTargetX;
    private float rightChangeX;
    private float rightDistanceMoved;
    private float rightTotalDistance;

    Curtains() {
        texture = new Texture(Gdx.files.internal("graphics/curtain.png"));
//        leftSprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(),
                texture.getHeight());
        leftSprite = new Sprite(region);
        leftSprite.setPosition(0, 0);
        rightSprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        rightSprite = new Sprite(region);
        rightSprite.setPosition(Constants.APP_WIDTH / 2, 0);

        leftMoving = false;
        leftTargetX = Constants.APP_WIDTH / 2;
        leftChangeX = 0;
        rightMoving = false;
        rightTargetX = Constants.APP_WIDTH / 2;
        rightChangeX = 0;
    }

    boolean isMoving () {
        return leftMoving | rightMoving;
    }

    void update() {
        if (leftMoving) {
            // open left curtain
            if (leftDistanceMoved >= leftTotalDistance) {
                leftMoving = false;
                leftSprite.setX(leftTargetX);
            } else {
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
                rightDistanceMoved += Math.abs(rightChangeX);
                rightSprite.setX(rightSprite.getX() + rightChangeX);
            }
        }
    }

    void draw (SpriteBatch batch) {
        leftSprite.draw(batch);
        rightSprite.draw(batch);
    }

    void dispose () {
        texture.dispose();
    }

    void open () {
        leftMoving = true;
        leftTargetX = Constants.OPEN_CURTAIN_WIDTH - leftSprite.getWidth();
        leftChangeX = -Constants.CURTAIN_SPEED * Gdx.graphics.getDeltaTime();
        leftDistanceMoved = 0;
        leftTotalDistance = Math.abs(leftSprite.getX() - leftTargetX);
        rightMoving = true;
        rightTargetX = Constants.APP_WIDTH - Constants.OPEN_CURTAIN_WIDTH;
        rightChangeX = Constants.CURTAIN_SPEED * Gdx.graphics.getDeltaTime();
        rightDistanceMoved = 0;
        rightTotalDistance = Math.abs(rightSprite.getX() - rightTargetX);
    }

    void close () {
        leftMoving = true;
        leftTargetX = 0;
        leftChangeX = Constants.CURTAIN_SPEED * Gdx.graphics.getDeltaTime();
        leftDistanceMoved = 0;
        leftTotalDistance = Math.abs(leftSprite.getX() - leftTargetX);
        rightMoving = true;
        rightTargetX = Constants.APP_WIDTH / 2;
        rightChangeX = -Constants.CURTAIN_SPEED * Gdx.graphics.getDeltaTime();
        rightDistanceMoved = 0;
        rightTotalDistance = Math.abs(rightSprite.getX() - rightTargetX);
    }
}

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
    private boolean rightMoving;
    private float leftTargetX;
    private float leftChangeX;
    private float rightTargetX;
    private float rightChangeX;

    Curtains() {
        texture = new Texture(Gdx.files.internal("graphics/curtain.png"));
        leftSprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(),
                texture.getHeight());
        leftSprite = new Sprite(region);
        leftSprite.setPosition(0, 0);
        rightSprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        rightSprite = new Sprite(region);
        rightSprite.setPosition(Constants.APP_WIDTH / 2, 0);

        leftMoving = false;
        rightMoving = false;
        leftTargetX = Constants.APP_WIDTH / 2;
        rightTargetX = Constants.APP_WIDTH / 2;
        leftChangeX = 0;
        rightChangeX = 0;
    }

    void update() {
        if (leftMoving) {
            // open left curtain
            if (Math.abs(leftSprite.getX() - leftTargetX) < 2) {
                leftMoving = false;
                leftSprite.setX(leftTargetX);
            } else {
                leftSprite.setX(leftSprite.getX() + leftChangeX);
            }
        }
        if (rightMoving) {
            // open right curtain
            if (Math.abs(rightSprite.getX() - rightTargetX) < 2) {
                rightMoving = false;
                rightSprite.setX(rightSprite.getX() + rightChangeX);
            } else {
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
        rightMoving = true;
        rightTargetX = Constants.APP_WIDTH - Constants.OPEN_CURTAIN_WIDTH;
        rightChangeX = Constants.CURTAIN_SPEED * Gdx.graphics.getDeltaTime();
    }

    void close () {

    }
}

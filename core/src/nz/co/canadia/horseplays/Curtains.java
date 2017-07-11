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

    boolean open;
    boolean opening;
    boolean closed;
    boolean closing;

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

        open = false;
        opening = false;
        closed = true;
        closing = false;
    }

    void update() {
        if (opening & !open) {
            // open left curtain
            if (Math.abs(leftSprite.getX() + leftSprite.getWidth() * 7 / 8) < 2) {
                opening = false;
                open = true;
            } else {
                leftSprite.setX(leftSprite.getX() - Constants.CURTAIN_SPEED * Gdx.graphics.getDeltaTime());
            }
            // open right curtain
            if (Math.abs(rightSprite.getX() + rightSprite.getWidth() / 8) < 2) {
                opening = false;
            } else {
                rightSprite.setX(rightSprite.getX() + Constants.CURTAIN_SPEED * Gdx.graphics.getDeltaTime());
            }
        }
        if (closing & !closed) {
            // close left curtain
            if (Math.abs(leftSprite.getX() + leftSprite.getWidth()) < 2) {
                closing = false;
                closed = true;
            } else {
                leftSprite.setX(leftSprite.getX() + Constants.CURTAIN_SPEED * Gdx.graphics.getDeltaTime());
            }
            // close right curtain
            if (Math.abs(rightSprite.getX() + rightSprite.getWidth()) < 2) {
                closing = false;
            } else {
                rightSprite.setX(rightSprite.getX() - Constants.CURTAIN_SPEED * Gdx.graphics.getDeltaTime());
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
        closing = false;
        closed = false;
        opening = true;
    }

    void close () {
        open = false;
        opening = false;
        closing = true;
    }
}

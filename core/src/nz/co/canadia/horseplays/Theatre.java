package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

import nz.co.canadia.horseplays.util.Constants;

/**
 * The theatre in which we stage our play
 */

class Theatre {

    private Backdrop backdrop;
    private Spotlight spotlight01;
    private Spotlight spotlight02;
    private TheatreStage stage;
    private Texture horseTexture01;
    private Horse horse01;
    private Texture horseTexture02;
    private Horse horse02;
    private Curtains curtains;

    private boolean showActive;

    Theatre() {
        stage = new TheatreStage(0, 0);
        backdrop = new Backdrop(Constants.APP_WIDTH / 2, stage.getHeight());
        spotlight01 = new Spotlight(
                Constants.APP_WIDTH / 11,
                Constants.APP_HEIGHT * 8 / 9,
                false);
        spotlight02 = new Spotlight(
                Constants.APP_WIDTH * 10 / 11,
                Constants.APP_HEIGHT * 8 / 9,
                true);
        horseTexture01 = new Texture(Gdx.files.internal("graphics/horse01.png"));
        horse01 = new Horse(horseTexture01, stage.getHeight(), true,
                Constants.Side.LEFT);
        horseTexture02 = new Texture(Gdx.files.internal("graphics/horse02.png"));
        horse02 = new Horse(horseTexture02, stage.getHeight(), true,
                Constants.Side.RIGHT);
        curtains = new Curtains();

        showActive = false;
    }

    void update() {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (!showActive) {
                startShow();
            }
            if (showActive) {
               // endShow();
            }

        }

        curtains.update();
        horse01.update();
        horse02.update();
    }

    void draw (SpriteBatch batch) {
        backdrop.draw(batch);
        horse01.draw(batch);
        horse02.draw(batch);
        stage.draw(batch);
        spotlight01.draw(batch);
        spotlight02.draw(batch);
        curtains.draw(batch);
    }

    void dispose() {
        backdrop.dispose();
        curtains.dispose();
        horseTexture01.dispose();
        horseTexture02.dispose();
        spotlight01.dispose();
        spotlight02.dispose();
        stage.dispose();
    }

    void startShow() {
        showActive = true;
        curtains.open();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                horse01.enter();
            }
        }, 2);
        horse02.enter();
    }

    void endShow() {
        showActive = false;
        curtains.close();
    }
}

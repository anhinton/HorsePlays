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
    private Texture horseCloseTexture01;
    private Horse horse01;
    private Texture horseTexture02;
    private Texture horseCloseTexture02;
    private Horse horse02;
    private Curtains curtains;
    private Horse currentHorse;

    private Constants.TheatreScene currentTheatreScene;
    private Constants.ZoomLevel currentZoomLevel;
    private boolean animating;

    private int clickCounter;

    Theatre() {
        stage = new TheatreStage(0, 0);
        backdrop = new Backdrop(Constants.APP_WIDTH / 2, stage.getHeight());
        spotlight01 = new Spotlight(
                Constants.SPOTLIGHT_OFFSET_X,
                Constants.SPOTLIGHT_OFFSET_Y,
                false);
        spotlight02 = new Spotlight(
                Constants.APP_WIDTH - Constants.SPOTLIGHT_OFFSET_X,
                Constants.SPOTLIGHT_OFFSET_Y,
                true);
        horseTexture01 = new Texture(Gdx.files.internal("graphics/horse01.png"));
        horseCloseTexture01 = new Texture(Gdx.files.internal("graphics/horseFace01.png"));
        horse01 = new Horse(horseTexture01, horseCloseTexture01, stage.getHeight(), true,
                Constants.Side.LEFT);
        horseTexture02 = new Texture(Gdx.files.internal("graphics/horse02.png"));
        horseCloseTexture02 = new Texture(Gdx.files.internal("graphics/horseFace02.png"));
        horse02 = new Horse(horseTexture02, horseCloseTexture02, stage.getHeight(), true,
                Constants.Side.RIGHT);
        curtains = new Curtains();

        currentTheatreScene = Constants.TheatreScene.START;
        animating = false;
        currentZoomLevel = Constants.ZoomLevel.WIDE;
        currentHorse = horse01;
        clickCounter = 0;
    }

    void update() {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            switch (currentTheatreScene) {
                case START:
                    startShow();
                    break;
                case OPENING:
                    break;
                case PERFORMING:
                    if (clickCounter <= 3) {
                        clickCounter++;
                        switch (currentZoomLevel) {
                            case WIDE:
                                currentZoomLevel = Constants.ZoomLevel.CLOSE;
                                break;
                            case CLOSE:
                                currentZoomLevel = Constants.ZoomLevel.WIDE;
                                break;
                        }
                    } else {
                        endShow();
                    }
                    break;
                case CLOSING:
                    break;
                case FINISHED:
                    break;
            }
        }

        // check animation state
        animating = curtains.isMoving() | horse01.isMoving() | horse02.isMoving();

        // advance to next theatre scene
        switch (currentTheatreScene) {
            case OPENING:
                if (!animating) {
                    currentTheatreScene = Constants.TheatreScene.PERFORMING;
                }
                break;
            case CLOSING:
                if (!animating) {
                    currentTheatreScene = Constants.TheatreScene.START;
                }
                break;
        }

        // update objects
        curtains.update();
        horse01.update();
        horse02.update();
    }

    void draw (SpriteBatch batch) {
        switch (currentZoomLevel) {
            case WIDE:
                backdrop.draw(batch);
                horse01.draw(batch, currentZoomLevel);
                horse02.draw(batch, currentZoomLevel);
                stage.draw(batch);
                spotlight01.draw(batch);
                spotlight02.draw(batch);
                curtains.draw(batch);
                break;
            case CLOSE:
                currentHorse.draw(batch, currentZoomLevel);
        }
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

    private void startShow() {
        curtains.open();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                horse01.enter();
                horse02.enter();
            }
        }, 2);
        currentTheatreScene = Constants.TheatreScene.OPENING;
    }

    private void endShow() {
        horse01.exit();
        horse02.exit();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                curtains.close();
            }
        }, 2);
        currentTheatreScene = Constants.TheatreScene.CLOSING;
    }
}

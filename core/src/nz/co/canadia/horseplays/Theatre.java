package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import nz.co.canadia.horseplays.util.Constants;

/**
 * The theatre in which we theatreStage our play
 */

public class Theatre {
    private Table table;
    private SpeechUI speechUI;

    private Backdrop backdrop;
    private Spotlight spotlight01;
    private Spotlight spotlight02;
    private TheatreStage theatreStage;
    private Texture horseTexture01;
    private Texture horseCloseTexture01;
    private Texture horseTexture02;
    private Texture horseCloseTexture02;
    private Array<Horse> horses;
    private Curtains curtains;
    private Horse currentHorse;

    private Constants.TheatreScene currentTheatreScene;
    private Constants.ZoomLevel currentZoomLevel;
    private boolean animating;

    private int clickCounter;
    private boolean prevTouchDown;
    private boolean touchDown;

    public Theatre(Table table) {
        this.table = table;
        speechUI = new SpeechUI(table);
        theatreStage = new TheatreStage(0, 0);
        backdrop = new Backdrop(Constants.APP_WIDTH / 2, theatreStage.getHeight());
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
        horses = new Array<Horse>();
        horses.add(new Horse(horseTexture01, horseCloseTexture01, theatreStage.getHeight(), true,
                Constants.Side.LEFT, speechUI));
        horseTexture02 = new Texture(Gdx.files.internal("graphics/horse02.png"));
        horseCloseTexture02 = new Texture(Gdx.files.internal("graphics/horseFace02.png"));
        horses.add(new Horse(horseTexture02, horseCloseTexture02, theatreStage.getHeight(), true,
                Constants.Side.RIGHT, speechUI));
        curtains = new Curtains();

        currentTheatreScene = Constants.TheatreScene.START;
        animating = false;
        currentZoomLevel = Constants.ZoomLevel.WIDE;
        currentHorse = horses.get(0);
        clickCounter = 0;
        prevTouchDown = false;
        touchDown = false;
    }

    public void update() {
        // handle input
        if (touchDown & !prevTouchDown) {
            prevTouchDown = true;
        }
        if (!touchDown & prevTouchDown) {
            prevTouchDown = false;
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
                                if (clickCounter == 1) {
                                    currentHorse = horses.get(1);
                                } else {
                                    currentHorse = horses.get(0);
                                }
                                break;
                            case CLOSE:
                                currentZoomLevel = Constants.ZoomLevel.WIDE;
                                break;
                        }
                    } else {
                        clickCounter = 0;
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
        animating = horsesMoving() | curtains.isMoving();

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
        for (Horse horse : horses) {
            horse.update();
        }
    }

    public void draw(SpriteBatch batch) {
        switch (currentZoomLevel) {
            case WIDE:
                //currentHorse.speak(false);
                backdrop.draw(batch);
                for (Horse horse : horses) {
                    horse.draw(batch, currentZoomLevel);
                }
                theatreStage.draw(batch);
                spotlight01.draw(batch);
                spotlight02.draw(batch);
                curtains.draw(batch);
                break;
            case CLOSE:
                //currentHorse.speak(true);
                currentHorse.draw(batch, currentZoomLevel);
        }
    }

    public void dispose() {
        backdrop.dispose();
        curtains.dispose();
        horseTexture01.dispose();
        horseTexture02.dispose();
        horseCloseTexture01.dispose();
        horseCloseTexture02.dispose();
        spotlight01.dispose();
        spotlight02.dispose();
        theatreStage.dispose();
    }

    boolean isTouchDown() {
        return touchDown;
    }

    public void setTouchDown(boolean touchDown) {
        this.touchDown = touchDown;
    }

    private void startShow() {
        curtains.open();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (Horse horse : horses) {
                    horse.enter();
                }
            }
        }, 2);
        currentTheatreScene = Constants.TheatreScene.OPENING;
    }

    private void endShow() {
        for (Horse horse : horses) {
            horse.exit();
        }
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                curtains.close();
            }
        }, 2);
        currentTheatreScene = Constants.TheatreScene.CLOSING;
    }

    private boolean horsesMoving() {
        for (Horse horse : horses) {
            if (horse.isMoving()) {
                return true;
            }
        }
        return false;
    }

    Array<Horse> getHorses() {
        return horses;
    }
}

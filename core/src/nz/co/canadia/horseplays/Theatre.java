package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import nz.co.canadia.horseplays.util.Constants;

/**
 * The theatre in which we theatreStage our play
 */

public class Theatre {

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

    private Constants.CurrentScene currentScene;
    private Constants.ZoomLevel currentZoomLevel;
    private boolean animating;

    private int clickCounter;
    private boolean prevTouchDown;
    private boolean touchDown;

    public Theatre() {
        theatreStage = new TheatreStage(0, 0);
        backdrop = new Backdrop(Constants.APP_WIDTH / 2f, theatreStage.getHeight());
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
                Constants.HorseSide.LEFT));
        horseTexture02 = new Texture(Gdx.files.internal("graphics/horse02.png"));
        horseCloseTexture02 = new Texture(Gdx.files.internal("graphics/horseFace02.png"));
        horses.add(new Horse(horseTexture02, horseCloseTexture02, theatreStage.getHeight(), true,
                Constants.HorseSide.RIGHT));
        curtains = new Curtains();

        currentScene = Constants.CurrentScene.START;
        animating = false;
        currentZoomLevel = Constants.ZoomLevel.WIDE;
        currentHorse = horses.get(0);
        clickCounter = 0;
        prevTouchDown = false;
        touchDown = false;
    }

    public void update() {

        // check animation state
        animating = horsesMoving() | curtains.isMoving();

        // advance to next theatre scene
        switch (currentScene) {
            case OPENING:
                if (!animating) {
                    currentScene = Constants.CurrentScene.PERFORMING;
                }
                break;
            case CLOSING:
                if (!animating) {
                    currentScene = Constants.CurrentScene.FINISHED;
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

    public void startShow() {
        curtains.open();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (Horse horse : horses) {
                    horse.enter();
                }
            }
        }, 2);
        currentScene = Constants.CurrentScene.OPENING;
    }

    public void endShow(boolean bomb) {
        if (bomb) {
            horses.get(1).exit();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    horses.get(0).exit();
                }
            }, 4);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    curtains.close();
                }
            }, 8);
        } else {
            for (Horse horse : horses) {
                horse.exit();
            }
            curtains.close();
        }
        currentScene = Constants.CurrentScene.CLOSING;
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

    public Constants.CurrentScene getCurrentScene() {
        return currentScene;
    }

    void setCurrentHorse(String actor, Array<String> actors) {
        if (actor.equals(actors.get(0))) {
            currentHorse = horses.get(0);
        } else if (actor.equals(actors.get(1))) {
            currentHorse = horses.get(1);
        }
    }

    public void setCurrentZoomLevel(Constants.ZoomLevel currentZoomLevel) {
        this.currentZoomLevel = currentZoomLevel;
    }
}

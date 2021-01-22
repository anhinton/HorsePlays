package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import nz.co.canadia.horseplays.screens.TheatreScreen;
import nz.co.canadia.horseplays.script.PlayScript;
import nz.co.canadia.horseplays.script.ScriptLine;
import nz.co.canadia.horseplays.util.Constants;

/**
 * The theatre in which we theatreStage our play
 */

public class Theatre {

    private final PlayScript playScript;
    private final SpeechUI speechUI;
    private final TheatreScreen theatreScreen;
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

    public Theatre(TheatreScreen theatreScreen) {

        this.theatreScreen = theatreScreen;
        playScript = new PlayScript();
        speechUI = new SpeechUI(this, playScript);

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

    public void advance() {
        if (!animating) {
            switch (getCurrentScene()) {
                case START:
                    startShow();
                    break;
//                case OPENING:
//                    break;
                case PERFORMING:
                    speak();
                    break;
//                case CLOSING:
//                    break;
                case FINISHED:
                    theatreScreen.exit();
                    break;
            }
        }
    }

    public void speak() {
        setCurrentZoomLevel(Constants.ZoomLevel.CLOSE);

        // check if bombThreshold has been exceeded
        if (playScript.hasBombed()) {
            playScript.setCurrentKnot("bomb");
        }

        if (playScript.hasLine()) {
            speechUI.speak(playScript.getCurrentLine());
        } else if (playScript.hasChoice()) {
            speechUI.speak(playScript.getCurrentChoices());
        } else if (playScript.hasKnot()) {
            playScript.nextKnot();
            speak();
        } else {
            speechUI.end();
            endShow();
        }
    }

    public SpeechUI getSpeechUI() {
        return speechUI;
    }

    public void update() {

        // check animation state
        animating = horsesMoving() | curtains.isMoving();

        // advance to next theatre scene
        switch (currentScene) {
            case OPENING:
                if (!animating) {
                    setCurrentScene(Constants.CurrentScene.PERFORMING);
                }
                break;
            case CLOSING:
                if (!animating) {
                    setCurrentScene(Constants.CurrentScene.FINISHED);
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
        setCurrentScene(Constants.CurrentScene.OPENING);
    }

    public void close(boolean hasBombed) {
    }

    public void endShow() {
        setCurrentZoomLevel(Constants.ZoomLevel.WIDE);
        setCurrentScene(Constants.CurrentScene.CLOSING);

        if (playScript.hasBombed()) {
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

    public void setCurrentScene(Constants.CurrentScene currentScene) {
        this.currentScene =  currentScene;
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

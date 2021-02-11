package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import nz.co.canadia.horseplays.screens.TheatreScreen;
import nz.co.canadia.horseplays.script.PlayScript;
import nz.co.canadia.horseplays.script.ScriptChoice;
import nz.co.canadia.horseplays.util.Constants;
import nz.co.canadia.horseplays.util.FontLoader;

/**
 * The theatre in which we theatreStage our play
 */

public class Theatre {

    private final PlayScript playScript;
    private final SpeechUI speechUI;
    private final TheatreScreen theatreScreen;
    private final Backdrop backdrop;
    private final Spotlight spotlight01;
    private final Spotlight spotlight02;
    private final TheatreStage theatreStage;
    private final Texture horseTexture01;
    private final Texture horseCloseTexture01;
    private final Texture horseTexture02;
    private final Texture horseCloseTexture02;
    private final Array<Horse> horses;
    private final Curtains curtains;
    private final Preferences autosave;
    public FontLoader fontLoader;
    public AssetManager manager;
    private Horse currentHorse;

    private Constants.CurrentScene currentScene;
    private Constants.ZoomLevel currentZoomLevel;
    private boolean animating;
    private int bombCount;

    public Theatre(TheatreScreen theatreScreen, int uiWidth, int uiHeight,
                   FileHandle playScriptXml, boolean load) {

        this.theatreScreen = theatreScreen;
        manager = theatreScreen.manager;
        fontLoader = theatreScreen.fontLoader;
        playScript = new PlayScript(playScriptXml);
        speechUI = new SpeechUI(this, uiWidth, uiHeight);
        speechUI.showTitle(playScript.getTitle());

        autosave = Gdx.app.getPreferences(Constants.AUTOSAVE_PATH);

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

        animating = false;

        if (load) {
            currentScene = Constants.CurrentScene.PERFORMING;
            playScript.setCurrentKnot(autosave.getString("currentKnot"));
            bombCount = autosave.getInteger("bombCount");
            curtains.setOpen();
            for(Horse horse : horses) {
                horse.setPerforming();
            }
            speak();
        } else {
            currentScene = Constants.CurrentScene.START;
            currentZoomLevel = Constants.ZoomLevel.WIDE;
            bombCount = 0;
        }
    }

    public void addBomb(int bomb) {
        if (bomb > 0) {
            bombCount += bomb;
            theatreScreen.setShaking(bomb);
        }
    }

    public void advance() {
        if (!animating) {
            switch (currentScene) {
                case START:
                    startShow();
                    break;
                case OPENING:
                    break;
                case PERFORMING:
                    if (!speechUI.buttonAdvanceOnly) {
                        playScript.nextLine();
                    }
                    speak();
                    break;
                case CLOSING:
                    break;
                case FINISHED:
                    theatreScreen.exit();
                    break;
            }
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
        speechUI.dispose();
    }

    public Array<String> getCharacters() {
        return playScript.getCharacters();
    }

    public SpeechUI getSpeechUI() {
        return speechUI;
    }

    private boolean hasBombed() {
        if (playScript.bombThreshold >= 0) {
            return bombCount >= playScript.bombThreshold;
        } else {
            return false;
        }
    }

    private boolean horsesAnimating() {
        for (Horse horse : horses) {
            if (horse.getAnimating()) {
                return true;
            }
        }
        return false;
    }

    public void speak() {
        setCurrentZoomLevel(Constants.ZoomLevel.CLOSE);

        // check if bombThreshold has been exceeded
        if (hasBombed()) {
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

    public void startShow() {
        speechUI.clearChildren();
        for (Horse horse : horses) {
            horse.startAnimating();
        }
        curtains.startAnimating();
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

    public void endShow() {
        setCurrentZoomLevel(Constants.ZoomLevel.WIDE);
        setCurrentScene(Constants.CurrentScene.CLOSING);

        clearAutosave();

        curtains.startAnimating();

        if (hasBombed()) {
            horses.get(1).startAnimating();
            horses.get(1).exit();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    curtains.close();
                }
            }, 4);
        } else {
            for (Horse horse : horses) {
                horse.startAnimating();
                horse.exit();
            }
            curtains.close();
        }
    }

    public void selectChoice(int digit) {

        if (speechUI.hasChoices & speechUI.buttonAdvanceOnly) {
            Array<ScriptChoice> currentChoices = playScript.getCurrentChoices();
            if (digit <= currentChoices.size) {
                ScriptChoice choice = currentChoices.get(digit - 1);
                if (choice.getDivert().equals(Constants.END_KNOT)) {
                    speechUI.end();
                } else {
                    addBomb(choice.getBomb());
                    setCurrentKnot(choice.getDivert());
                    advance();
                }
            }
        }
    }

    void setCurrentHorse(String actor, Array<String> actors) {
        if (actor.equals(actors.get(0))) {
            currentHorse = horses.get(0);
        } else if (actor.equals(actors.get(1))) {
            currentHorse = horses.get(1);
        }
    }

    public void setCurrentKnot(String knot) {
        playScript.setCurrentKnot(knot);
    }

    public void setCurrentScene(Constants.CurrentScene currentScene) {
        this.currentScene =  currentScene;
    }

    public void setCurrentZoomLevel(Constants.ZoomLevel currentZoomLevel) {
        this.currentZoomLevel = currentZoomLevel;
    }

    public void clearAutosave() {
        autosave.clear();
        autosave.flush();
    }

    public void saveProgress() {
        autosave.putString("currentPlayXml", playScript.getPlayScriptXml().toString());
        autosave.putString("currentKnot", playScript.getCurrentKnotId());
        autosave.putInteger("bombCount", bombCount);
        autosave.flush();
    }

    public void update() {

        // check animation state
        animating = horsesAnimating() | curtains.getAnimating();

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
}

package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import nz.co.canadia.horseplays.screens.TheatreScreen;
import nz.co.canadia.horseplays.script.PlayScript;
import nz.co.canadia.horseplays.script.ScriptChoice;
import nz.co.canadia.horseplays.script.ScriptLine;
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
    private final Array<Horse> horses;
    private final Curtains curtains;
    private final Preferences autosave;
    private final Sound coughSound;
    private final Sound oohSound;
    public TextureAtlas atlas;
    public FontLoader fontLoader;
    public AssetManager manager;
    private Horse currentHorse;

    private Constants.CurrentScene currentScene;
    private Constants.ZoomLevel currentZoomLevel;
    private boolean animating;
    private int bombCount;
    private final Array<String> log;

    public Theatre(TheatreScreen theatreScreen, int uiWidth, int uiHeight,
                   FileHandle playScriptXml, boolean load) {

        this.theatreScreen = theatreScreen;
        atlas = theatreScreen.atlas;
        manager = theatreScreen.manager;
        fontLoader = theatreScreen.fontLoader;
        playScript = new PlayScript(playScriptXml);
        speechUI = new SpeechUI(this, uiWidth, uiHeight);
        speechUI.showTitle(playScript.getTitle());

        autosave = Gdx.app.getPreferences(Constants.AUTOSAVE_PATH);

        coughSound = theatreScreen.manager.get("audio/cough.mp3", Sound.class);
        oohSound = theatreScreen.manager.get("audio/ooh.mp3", Sound.class);

        theatreStage = new TheatreStage(0, 0, atlas.createSprite("graphics/stage"));
        backdrop = new Backdrop(Constants.APP_WIDTH / 2f, theatreStage.getHeight(),
                atlas.createSprite("graphics/backdrop"));
        spotlight01 = new Spotlight(
                Constants.SPOTLIGHT_OFFSET_X,
                Constants.SPOTLIGHT_OFFSET_Y,
                false,
                atlas.createSprite("graphics/spotlight"));
        spotlight02 = new Spotlight(
                Constants.APP_WIDTH - Constants.SPOTLIGHT_OFFSET_X,
                Constants.SPOTLIGHT_OFFSET_Y,
                true,
                atlas.createSprite("graphics/spotlight"));
        Sprite horseSprite01 = atlas.createSprite("graphics/horse01");
        Sprite closeHorseSprite01 = atlas.createSprite("graphics/horseFace01");
        horses = new Array<>();
        horses.add(new Horse(horseSprite01, closeHorseSprite01, theatreStage.getHeight(), true,
                Constants.HorseSide.LEFT));
        Sprite horseSprite02 = atlas.createSprite("graphics/horse02");
        Sprite closeHorseSprite02 = atlas.createSprite("graphics/horseFace02");
        horses.add(new Horse(horseSprite02, closeHorseSprite02, theatreStage.getHeight(), true,
                Constants.HorseSide.RIGHT));
        curtains = new Curtains(atlas.findRegion("graphics/curtain"));

        animating = false;
        log = new Array<>();

        if (load) {
            currentScene = Constants.CurrentScene.PERFORMING;
            playScript.setCurrentKnot(autosave.getString("currentKnot"));
            bombCount = autosave.getInteger("bombCount");
            loadLog();
            curtains.setOpen();
            for(Horse horse : horses) {
                horse.setPerforming();
            }
            speak();
        } else {
            currentScene = Constants.CurrentScene.START;
            currentZoomLevel = Constants.ZoomLevel.WIDE;
            bombCount = 0;
            if (playScript.hasLine()) {
                appendLog(playScript.getCurrentLine());
            }
        }
    }

    public void addBomb(int bomb) {
        if (bomb > 0) {
            switch(bomb) {
                case 1:
                    coughSound.play(theatreScreen.getSoundVolume());
                    break;
                case 2:
                    oohSound.play(theatreScreen.getSoundVolume());
                    break;
            }
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
                case CLOSING:
                    break;
                case PERFORMING:
                    if (!speechUI.buttonAdvanceOnly) {
                        playScript.nextLine();
                    }
                    if (playScript.hasLine()) {
                        appendLog(playScript.getCurrentLine());
                    }
                    speak();
                    break;
                case FINISHED:
                    theatreScreen.exit();
                    break;
            }
        }
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
            Gdx.app.log("Theatre.speak", "PlayScript has Knot!");
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
                    speechUI.showOutcome(true);
                    curtains.close();
                }
            }, 4);
        } else {
            speechUI.showOutcome(false);
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

    public void appendLog(String character, String text) {
        log.add(character + ": " + text);
    }

    public void appendLog(ScriptChoice choice) {
        appendLog(choice.getCharacter(), choice.getText());
    }

    public void appendLog(ScriptLine line) {
        appendLog(line.getCharacter(), line.getText());
    }

    public void loadLog() {
        String logString = autosave.getString("log", "");
        String[] split = logString.split("\n");
        for (String s: split) {
            log.add(s);
        }
    }

    public void saveLog() {
        StringBuilder logString = new StringBuilder();
        logString.append(log.removeIndex(0));
        for (String s: log) {
            logString.append("\n");
            logString.append(s);
        }
        autosave.putString("log", logString.toString());
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

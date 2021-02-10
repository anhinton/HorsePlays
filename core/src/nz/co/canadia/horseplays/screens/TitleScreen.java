package nz.co.canadia.horseplays.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import nz.co.canadia.horseplays.HorsePlays;
import nz.co.canadia.horseplays.util.Constants;

/**
 * Title screen to show on launch
 */

public class TitleScreen implements InputProcessor, Screen {
    private final HorsePlays game;
    private final Preferences autosave;
    private final Preferences settings;
    private final Stage stage;
    private final Table table;
    private final BitmapFont smallFont;
    private final BitmapFont bigFont;
    private final Texture redBubbleTexture;
    private final Texture greyBubbleTexture;
    private final Label.LabelStyle smallLabelStyle;
    private final Label.LabelStyle bigLabelStyle;
    private final Label musicVolumeValueLabel;
    private final Label soundVolumeValueLabel;
    private final Skin skin;
    private final OrthographicCamera camera;
    private final int buttonPad;
    private final int menuButtonWidth;
    private final int speechButtonWidth;
    private final int volumeButtonWidth;
    private Constants.CurrentTitleMenu currentTitleMenu;

    public TitleScreen (final HorsePlays game) {
        this.game = game;

        buttonPad = MathUtils.round((float) Constants.BUTTON_PAD / Constants.APP_HEIGHT * game.getUiHeight());
        menuButtonWidth = MathUtils.round((float) Constants.MENU_BUTTON_WIDTH / Constants.APP_WIDTH * game.getUiWidth());
        speechButtonWidth = MathUtils.round((float) Constants.SPEECH_BUTTON_WIDTH / Constants.APP_WIDTH * game.getUiWidth());
        volumeButtonWidth = MathUtils.round((float) Constants.VOLUME_BUTTON_WIDTH / Constants.APP_WIDTH * game.getUiWidth());

        autosave = Gdx.app.getPreferences(Constants.AUTOSAVE_PATH);
        settings = Gdx.app.getPreferences(Constants.SETTINGS_PATH);
        game.setMusicVolume(settings.getFloat("musicVolume", Constants.MUSIC_VOLUME_DEFAULT));
        game.setSoundVolume(settings.getFloat("soundVolume", Constants.SOUND_VOLUME_DEFAULT));

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        redBubbleTexture = game.manager.get("ui/redBubble.png");
        greyBubbleTexture = game.manager.get("ui/greyBubble.png");
        smallFont = game.fontLoader.getSmallFont(game.manager);
        bigFont = game.fontLoader.getBigFont(game.manager);

        bigLabelStyle = new Label.LabelStyle(bigFont, Constants.FONT_COLOR);
        smallLabelStyle = new Label.LabelStyle(smallFont, Constants.FONT_COLOR);
        musicVolumeValueLabel = new Label(printVolume(game.getMusicVolume()), smallLabelStyle);
        soundVolumeValueLabel = new Label(printVolume(game.getSoundVolume()), smallLabelStyle);

        camera = new OrthographicCamera();
        FitViewport uiViewport = new FitViewport(game.getUiWidth(), game.getUiHeight(), camera);
        stage = new Stage(uiViewport);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        showMainMenu();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private TextButton menuButton(String text) {
        NinePatchDrawable downNinePatch = new NinePatchDrawable(
                new NinePatch(
                        redBubbleTexture,
                        20, 20, 20, 20
                )
        );
        NinePatchDrawable upNinePatch = new NinePatchDrawable(
                new NinePatch(
                        greyBubbleTexture,
                        20, 20, 20, 20
                )
        );
        return new TextButton(
                text,
                new TextButton.TextButtonStyle(
                        upNinePatch, downNinePatch,
                        upNinePatch, smallFont
                )
        );
    }

    private void showMainMenu() {
        currentTitleMenu = Constants.CurrentTitleMenu.MAIN;
        table.clearChildren();
        table.center();

        // The big title so everyone knows what cool game this is
        Label anhintonLabel = new Label("Ashley Noel Hinton's", smallLabelStyle);
        table.add(anhintonLabel);
        table.row();
        Label titleLabel = new Label("HORSE PLAYS", bigLabelStyle);
        table.add(titleLabel);
        table.row();

        if (autosave.contains("currentPlayXml")) {
            TextButton continueButton = menuButton("CONTINUE");
            continueButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    startPlay(Gdx.files.internal(autosave.getString("currentPlayXml")), true);
                }
            });
            table.add(continueButton).space(buttonPad).width(menuButtonWidth);
            table.row();
        }

        TextButton startButton = menuButton("NEW");
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showNewMenu();
            }
        });
        table.add(startButton).space(buttonPad).width(menuButtonWidth);
        table.row();

        TextButton settingsButton = menuButton("SETTINGS");
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showSettingsMenu();
            }
        });
        table.add(settingsButton).space(buttonPad).width(menuButtonWidth);
        table.row();
        
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            TextButton quitButton = menuButton("QUIT");
            quitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    goBack();
                }
            });
            table.add(quitButton).space(buttonPad).width(menuButtonWidth);
            table.row();            
        }
    }

    private void showNewMenu() {
        currentTitleMenu = Constants.CurrentTitleMenu.NEW;
        table.clearChildren();
        table.center();

        Label.LabelStyle topLabelStyle = new Label.LabelStyle(smallFont, Constants.FONT_COLOR);
        Label topLabel = new Label("Select a play:", topLabelStyle);
        table.add(topLabel).space(buttonPad);
        table.row();

        // The FBI's Most Unwanted
        TextButton fbiButton = menuButton("The FBI's Most Unwanted");
        fbiButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPlay(Gdx.files.internal("playscripts/fbimostunwanted.xml"), false);
            }
        });
        table.add(fbiButton).space(buttonPad).width(speechButtonWidth);
        table.row();

        // A Whole Big Sucking Thing
        TextButton suckingButton = menuButton("A Whole Big Sucking Thing");
        suckingButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPlay(Gdx.files.internal("playscripts/awholebigsuckingthing.xml"), false);
            }
        });
        table.add(suckingButton).space(buttonPad).width(speechButtonWidth);
        table.row();

        // Back button
        TextButton backButton = menuButton("BACK");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goBack();
            }
        });
        table.add(backButton).space(buttonPad).width(speechButtonWidth);
        table.row();
    }

    public void showSettingsMenu() {
        currentTitleMenu = Constants.CurrentTitleMenu.SETTINGS;
        table.clearChildren();
        table.center().pad(buttonPad);

        Label.LabelStyle settingsLabelStyle = new Label.LabelStyle(bigFont, Constants.FONT_COLOR);
        Label settingsLabel = new Label("Settings", settingsLabelStyle);
        table.add(settingsLabel).space(buttonPad).left().colspan(4);
        table.row();
        
        // Music Volume
        // Label
        Label musicVolumeLabel = new Label("Music Volume: ", smallLabelStyle);
        table.add(musicVolumeLabel).space(buttonPad).left();
        // Down button
        TextButton musicVolumeDownButton = menuButton("DOWN");
        musicVolumeDownButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicVolumeDown();
            }
        });
        table.add(musicVolumeDownButton).left().space(buttonPad).width(volumeButtonWidth);
        // Up button
        TextButton musicVolumeUpButton = menuButton("UP");
        musicVolumeUpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicVolumeUp();
            }
        });
        table.add(musicVolumeUpButton).left().space(buttonPad).width(volumeButtonWidth);
        //Value
        table.add(musicVolumeValueLabel).space(buttonPad).left();
        table.row();

        // Sound Volume
        // Label
        Label soundVolumeLabel = new Label("Sound Volume: ", smallLabelStyle);
        table.add(soundVolumeLabel).space(buttonPad).left();
        // Down button
        TextButton soundVolumeDownButton = menuButton("DOWN");
        soundVolumeDownButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundVolumeDown();
            }
        });
        table.add(soundVolumeDownButton).left().space(buttonPad).width(volumeButtonWidth);
        // Up button
        TextButton soundVolumeUpButton = menuButton("UP");
        soundVolumeUpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundVolumeUp();
            }
        });
        table.add(soundVolumeUpButton).left().space(buttonPad).width(volumeButtonWidth);
        //Value
        table.add(soundVolumeValueLabel).space(buttonPad).left();
        table.row();

        // Credits button
        TextButton creditsButton = menuButton("CREDITS");
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showCreditsMenu();
            }
        });
        table.add(creditsButton).space(buttonPad).width(menuButtonWidth).left().colspan(4);
        table.row();

        // Back button
        TextButton backButton = menuButton("BACK");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.flush();
                goBack();
            }
        });
        table.add(backButton).space(buttonPad).width(menuButtonWidth).left().colspan(4);
        table.row();

    }

    private void showCreditsMenu() {
        currentTitleMenu = Constants.CurrentTitleMenu.CREDITS;
        table.clearChildren();
        table.top().left().pad(buttonPad);

        Label.LabelStyle settingsLabelStyle = new Label.LabelStyle(bigFont, Constants.FONT_COLOR);
        Label settingsLabel = new Label("Credits", settingsLabelStyle);
        table.add(settingsLabel).space(buttonPad).left();

        // Back button
        TextButton backButton = menuButton("BACK");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goBack();
            }
        });
        table.add(backButton).space(buttonPad).width(volumeButtonWidth).right();
        table.row();

        // credits ScrollPane
        FileHandle file = Gdx.files.internal("credits.txt");
        String creditsText = file.readString("UTF-8");
        Label creditsLabel = new Label(creditsText, smallLabelStyle);
        creditsLabel.setWrap(true);
        ScrollPane creditsPane = new ScrollPane(creditsLabel, skin, "default");
        creditsPane.setFadeScrollBars(false);
        table.add(creditsPane).space(buttonPad).prefWidth(game.getUiWidth()).colspan(2);
        table.row();
    }

    private void goBack() {
        switch (currentTitleMenu) {
            case MAIN:
                quit();
                break;
            case NEW:
            case SETTINGS:
                showMainMenu();
                break;
            case CREDITS:
                showSettingsMenu();
                break;
        }
    }

    private String printVolume(float volume) {
        return String.valueOf(MathUtils.round(volume * 10));
    }

    private void musicVolumeUp() {
        setMusicVolume(game.getMusicVolume() + 0.1f);
    }

    private void musicVolumeDown() {
        setMusicVolume(game.getMusicVolume() - 0.1f);
    }

    private void setMusicVolume(float musicVolume) {
        game.setMusicVolume(musicVolume);
        musicVolumeValueLabel.setText(printVolume(game.getMusicVolume()));
        settings.putFloat("musicVolume", game.getMusicVolume());
    }

    private void soundVolumeUp() {
        setSoundVolume(game.getSoundVolume() + 0.1f);
    }

    private void soundVolumeDown() {
        setSoundVolume(game.getSoundVolume() - 0.1f);
    }

    private void setSoundVolume(float soundVolume) {
        game.setSoundVolume(soundVolume);
        soundVolumeValueLabel.setText(printVolume(game.getSoundVolume()));
        settings.putFloat("soundVolume", game.getSoundVolume());
    }

    private void startPlay(FileHandle playScriptXml, boolean load) {
        game.setScreen(new TheatreScreen(game, playScriptXml, load));
        dispose();
    }

    private void quit() {
        Gdx.app.exit();
        dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
//        camera.translate(0, 0, 0);
        camera.update();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.BACK:
            case Input.Keys.ESCAPE:
                goBack();
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

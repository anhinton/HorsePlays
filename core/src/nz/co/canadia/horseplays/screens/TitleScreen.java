package nz.co.canadia.horseplays.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import nz.co.canadia.horseplays.HorsePlays;
import nz.co.canadia.horseplays.util.Constants;

/**
 * Title screen to show on launch
 */

public class TitleScreen implements InputProcessor, Screen {
    private final HorsePlays game;
    private final Preferences autosave;
    private final Stage stage;
    private final Table table;
    private final BitmapFont smallFont;
    private final BitmapFont bigFont;
    private final Texture speechBubble02Texture;
    private final Texture choiceBubble01Texture;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    public TitleScreen (final HorsePlays game) {
        this.game = game;

        autosave = Gdx.app.getPreferences(Constants.AUTOSAVE_PATH);

        speechBubble02Texture = new Texture(Gdx.files.internal("ui/speechBubble02.png"));
        choiceBubble01Texture = new Texture(Gdx.files.internal("ui/choiceBubble01.png"));
        smallFont = new BitmapFont(Gdx.files.internal("fonts/TlwgMonoBold24.fnt"));
        bigFont = new BitmapFont(Gdx.files.internal("fonts/TlwgMonoBold64.fnt"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT,
                camera);

        stage = new Stage(viewport);
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
                        speechBubble02Texture,
                        20, 20, 20, 20
                )
        );
        NinePatchDrawable upNinePatch = new NinePatchDrawable(
                new NinePatch(
                        choiceBubble01Texture,
                        20, 20, 20, 20
                )
        );
        TextButton menuButton = new TextButton(
                text,
                new TextButton.TextButtonStyle(
                        upNinePatch, downNinePatch,
                        upNinePatch, smallFont
                )
        );
        menuButton.getLabel().setWrap(true);
        return menuButton;
    }

    private void showMainMenu() {
        table.clearChildren();

        Label.LabelStyle titleLabelStyle = new Label.LabelStyle(bigFont, Constants.FONT_COLOR);
        Label titleLabel = new Label("HORSE PLAYS", titleLabelStyle);
        table.add(titleLabel).space(Constants.BUTTON_PAD);
        table.row();

        if (autosave.contains("currentPlayXml")) {
            TextButton continueButton = menuButton("CONTINUE");
            continueButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    startPlay(Gdx.files.internal(autosave.getString("currentPlayXml")), true);
                }
            });
            table.add(continueButton).space(Constants.BUTTON_PAD).width(Constants.MENU_BUTTON_WIDTH);
            table.row();
        }

        TextButton startButton = menuButton("NEW");
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showPlayMenu();
            }
        });
        table.add(startButton).space(Constants.BUTTON_PAD).width(Constants.MENU_BUTTON_WIDTH);
        table.row();

        TextButton settingsButton = menuButton("SETTINGS");
        table.add(settingsButton).space(Constants.BUTTON_PAD).width(Constants.MENU_BUTTON_WIDTH);
        table.row();
        
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            TextButton quitButton = menuButton("QUIT");
            quitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    quit();
                }
            });
            table.add(quitButton).space(Constants.BUTTON_PAD).width(Constants.MENU_BUTTON_WIDTH);
            table.row();            
        }
    }

    private void showPlayMenu() {
        table.clearChildren();

        Label.LabelStyle topLabelStyle = new Label.LabelStyle(smallFont, Constants.FONT_COLOR);
        Label topLabel = new Label("Select a play:", topLabelStyle);
        table.add(topLabel).space(Constants.BUTTON_PAD);
        table.row();

        // The FBI's Most Unwanted
        TextButton fbiButton = menuButton("The FBI's Most Unwanted");
        fbiButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPlay(Gdx.files.internal("playscripts/fbimostunwanted.xml"), false);
            }
        });
        table.add(fbiButton).space(Constants.BUTTON_PAD).width(Constants.SPEECH_BUTTON_WIDTH);
        table.row();

        // A Whole Big Sucking Thing
        TextButton suckingButton = menuButton("A Whole Big Sucking Thing");
        suckingButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPlay(Gdx.files.internal("playscripts/awholebigsuckingthing.xml"), false);
            }
        });
        table.add(suckingButton).space(Constants.BUTTON_PAD).width(Constants.SPEECH_BUTTON_WIDTH);
        table.row();

        // Back button
        TextButton backButton = menuButton("BACK");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showMainMenu();
            }
        });
        table.add(backButton).space(Constants.BUTTON_PAD).width(Constants.SPEECH_BUTTON_WIDTH);
        table.row();
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

        camera.update();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        speechBubble02Texture.dispose();
        choiceBubble01Texture.dispose();
        smallFont.dispose();
        bigFont.dispose();
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.BACK:
            case Input.Keys.ESCAPE:
                quit();
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

package nz.co.canadia.horseplays.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import nz.co.canadia.horseplays.HorsePlays;
import nz.co.canadia.horseplays.Theatre;
import nz.co.canadia.horseplays.util.Constants;

/**
 * The stage screen where the game is played.
 */

public class TheatreScreen implements InputProcessor, Screen {
    private final HorsePlays game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;

    private final Theatre theatre;
    private final Table menuUi;
    private final NinePatchDrawable redNinePatch;
    private final NinePatchDrawable greyNinePatch;
    private final BitmapFont smallFont;
    public AssetManager manager;
    private Constants.CurrentGameMenu currentGameMenu;

    public TheatreScreen(final HorsePlays game,
                         FileHandle playScriptXml, boolean load) {
        manager = game.manager;
        this.game = game;

        Texture redBubbleTexture = manager.get("ui/redBubble.png");
        redNinePatch = new NinePatchDrawable(
                new NinePatch(redBubbleTexture, 20, 20, 20, 20)
        );
        Texture greyBubbleTexture = manager.get("ui/greyBubble.png");
        greyNinePatch = new NinePatchDrawable(
                new NinePatch(greyBubbleTexture, 20, 20, 20, 20)
        );
        smallFont = manager.get("fonts/Podkova24.fnt");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT, camera);
        stage = new Stage(viewport);

        menuUi = new Table();
        menuUi.setFillParent(true);

        theatre = new Theatre(this, playScriptXml, load);
        stage.addActor(theatre.getSpeechUI());
        stage.addActor(menuUi);

        showGame();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void showGame() {
        theatre.getSpeechUI().setVisible(true);
        currentGameMenu = Constants.CurrentGameMenu.GAME;
        menuUi.clearChildren();
        menuUi.top().left().pad(Constants.BUTTON_PAD);

        Texture menuTexture = manager.get("ui/menu-icon.png");
        TextureRegionDrawable menuRegionDrawable = new TextureRegionDrawable(menuTexture);
        ImageButton.ImageButtonStyle menuButtonStyle = new ImageButton.ImageButtonStyle(greyNinePatch, redNinePatch,
                greyNinePatch, menuRegionDrawable, menuRegionDrawable, menuRegionDrawable);
        ImageButton menuButton = new ImageButton(menuButtonStyle);
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showMenu();
            }
        });
        menuUi.add(menuButton);
    }

    private void showMenu() {
        currentGameMenu = Constants.CurrentGameMenu.MENU;
        menuUi.clearChildren();
        menuUi.center();
        theatre.getSpeechUI().setVisible(false);

        Label.LabelStyle topLabelStyle = new Label.LabelStyle(smallFont, Constants.FONT_COLOR);
        Label topLabel = new Label("Menu:", topLabelStyle);
        menuUi.add(topLabel).space(Constants.BUTTON_PAD);
        menuUi.row();

        // Resume button
        TextButton resumeButton = new TextButton(
                "RESUME",
                new TextButton.TextButtonStyle(
                        greyNinePatch, redNinePatch,
                        greyNinePatch, smallFont));
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showGame();
            }
        });
        menuUi.add(resumeButton).space(Constants.BUTTON_PAD).width(Constants.MENU_BUTTON_WIDTH);
        menuUi.row();

        // Quit button
        TextButton quitButton = new TextButton(
                "QUIT",
                new TextButton.TextButtonStyle(
                        greyNinePatch, redNinePatch,
                        greyNinePatch, smallFont));
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                theatre.saveProgress();
                exit();
            }
        });
        menuUi.add(quitButton).space(Constants.BUTTON_PAD).width(Constants.MENU_BUTTON_WIDTH);
        menuUi.row();
    }

    private void goBack() {
        switch(currentGameMenu) {
            case GAME:
                showMenu();
                break;
            case MENU:
                showGame();
                break;
        }
    }

    public void exit() {
        game.setScreen(new TitleScreen(game));
        dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        theatre.update();

        game.batch.begin();
        theatre.draw(game.batch);
        game.batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        theatre.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (currentGameMenu == Constants.CurrentGameMenu.MENU) {
            switch (keycode) {
                case Input.Keys.BACK:
                case Input.Keys.ESCAPE:
                case Input.Keys.ENTER:
                case Input.Keys.SPACE:
                case Input.Keys.R:
                    goBack();
                    break;
                case Input.Keys.Q:
                    theatre.saveProgress();
                    exit();
                    break;
            }
        } else if (currentGameMenu == Constants.CurrentGameMenu.GAME) {
            switch (keycode) {
                case Input.Keys.BACK:
                case Input.Keys.ESCAPE:
                case Input.Keys.Q:
                    goBack();
                    break;
                case Input.Keys.ENTER:
                case Input.Keys.SPACE:
                    theatre.advance();
                    break;
                case Input.Keys.NUM_1:
                case Input.Keys.NUMPAD_1:
                    theatre.selectChoice(1);
                    break;
                case Input.Keys.NUM_2:
                case Input.Keys.NUMPAD_2:
                    theatre.selectChoice(2);
                    break;
                case Input.Keys.NUM_3:
                case Input.Keys.NUMPAD_3:
                    theatre.selectChoice(3);
                    break;
                case Input.Keys.NUM_4:
                case Input.Keys.NUMPAD_4:
                    theatre.selectChoice(4);
                    break;
                case Input.Keys.NUM_5:
                case Input.Keys.NUMPAD_5:
                    theatre.selectChoice(5);
                    break;
                case Input.Keys.NUM_6:
                case Input.Keys.NUMPAD_6:
                    theatre.selectChoice(6);
                    break;
                case Input.Keys.NUM_7:
                case Input.Keys.NUMPAD_7:
                    theatre.selectChoice(7);
                    break;
                case Input.Keys.NUM_8:
                case Input.Keys.NUMPAD_8:
                    theatre.selectChoice(8);
                    break;
                case Input.Keys.NUM_9:
                case Input.Keys.NUMPAD_9:
                    theatre.selectChoice(9);
                    break;
                case Input.Keys.NUM_0:
                case Input.Keys.NUMPAD_0:
                    theatre.selectChoice(10);
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (currentGameMenu == Constants.CurrentGameMenu.GAME) {
            theatre.advance();
        }
        return true;
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

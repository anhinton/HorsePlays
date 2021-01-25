package nz.co.canadia.horseplays.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    public TheatreScreen(final HorsePlays game,
                         FileHandle playScriptXml, boolean load) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT, camera);
        stage = new Stage(viewport);

        theatre = new Theatre(this, playScriptXml, load);
//        table.setDebug(true);
        stage.addActor(theatre.getSpeechUI());

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
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
        switch (keycode) {
            case Input.Keys.BACK:
            case Input.Keys.ESCAPE:
                exit();
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
        theatre.advance();
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

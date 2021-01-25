package nz.co.canadia.horseplays.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private BitmapFont font;
    private OrthographicCamera camera;
    private Viewport viewport;

    public TitleScreen (final HorsePlays game) {
        this.game = game;

        autosave = Gdx.app.getPreferences(Constants.AUTOSAVE_PATH);

        font = new BitmapFont(Gdx.files.internal("fonts/TlwgMonoBold64.fnt"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT,
                camera);

        Gdx.input.setInputProcessor(this);
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
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        font.setColor(Constants.FONT_COLOR);
        font.draw(game.batch, "HORSE PLAYS\n1. Continue\n2. New", 100, Constants.APP_HEIGHT - Constants.BUTTON_PAD);

        game.batch.end();
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

    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.BACK:
            case Input.Keys.ESCAPE:
                quit();
                break;
            case Input.Keys.NUM_1:
            case Input.Keys.NUMPAD_1:
                // Continue
                if (autosave.contains("currentPlayXml")) {
                    startPlay(Gdx.files.internal("playscripts/playscript.xml"), true);
                }
                break;
            case Input.Keys.NUM_2:
            case Input.Keys.NUMPAD_2:
                // New
                startPlay(Gdx.files.internal("playscripts/playscript.xml"), false);
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

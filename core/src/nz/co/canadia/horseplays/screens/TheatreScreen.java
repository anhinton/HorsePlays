package nz.co.canadia.horseplays.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import nz.co.canadia.horseplays.HorsePlays;
import nz.co.canadia.horseplays.SpeechUI;
import nz.co.canadia.horseplays.Theatre;
import nz.co.canadia.horseplays.script.PlayScript;
import nz.co.canadia.horseplays.util.Constants;

/**
 * The stage screen where the game is played.
 */

public class TheatreScreen implements Screen, InputProcessor {
    private final HorsePlays game;
    private final SpeechUI speechUI;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Table table;
    private PlayScript playScript;

    private Theatre theatre;

    public TheatreScreen(final HorsePlays game) {
        this.game = game;

        playScript = new PlayScript();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT, camera);
        stage = new Stage(viewport);
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                TheatreScreen.this.touchUp((int)(x), (int)(y), pointer, button);
            }
        });
        table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
        stage.addActor(table);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        theatre = new Theatre();

        speechUI = new SpeechUI(table, playScript, theatre);
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
        font.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
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
        theatre.setTouchDown(true);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        theatre.setTouchDown(false);
        switch(theatre.getCurrentTheatreScene()) {
             case START:
                theatre.startShow();
                break;
            case OPENING:
                break;
            case PERFORMING:
                theatre.setCurrentZoomLevel(Constants.ZoomLevel.CLOSE);
                speechUI.speak();
                break;
            case CLOSING:
                break;
            case FINISHED:
                break;
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
    public boolean scrolled(int amount) {
        return false;
    }
}

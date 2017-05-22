package nz.co.canadia.horseplays.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import nz.co.canadia.horseplays.HorsePlays;
import nz.co.canadia.horseplays.util.Constants;

/**
 * Title screen to show on launch
 */

public class TitleScreen implements Screen {
    private final HorsePlays game;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Viewport viewport;

    public TitleScreen (final HorsePlays game) {
        this.game = game;

        font = new BitmapFont(Gdx.files.internal("fonts/TlwgMonoBold64.fnt"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT,
                camera);

    }

    @Override
    public void show() {

    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(168/255f, 16/255f, 6/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        font.setColor(Constants.FONT_COLOR);
        font.draw(game.batch, "HORSE PLAYS", 100, 100);


        game.batch.end();

        // ESC or BACK quits the game
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)
                || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
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
}

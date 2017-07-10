package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nz.co.canadia.horseplays.util.Constants;

/**
 * The theatre in which we stage our play
 */

class Theatre {

    private Backdrop backdrop;
    private Spotlight spotlight01;
    private Spotlight spotlight02;
    private TheatreStage stage;
    private Texture horseTexture01;
    private Horse horse01;
    private Texture horseTexture02;
    private Horse horse02;
    private Curtains curtains;

    Theatre() {
        stage = new TheatreStage(0, 0);
        backdrop = new Backdrop(Constants.APP_WIDTH / 2, stage.getHeight());
        spotlight01 = new Spotlight(
                Constants.APP_WIDTH / 11,
                Constants.APP_HEIGHT * 8 / 9,
                false);
        spotlight02 = new Spotlight(
                Constants.APP_WIDTH * 10 / 11,
                Constants.APP_HEIGHT * 8 / 9,
                true);
        horseTexture01 = new Texture(Gdx.files.internal("graphics/horse01.png"));
        horse01 = new Horse(horseTexture01, Constants.APP_WIDTH / 4, stage.getHeight(), true);
        horseTexture02 = new Texture(Gdx.files.internal("graphics/horse02.png"));
        horse02 = new Horse(horseTexture02, Constants.APP_WIDTH * 3 / 4, stage.getHeight(), true);
        curtains = new Curtains();
    }

    void draw (SpriteBatch batch) {
        backdrop.draw(batch);
        horse01.draw(batch);
        horse02.draw(batch);
        stage.draw(batch);
        spotlight01.draw(batch);
        spotlight02.draw(batch);
        curtains.draw(batch);
    }

    void dispose() {
        backdrop.dispose();
        curtains.dispose();
        horseTexture01.dispose();
        horseTexture02.dispose();
        spotlight01.dispose();
        spotlight02.dispose();
        stage.dispose();
    }
}

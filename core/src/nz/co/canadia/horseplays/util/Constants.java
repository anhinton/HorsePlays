package nz.co.canadia.horseplays.util;

import com.badlogic.gdx.graphics.Color;

/**
 * Store game constants
 */

public class Constants {
    public static final String GAME_NAME = "Horse Plays";
    public static final int APP_WIDTH = 960;
    public static final int APP_HEIGHT = 540;

    public static final Color BACKGROUND_COLOR = new Color(168/255f, 16/255f,
            6/255f, 1);
    public static final Color FONT_COLOR = new Color(1, 1, 1, 1);

    public static final float CURTAIN_SPEED = 200;
    public static final float OPEN_CURTAIN_WIDTH = APP_WIDTH / 16;
    public enum Side { LEFT, RIGHT }

    public static final float HORSE_SPEED = CURTAIN_SPEED;
    public static final float HORSE_MARK = APP_WIDTH / 4;

    // a scene is used to work out what part of the game we are up to
    public enum TheatreScene {
        START, OPENING, PERFORMING, CLOSING, FINISHED
    }
}

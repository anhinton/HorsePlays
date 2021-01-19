package nz.co.canadia.horseplays.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

/**
 * Store game constants
 */

public class Constants {
    public static final String GAME_NAME = "Horse Plays";
    public static final int APP_WIDTH = 960;
    public static final int APP_HEIGHT = 540;

    public static final Color BACKGROUND_COLOR = new Color(
            168/255f, 16/255f, 6/255f, 1);
    public static final Color FONT_COLOR = new Color(1, 1, 1, 1);

    public static final float CURTAIN_SPEED = 200;
    public static final float OPEN_CURTAIN_WIDTH = APP_WIDTH / 16f;
    public static final int LINE_LENGTH = 60;

    public enum HorseSide { LEFT, RIGHT }

    public static final float HORSE_SPEED = CURTAIN_SPEED;
    public static final float HORSE_MARK = APP_WIDTH / 4f;

    public static final float SPOTLIGHT_OFFSET_X = Constants.APP_WIDTH / 11f;
    public static final float SPOTLIGHT_OFFSET_Y = Constants.APP_HEIGHT * 8f / 9;

    // a scene is used to work out what part of the game we are up to
    public enum CurrentScene {
        START, OPENING, PERFORMING, CLOSING, FINISHED
    }

    // zoomLevel determines whether we are looking at the whole stage, or just one horse
    public enum ZoomLevel {
        WIDE, CLOSE
    }

    public static final String END_KNOT = "DONE";

    public static final int BUTTON_PAD = 20;
    public static final int BUTTON_ALIGN = Align.center;
}

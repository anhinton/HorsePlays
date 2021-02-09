package nz.co.canadia.horseplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
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

    // which title menu is showing
    public enum CurrentTitleMenu {
        MAIN, NEW, SETTINGS, CREDITS, QUIT
    }

    // which game ui is showing
    public enum CurrentGameMenu {
        GAME, MENU
    }

    // a scene is used to work out what part of the game we are up to
    public enum CurrentScene {
        START, OPENING, PERFORMING, CLOSING, FINISHED
    }

    // zoomLevel determines whether we are looking at the whole stage, or just one horse
    public enum ZoomLevel {
        WIDE, CLOSE
    }

    public static final String END_KNOT = "DONE";

    public static final int BIG_FONT_SIZE = 74;
    public static final int SMALL_FONT_SIZE = MathUtils.round(34f / APP_HEIGHT * Gdx.graphics.getBackBufferHeight());

    public static final int BUTTON_PAD = MathUtils.round(20f / APP_HEIGHT * Gdx.graphics.getBackBufferHeight());
    public static final int BUTTON_ALIGN = Align.center;
    public static final int MENU_BUTTON_WIDTH = MathUtils.round(APP_WIDTH / 3f);
    public static final int SPEECH_BUTTON_WIDTH = MathUtils.round(Constants.APP_WIDTH * 2f / 3);
    public static final int VOLUME_BUTTON_WIDTH = MathUtils.round(100f / APP_WIDTH * Gdx.graphics.getBackBufferWidth());

    public static final String AUTOSAVE_PATH = "nz.co.canadia.horseplays.autosave";
    public static final String SETTINGS_PATH = "nz.co.canadia.horseplays.settings";
    public static final float MUSIC_VOLUME_DEFAULT = 0.5f;
    public static final float SOUND_VOLUME_DEFAULT = 0.8f;
}

package nz.co.canadia.horseplays.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import nz.co.canadia.horseplays.HorsePlays;
import nz.co.canadia.horseplays.util.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setResizable(false);
		config.setTitle(Constants.GAME_NAME);
		config.setWindowedMode(Constants.APP_WIDTH, Constants.APP_HEIGHT);
		new Lwjgl3Application(new HorsePlays(new DesktopFontLoader()), config);
	}
}

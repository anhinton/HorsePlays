package nz.co.canadia.horseplays.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import nz.co.canadia.horseplays.HorsePlays;
import nz.co.canadia.horseplays.util.Constants;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(Constants.APP_WIDTH, Constants.APP_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new HorsePlays(new HtmlFontLoader());
        }
}
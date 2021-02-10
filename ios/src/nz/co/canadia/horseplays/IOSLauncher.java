package nz.co.canadia.horseplays;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import nz.co.canadia.horseplays.HorsePlays;

public class IOSLauncher extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.allowIpod = true;
        config.orientationLandscape = true;
        config.orientationPortrait = false;
        config.statusBarVisible = false;
        config.useAccelerometer = false;
        config.useCompass = false;
        return new IOSApplication(new HorsePlays(new IOSFontLoader()), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}
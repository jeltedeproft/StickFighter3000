package jelte.mygame.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import jelte.mygame.StickFighter;

public class Lwjgl3Launcher {

	public static void main(String[] args) {
		createApplication();
	}

	private static Lwjgl3Application createApplication() {
		return new Lwjgl3Application(new StickFighter(), getDefaultConfiguration());
	}

	//// If you remove the refresh rate line and set Vsync to false, you can get unlimited
	//// FPS, which can be useful for testing performance, but can also be very
	//// stressful to some hardware. You may also need to configure GPU drivers to
	//// fully disable Vsync; this can cause screen tearing.
	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("StickFighter3000");
		configuration.disableAudio(true);// tuningfork requirement
		configuration.useVsync(true);
		configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
		configuration.setWindowedMode(3840, 2160);
		// configuration.setWindowedMode(600, 600);
		// configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		configuration.setWindowIcon("icon/icon128.png", "icon/icon64.png", "icon/icon32.png", "icon/icon16.png");
		return configuration;
	}
}
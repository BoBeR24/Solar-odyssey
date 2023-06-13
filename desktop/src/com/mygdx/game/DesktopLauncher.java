package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.GUI.Odyssey;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setForegroundFPS(120);
		config.setTitle("Odyssey");
		config.setWindowSizeLimits(1920, 1080, 1920,1080);
		// gets the information from the display to set up fullscreen:
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

		new Lwjgl3Application(new Odyssey(), config);
	}
}

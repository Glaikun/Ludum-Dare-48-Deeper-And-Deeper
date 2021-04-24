package com.glaikunt.framework.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.glaikunt.framework.Display;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//		config.width = (int) (1440 * .9f);
//		config.height = (int) (900 * .9f);
		config.width = 800;
		config.height = 600;
		config.title = "Libgdx Framework";
		config.resizable = false;
		new LwjglApplication(new Display(), config);
	}
}

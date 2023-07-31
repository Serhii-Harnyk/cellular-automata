package sharnyk.java;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import sharnyk.testca.mapgen.MapgenApp;


public class MapgenAppDesktop {
	public static void main (String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1000, 750);
		new Lwjgl3Application(new MapgenApp(), config);
	}
}

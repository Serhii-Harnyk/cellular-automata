package sharnyk.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import sharnyk.testca.mapgen.MapgenApp;


public class MapgenAppDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 750;
		config.width = 1000;
		new LwjglApplication(new MapgenApp(), config);
	}
}

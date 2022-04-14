package sharnyk.html;

import sharnyk.core.MapgenApp;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class MapgenAppHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new MapgenApp();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}

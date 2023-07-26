package sharnyk.testca.mapgen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sharnyk.core.MapgenMenu;
import sharnyk.testca.mapgen.diasqu.DiaSquGame;
import sharnyk.testca.mapgen.majvote.screens.MajorityVoteMenu;

public class MapgenApp extends Game {

  public SpriteBatch batch;
  public BitmapFont font;

  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont(); // use libGDX's default Arial font
    this.setScreen(new MapgenMenu(this));
  }

  public void render() {
    super.render(); // important!
  }

  public void dispose() {
    batch.dispose();
    font.dispose();
  }

}


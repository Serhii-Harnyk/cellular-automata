package sharnyk.testca.mapgen.gradient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import sharnyk.testca.mapgen.MapgenApp;
import sharnyk.testca.mapgen.diasqu.DiaSqu;

public class GradientGame implements Screen {

  private static final int WIDTH = 1025;
  private static final int HEIGHT = 1025;

  private Color[] colorMap = {Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.CHARTREUSE, Color.BROWN, Color.CORAL, Color.CYAN,
      Color.GOLD, Color.LIME, Color.ORANGE, Color.MAROON, Color.FOREST, Color.NAVY, Color.ROYAL, Color.WHITE};

  // visual utils
  private ShapeRenderer renderer;
  private final MapgenApp game;
  private OrthographicCamera camera;
  private Skin uiSkin;
  private Stage uiStage;



  public GradientGame(final MapgenApp gam) {
    this.game = gam;
    uiSkin = new Skin(Gdx.files.internal("mapgen/uiskin.json"));
    uiStage = new Stage(new ScreenViewport(), game.batch);

    // create the camera and the SpriteBatch
    camera = new OrthographicCamera();
    camera.setToOrtho(false, WIDTH, HEIGHT+30);

  }


  @Override
  public void render(float delta) {
    // clear the screen with a dark blue color. The
    // arguments to clear are the red, green
    // blue and alpha component in the range [0,1]
    // of the color to be used to clear the screen.
    ScreenUtils.clear(1, 1, 1, 0);

    // tell the camera to update its matrices.
    camera.update();

    renderer.setProjectionMatrix(camera.combined);
    renderer.begin(ShapeType.Filled);
    renderer.setColor(Color.BLACK);

    int scale = (int) Math.pow(2, 6);

//    for(int x = 0; x<tileMap.length; x++) {
//      for(int y = 0; y<tileMap[0].length; y++) {
//        renderer.setColor(colorMap[tileMap[x][y]]);
//        renderer.setColor(new Color());
//        renderer.rect(x*scale, y*scale , scale, scale);
//      }
//    }

    for(int i=0; i<colorMap.length; i++) {
      renderer.setColor(colorMap[i]);
      renderer.rect(i*scale,0,scale,HEIGHT+scale);
    }

    renderer.end();

    uiStage.act();
    uiStage.draw();
  }


  @Override
  public void resize(int width, int height) {
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(uiStage);
    renderer = new ShapeRenderer();
    renderer.setAutoShapeType(true);
    game.font.setColor(0,0,1,0.8f);
    game.font.getData().setScale(1.5f);


  }


  @Override
  public void hide() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void dispose() {
    uiStage.dispose();
  }

}

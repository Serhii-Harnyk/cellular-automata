package sharnyk.testca.mapgen.trail;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class TrailGame implements Screen {

  private static final int WIDTH = 1025;
  private static final int HEIGHT = 1025;

  private final Color[] colorMap = {Color.WHITE, Color.BLACK};

  // visual utils
  private ShapeRenderer renderer;
  private final MapgenApp game;
  private final OrthographicCamera camera;
  private final Skin uiSkin;
  private final Stage uiStage;

  // logic utils
  private final Trail trail;
  // game state
  private final int step = 0;
  int[][] tileMap;
  TrailGameConfig trailGameConfig;




  public TrailGame(final MapgenApp gam, TrailGameConfig config) {
    this.game = gam;
    uiSkin = new Skin(Gdx.files.internal("mapgen/uiskin.json"));
    uiStage = new Stage(new ScreenViewport(), game.batch);

    // create the camera and the SpriteBatch
    camera = new OrthographicCamera();
    camera.setToOrtho(false, WIDTH, HEIGHT+30);

    trail = new Trail(config.getHeight(), config.getWidth(), config.getPathLength(), config.getMaxChunkLength());
    trailGameConfig = config;

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

    int scale = Math.min(WIDTH/10, HEIGHT/10);

    for(int x = 0; x<tileMap.length; x++) {
      for(int y = 0; y<tileMap[0].length; y++) {
        renderer.setColor(colorMap[tileMap[x][y]]);
        renderer.rect(x*scale, y*scale , scale, scale);
      }
    }

    renderer.end();

    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
      generateMap();

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

    generateMap();

  }

  private void generateMap() {
    tileMap = trail.trail();
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

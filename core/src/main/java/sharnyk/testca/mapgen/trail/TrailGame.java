package sharnyk.testca.mapgen.trail;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import sharnyk.testca.mapgen.MapgenApp;
import sharnyk.testca.mapgen.majvote.screens.MajorityVoteMenu;

public class TrailGame implements Screen {

  private static final int WIDTH = 1025;
  private static final int HEIGHT = 1025;

  private final Color[] colorMap = {Color.WHITE, Color.BLACK, Color.LIGHT_GRAY};

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

    int scale = Math.min(WIDTH/trailGameConfig.getWidth(), HEIGHT/trailGameConfig.getHeight());

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

    Table table = new Table(uiSkin);
    table.setFillParent(true);
    TextButton playButton = new TextButton( "Back", uiSkin);
    playButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {

        game.setScreen(new TrailMenu(game, trailGameConfig));
        dispose();
      }
    });
    table.add(playButton);

    uiStage.addActor(playButton);

  }

  private void generateMap() {
    tileMap = trail.field();

    //decorate with light gray
    for(int x = 0; x<tileMap.length; x++) {
      for(int y = 0; y<tileMap[0].length; y++) {
        if(tileMap[x][y] == 0 && (x+y)%2==1) {
          tileMap[x][y] = 2;
        }
      }
    }
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

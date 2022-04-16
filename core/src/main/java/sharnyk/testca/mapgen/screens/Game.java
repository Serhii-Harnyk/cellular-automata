package sharnyk.testca.mapgen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
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
import sharnyk.testca.mapgen.MajorityVoteApp;
import sharnyk.testca.mapgen.domain.split.MajorityVoteConcurrentSplitter;
import sharnyk.testca.mapgen.domain.split.MajorityVoteSplitter;
import sharnyk.testca.mapgen.domain.MapInit;
import sharnyk.testca.mapgen.domain.MajorityVote;
import sharnyk.testca.mapgen.domain.neigh.Diagonal;
import sharnyk.testca.mapgen.domain.neigh.Moore;
import sharnyk.testca.mapgen.domain.neigh.Neighbourhood;
import sharnyk.testca.mapgen.domain.neigh.VonNeumann;
import sharnyk.testca.mapgen.domain.topology.Cylinder;
import sharnyk.testca.mapgen.domain.topology.Plain;
import sharnyk.testca.mapgen.domain.topology.Topology;
import sharnyk.testca.mapgen.domain.topology.Torus;

public class Game implements Screen {

  private static final int WIDTH = 800;
  private static final int HEIGHT = 480;

  private final GameConfig config;
  private Color[] colorMap = {Color.BLACK, Color.WHITE, Color.RED, Color.GREEN, Color.BLUE};

  // visual utils
  private ShapeRenderer renderer;
  private final MajorityVoteApp game;
  private OrthographicCamera camera;
  private Skin uiSkin;
  private Stage uiStage;

  // logic utils
  private MapInit mapInit = new MapInit();
  private MajorityVote majorityVote;
  private MajorityVoteConcurrentSplitter splitter;

  // game state
  private int[][] tileMap;
  private int whiteSum = 0;
  private int step = 0;




  public Game(final MajorityVoteApp gam, GameConfig config) {
    this.game = gam;
    this.config = config;
    uiSkin = new Skin(Gdx.files.internal("mapgen/uiskin.json"));
    uiStage = new Stage(new ScreenViewport(), game.batch);

    Topology topology;
    if(config.getTopology().equals("Torus"))
      topology = new Torus();
    else if(config.getTopology().equals("Cylinder"))
      topology = new Cylinder();
    else
      topology = new Plain();

    Neighbourhood neigh;
    if(config.getTopology().equals("Von Neumann"))
      neigh = new VonNeumann();
    else if(config.getTopology().equals("Diagonal"))
      neigh = new Diagonal();
    else
      neigh = new Moore();
    majorityVote = new MajorityVote(config.getColors(), config.getNeighSize(),
        topology, neigh);
    splitter = new MajorityVoteConcurrentSplitter(majorityVote, 16);

//    rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
//    rainMusic.setLooping(true);

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

    int scale = config.getScale();
    for(int x = 0; x<tileMap.length; x++) {
      for(int y = 0; y<tileMap[0].length; y++) {
        renderer.setColor(colorMap[tileMap[x][y]]);
        renderer.rect(x*scale, y*scale, scale, scale);
      }
    }

    renderer.end();




    game.batch.begin();

    game.font.draw(game.batch, "Total white cells: " + whiteSum, 5, 735);
    game.font.draw(game.batch, "Step: " + step, 250, 735);
    game.font.draw(game.batch, "Scale: " + scale, 375, 735);
    game.font.draw(game.batch, "Neigh size: " + config.getNeighSize(), 475, 735);
    game.font.draw(game.batch, "Topology:" + config.getTopology(), 610, 735);
    if(config.getColors() == 2)
      game.font.draw(game.batch, "Density: " + config.getDensity(), 770, 735);

    game.batch.end();

    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
      applyMajorityVote();

    if (Gdx.input.isKeyPressed(Keys.ENTER))
      applyMajorityVote();

    uiStage.act();
    uiStage.draw();
  }

  private void applyMajorityVote() {
    tileMap = splitter.changeMap(tileMap);
    whiteSum = mapInit.countSum(tileMap);
    step++;
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

    tileMap = mapInit.noiseMap(WIDTH/config.getScale(), HEIGHT/config.getScale(), config.getColors(), config.getDensity());
    whiteSum = mapInit.countSum(tileMap);


    Table table = new Table(uiSkin);
    table.setFillParent(true);
    TextButton playButton = new TextButton( "Close", uiSkin);
    playButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {

        game.setScreen(new MainMenu(game));
        dispose();
      }
    });
    table.add(playButton);

    uiStage.addActor(playButton);
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

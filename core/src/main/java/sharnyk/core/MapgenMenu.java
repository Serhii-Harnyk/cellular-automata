package sharnyk.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import sharnyk.testca.mapgen.MapgenApp;
import sharnyk.testca.mapgen.diasqu.DiaSquGame;
import sharnyk.testca.mapgen.gradient.GradientGame;
import sharnyk.testca.mapgen.majvote.screens.GameConfig;
import sharnyk.testca.mapgen.majvote.screens.MajorityVoteMenu;
import sharnyk.testca.mapgen.surv.SurvivalGame;
import sharnyk.testca.mapgen.trail.TrailGame;
import sharnyk.testca.mapgen.trail.TrailMenu;

public class MapgenMenu implements Screen {

  MapgenApp game;
  OrthographicCamera camera;
  Stage uiStage;
  Skin uiSkin;
  GameConfig config;

  public MapgenMenu(final MapgenApp game) {
    GameConfig defaultConfig = GameConfig.builder()
        .scale(5)
        .density(Math.round(50f))
        .colors(2)
        .neighSize(1)
        .topology("Plain")
        .neigh("Moore")
        .build();

    this.game = game;
    this.config = defaultConfig;

    setUpScreen();
  }

  public MapgenMenu(final MapgenApp game, GameConfig config) {
    this.game = game;
    this.config = config;

    setUpScreen();
  }

  private void setUpScreen(){
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);
    uiSkin = new Skin(Gdx.files.internal("mapgen/uiskin.json"));
    uiStage = new Stage(new ScreenViewport(), game.batch);
  }

  @Override
  public void render(float delta) {

    ScreenUtils.clear(0, 0, 0.2f, 1);

    camera.update();
    game.batch.setProjectionMatrix(camera.combined);


    uiStage.act();
    uiStage.draw();

  }

  @Override
  public void resize(int width, int height) {
  }

  @Override
  public void show() {
    ScreenUtils.clear(0, 0, 0.2f, 1);
    Gdx.input.setInputProcessor(uiStage);

    createButtons();

  }

  private void createButtons() {

    Table table = new Table(uiSkin);
    table.setFillParent(true);


    TextButton majorityVote = new TextButton( "Majority Vote", uiSkin);
    majorityVote.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(new MajorityVoteMenu(game));
        dispose();
      }
    });

    TextButton diamondSquare = new TextButton( "Diamond-Square", uiSkin);
    diamondSquare.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(new DiaSquGame(game));
        dispose();
      }
    });

    TextButton gradient = new TextButton( "Colors Test", uiSkin);
    gradient.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(new GradientGame(game));
        dispose();
      }
    });

    TextButton survivalGame = new TextButton( "Survival Game", uiSkin);
    survivalGame.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(new SurvivalGame(game));
        dispose();
      }
    });

    TextButton trailGame = new TextButton( "Trail Game", uiSkin);
    trailGame.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(new TrailMenu(game));
        dispose();
      }
    });

    table.add(new Label("Select simulation", uiSkin)).colspan(2);
    table.row();
    table.add(new Label("", uiSkin)).colspan(2);
    table.row();



    table.add(majorityVote);
    table.row();
    table.add(diamondSquare);
    table.row();
    table.add(survivalGame);
    table.row();
    table.add(trailGame);

    uiStage.addActor(table);
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

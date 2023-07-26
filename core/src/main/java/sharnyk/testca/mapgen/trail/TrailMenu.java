package sharnyk.testca.mapgen.trail;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import sharnyk.testca.mapgen.MapgenApp;

public class TrailMenu implements Screen {

  MapgenApp game;
  OrthographicCamera camera;
  Stage uiStage;
  Skin uiSkin;
  TrailGameConfig config;

  public TrailMenu(final MapgenApp game) {
    TrailGameConfig defaultConfig = TrailGameConfig.builder()
            .height(10)
            .width(10)
            .pathLength(30)
            .maxChunkLength(4)
        .build();

    this.game = game;
    this.config = defaultConfig;

    setUpScreen();
  }

  public TrailMenu(final MapgenApp game, TrailGameConfig config) {
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

    final TextField height = new TextField(config.getHeight()+"", uiSkin);
    final TextField width = new TextField(config.getWidth()+"", uiSkin);
    final TextField pathLength = new TextField(config.getPathLength()+"", uiSkin);
    final TextField maxChunk = new TextField(config.getMaxChunkLength()+"", uiSkin);




    TextButton playButton = new TextButton( "Run Simulation", uiSkin);
    playButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {


        final TrailGameConfig config = TrailGameConfig.builder()
                .height(Integer.parseInt(height.getText()))
                .width(Integer.parseInt(width.getText()))
                .maxChunkLength(Integer.parseInt(maxChunk.getText()))
                .pathLength(Integer.parseInt(pathLength.getText()))
            .build();

        game.setScreen(new TrailGame(game, config));
        dispose();
      }
    });

    table.add(new Label("Trail Generator", uiSkin)).colspan(2);
    table.row();
    table.add(new Label("", uiSkin)).colspan(2);
    table.row();


    table.add(new Label("Width", uiSkin));
    table.add(width).fillX().uniformX();
    table.row().pad(10, 0, 10, 0);

    table.add(new Label("Height", uiSkin));
    table.add(height).fillX().uniformX();
    table.row().pad(10, 0, 10, 0);

    table.add(new Label("Path length", uiSkin));
    table.add(pathLength).fillX().uniformX();
    table.row().pad(10, 0, 10, 0);

    table.add(new Label("Max chunk length", uiSkin));
    table.add(maxChunk).fillX().uniformX();
    table.row().pad(10, 0, 10, 0);

    table.add(playButton);

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

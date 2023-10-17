package sharnyk.testca.mapgen.oned;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import sharnyk.testca.mapgen.CAApp;

public class OneDMenu implements Screen {

  CAApp game;
  OrthographicCamera camera;
  Stage uiStage;
  Skin uiSkin;
  OneDGameConfig config;

  public OneDMenu(final CAApp game) {
    OneDGameConfig defaultConfig = OneDGameConfig.builder()
            .height(500)
            .width(1000)
        .build();

    this.game = game;
    this.config = defaultConfig;

    setUpScreen();
  }

  public OneDMenu(final CAApp game, OneDGameConfig config) {
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



    TextButton playButton = new TextButton( "Run Simulation", uiSkin);
    playButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {


        final OneDGameConfig config = OneDGameConfig.builder()
                .height(Integer.parseInt(height.getText()))
                .width(Integer.parseInt(width.getText()))
            .build();

        game.setScreen(new OneDGame(game, config));
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

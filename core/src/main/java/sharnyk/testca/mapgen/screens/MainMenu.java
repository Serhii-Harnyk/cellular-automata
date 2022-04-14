package sharnyk.testca.mapgen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import sharnyk.testca.mapgen.MajorityVoteApp;

public class MainMenu implements Screen {

  final MajorityVoteApp game;
  OrthographicCamera camera;
  Stage uiStage;
  Skin uiSkin;

  public MainMenu(final MajorityVoteApp game) {
    this.game = game;

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

    final SelectBox<Integer> selectScale = new SelectBox<>(uiSkin);
    selectScale.setItems(1,2,3,4,5,10,50);
    selectScale.setSelected(5);

    final SelectBox<Integer> colors = new SelectBox<>(uiSkin);
    colors.setItems(2,3,4,5);
    colors.setSelected(2);

    final SelectBox<Integer> neighSize = new SelectBox<>(uiSkin);
    neighSize.setItems(1, 2,3,4,5);
    neighSize.setSelected(1);


    final Slider densitySlider = new Slider(1f, 100f, 1f, false, uiSkin);
    densitySlider.setValue(50f);

    final Label densityLabel = new Label(Float.valueOf(densitySlider.getValue()).toString(), uiSkin);
    densitySlider.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        densityLabel.setText(Float.valueOf(densitySlider.getValue()).toString());
      }
    });

    final SelectBox<String> topology= new SelectBox<>(uiSkin);
    topology.setItems("Plain", "Cylinder", "Torus");
    topology.setSelected("Plain");

    final SelectBox<String> neigh = new SelectBox<>(uiSkin);
    neigh.setItems("Moore", "Von Neumann", "Diagonal");
    neigh.setSelected("Moore");


    TextButton playButton = new TextButton( "Run Simulation", uiSkin);
    playButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {


        final GameConfig config = GameConfig.builder()
            .scale(selectScale.getSelected())
            .density(Math.round(densitySlider.getValue()))
            .colors(colors.getSelected())
            .neighSize(neighSize.getSelected())
            .topology(topology.getSelected())
            .neigh(neigh.getSelected())
            .build();

        game.setScreen(new Game(game, config));
        dispose();
      }
    });

    table.add(new Label("Majority Vote Rule Simulation", uiSkin)).colspan(2);
    table.row();
    table.add(new Label("", uiSkin)).colspan(2);
    table.row();


    table.add(new Label("Select scale", uiSkin));
    table.add(selectScale).fillX().uniformX();
    table.row().pad(10, 0, 10, 0);

    table.add(new Label("Number of colors", uiSkin));
    table.add(colors).fillX().uniformX();
    table.row().pad(10, 0, 10, 0);

    table.add(new Label("Density(only for 2 colors)", uiSkin));
    table.add(densitySlider).fillX().uniformX();
    table.add(densityLabel);
    table.row().pad(10, 0, 10, 0);

    table.add(new Label("Neighborhood size", uiSkin));
    table.add(neighSize).fillX().uniformX();
    table.row().pad(10, 0, 10, 0);

    table.add(new Label("Topology", uiSkin));
    table.add(topology).fillX().uniformX();
    table.row().pad(10, 0, 10, 0);

    table.add(new Label("Neighbourhood", uiSkin));
    table.add(neigh).fillX().uniformX();
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

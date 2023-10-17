package sharnyk.testca.mapgen.core;

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
import sharnyk.testca.mapgen.CAApp;
import sharnyk.testca.mapgen.majvote.screens.MajorityVoteMenu;
import sharnyk.testca.mapgen.oned.OneDMenu;
import sharnyk.testca.mapgen.ships.ShipsMenu;
import sharnyk.testca.mapgen.trail.TrailMenu;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Screen {

    CAApp game;
    OrthographicCamera camera;
    Stage uiStage;
    Skin uiSkin;

    public MainMenu(final CAApp game) {
        this.game = game;

        setUpScreen();
    }

    private void setUpScreen() {
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

        List<TextButton> buttons = new ArrayList<>();
        buttons.add(newGameButton("Majority Vote", new MajorityVoteMenu(game)));
        buttons.add(newGameButton("Trail Game", new TrailMenu(game)));
        buttons.add(newGameButton("1d CA", new OneDMenu(game)));
        buttons.add(newGameButton("Ships", new ShipsMenu(game)));

        table.add(new Label("Select simulation", uiSkin)).colspan(2);
        table.row();
        table.add(new Label("", uiSkin)).colspan(2);
        table.row();


        for(TextButton button : buttons) {
            table.add(button);
            table.row();
        }

        uiStage.addActor(table);
    }

    private TextButton newGameButton(String text,Screen screen) {
        TextButton button = new TextButton(text, uiSkin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(screen);
                dispose();
            }
        });
        return button;
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

package sharnyk.testca.mapgen.ships;

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
import sharnyk.testca.mapgen.CAApp;

public class ShipsGame implements Screen {

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 1024;

    private final Color[] colorMap = {Color.WHITE, Color.RED, Color.DARK_GRAY, Color.SKY, Color.LIGHT_GRAY};

    // visual utils
    private ShapeRenderer renderer;
    private final CAApp game;
    private final OrthographicCamera camera;
    private final Skin uiSkin;
    private final Stage uiStage;

    // logic utils
    private final Ships ships;
    // game state
    private final int step = 0;
    int[][] tileMap;
    ShipsGameConfig shipsGameConfig;


    public ShipsGame(final CAApp gam, ShipsGameConfig config) {
        this.game = gam;
        uiSkin = new Skin(Gdx.files.internal("mapgen/uiskin.json"));
        uiStage = new Stage(new ScreenViewport(), game.batch);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT + 30);

        ships = new Ships(config.getHeight(), config.getWidth(), config.getPathLength());
        shipsGameConfig = config;

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

        int scale = Math.min(WIDTH / shipsGameConfig.getWidth(), HEIGHT / shipsGameConfig.getHeight());

        for (int x = 0; x < tileMap.length; x++) {
            for (int y = 0; y < tileMap[0].length; y++) {
                renderer.setColor(colorMap[tileMap[x][y]]);
                renderer.rect(x * scale, y * scale, scale, scale);
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
        game.font.setColor(0, 0, 1, 0.8f);
        game.font.getData().setScale(1.5f);

        generateMap();

    }

    private void generateMap() {
        tileMap = cloneArray(ships.field());

        decorate();
    }

    private void decorate() {
        //decorate with light gray
        for (int x = 0; x < tileMap.length; x++) {
            for (int y = 0; y < tileMap[0].length; y++) {
                if (tileMap[x][y] == 0 && (x + y) % 2 == 1) {
                    tileMap[x][y] = 4;
                }
            }
        }
    }

    private int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
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

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.utils.ScreenUtils;


/**
 * Screen in which solar system simulation happens
 * */
public class SolarSystemScreen implements Screen {

    private final Odyssey game;
    public static final OrthographicCamera camera = new OrthographicCamera();
    private final SimulationLogic logic;
    public static State state = State.RUNNING;


    public SolarSystemScreen(final Odyssey game) {
        this.game = game;

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // create a camera
        camera.zoom = 2f;
        camera.update(); // update camera

        game.shape.setProjectionMatrix(camera.combined);

        this.logic = new SimulationLogic(game); // class which updates state of the game

        SystemInitializer.fillSystemWithPlanets(); // adds planets and the Sun to the system

//        for (double x = 0.2360; x < 0.2410; x = x + 0.0002) {
//            for (double y = 0.006862175; y < 0.007162175; y = y + 0.0002) {
//                for (double z = 0.08; z < 0.086736; z = z + 0.001) {
//                    ProbeLauncher.launch(new Vector(41.0 + x, -15.0 - y, -3.1 - z));
//                }
//            }
//        }


        ProbeLauncher.launch(new Vector(41.2384, -15.006862175, -3.183)); // probe that hits titan

    }

    /**
     * Called when screen appears
     * */
    @Override
    public void show() {
    }

    /**
     * renders game frames. Launches logic.update for updating game logic
     * */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // trails on/off

        logic.moveCameraToProbe(camera); // if you want to make camera follow the probe - uncomment this
        game.shape.setProjectionMatrix(camera.combined);

        game.shape.begin(ShapeType.Filled);

        logic.update();

        game.shape.end();

    }

    /**
     * Called when size of the window is changed
     * */
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        logic.close();
    }
}

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

        this.logic = new SimulationLogic(game); // initialize our simulation
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

        //logic.moveCameraToProbe(camera); // if you want to make camera follow the probe - uncomment this
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

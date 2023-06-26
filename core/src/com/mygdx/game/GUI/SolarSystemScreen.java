package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameLogic.FlightLogic;
import com.mygdx.game.GameLogic.State;
import com.mygdx.game.PhysicsEngine.CameraUtils;


/**
 * Screen in which solar system simulation happens
 * */
public class SolarSystemScreen implements Screen {

    private final Odyssey game;
    public static final OrthographicCamera camera = new OrthographicCamera();
    private final FlightLogic logic;
    public static State state = State.RUNNING;
    public static Vector3 centerScreenCords;

    public SolarSystemScreen(final Odyssey game) {
        this.game = game;

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // create a camera
        camera.zoom = 2f;
        camera.update(); // update camera

        game.shape.setProjectionMatrix(camera.combined);

        centerScreenCords = new Vector3((Gdx.graphics.getWidth()) / 2.0f ,
                (Gdx.graphics.getHeight()) / 2.0f, 0);

        this.logic = new FlightLogic(game); // initialize our simulation
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

        CameraUtils.moveCameraToProbe(camera, centerScreenCords); // if you want to make camera follow the probe - uncomment this
        game.shape.setProjectionMatrix(camera.combined);

        game.shape.begin(ShapeType.Filled);
        game.batch.begin();

        logic.update();

        game.batch.end();
        game.shape.end();

    }

    /**
     * Called when size of the window is changed
     * */
    @Override
    public void resize(int width, int height) {

    }

    /** method to pause the simulation by switching current state of the game to paused
     * */
    @Override
    public void pause() {
        SolarSystemScreen.state = State.PAUSED;
    }

    /** method to unpause the simulation by switching current state of the game to running
     * */
    @Override
    public void resume() {
        SolarSystemScreen.state = State.RUNNING;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        logic.close();
    }
}
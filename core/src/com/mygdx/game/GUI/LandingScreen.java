package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameLogic.FlightLogic;
import com.mygdx.game.GameLogic.LandingLogic;
import com.mygdx.game.GameLogic.State;
import com.mygdx.game.PhysicsEngine.CameraUtils;

public class LandingScreen implements Screen {

    private final Odyssey game;
    public static final OrthographicCamera camera = new OrthographicCamera();
    private final LandingLogic logic;
    public static State state = State.RUNNING;
    public static Vector3 centerScreenCords;

    public LandingScreen(final Odyssey game) {
        this.game = game;

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // create a camera
        camera.zoom = 3f;
        camera.update(); // update camera

        centerScreenCords = new Vector3((Gdx.graphics.getWidth()) / 2.0f ,
                (Gdx.graphics.getHeight()) / 2.0f, 0);

        game.shape.setProjectionMatrix(camera.combined);

        this.logic = new LandingLogic(game); // initialize our simulation
    }

    @Override
    public void show() {

    }

    /**
     * renders game frames. Launches logic.update for updating game logic
     * */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // trails on/off

//        CameraUtils.moveCameraToProbe(camera, centerScreenCords); // if you want to make camera follow the probe - uncomment this
        game.shape.setProjectionMatrix(camera.combined);

        game.shape.begin(ShapeRenderer.ShapeType.Filled);

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

    }
}

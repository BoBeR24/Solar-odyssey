package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameLogic.FlightLogic;
import com.mygdx.game.GameLogic.LandingLogic;
import com.mygdx.game.GameLogic.State;

public class LandingScreen implements Screen {

    private final Odyssey game;
    public static final OrthographicCamera camera = new OrthographicCamera();
    private final LandingLogic logic;
    public static State state = State.RUNNING;

    public LandingScreen(final Odyssey game) {
        this.game = game;

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // create a camera
        camera.zoom = 2f;
        camera.update(); // update camera

        game.shape.setProjectionMatrix(camera.combined);

        this.logic = new LandingLogic(game); // initialize our simulation
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // trails on/off

//        logic.moveCameraToProbe(camera); // if you want to make camera follow the probe - uncomment this
        game.shape.setProjectionMatrix(camera.combined);

        game.shape.begin(ShapeRenderer.ShapeType.Filled);

        logic.update();

        game.shape.end();
    }

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

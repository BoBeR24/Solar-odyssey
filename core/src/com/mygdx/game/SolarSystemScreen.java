package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

/**
 * Screen in which solar system simulation happens
 * */
public class SolarSystemScreen implements Screen {

    private final Odyssey game;
    public static final OrthographicCamera camera = new OrthographicCamera();
    private final SimulationLogic logic;
    public static State state = State.RUNNING;

    private final celestialBody sun;
    private final celestialBody mercury;
    private final celestialBody venus;
    private final celestialBody earth;
    private final celestialBody moon;
    private final celestialBody mars;
    private final celestialBody jupiter;
    private final celestialBody saturn;
    private final celestialBody titan;
    private final celestialBody neptune;
    private final celestialBody uranus;

    private ArrayList<celestialBody> bodies = SolarSystem.planets; // list of all bodies


    public SolarSystemScreen(final Odyssey game) {
        this.game = game;

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // create a camera
        camera.zoom = 2f;
        camera.update(); // update camera

        game.shape.setProjectionMatrix(camera.combined);

        this.logic = new SimulationLogic(game); // class which updates state of the game

        //BELOW ALL BODIES OF THE SIMULATION ARE ADDED//

        this.sun = new celestialBody("Sun"); // add all bodies here
        sun.setColor(Color.YELLOW);
        bodies.add(sun);

        this.mercury = new celestialBody("Mercury");
        mercury.setColor(Color.valueOf("#C0C2C9"));
        bodies.add(mercury);

        this.venus = new celestialBody("Venus");
        venus.setColor(Color.valueOf("#f7e4ab"));
        bodies.add(venus);

        this.earth = new celestialBody("Earth");
        earth.setColor(Color.BLUE);
        bodies.add(earth);

        this.moon = new celestialBody("Moon");
        moon.setColor(Color.WHITE);
        bodies.add(moon);

        this.mars = new celestialBody("Mars");
        mars.setColor(Color.valueOf("#D6723B"));
        bodies.add(mars);

        this.jupiter = new celestialBody("Jupiter");
        jupiter.setColor(Color.valueOf("#c99039"));
        bodies.add(jupiter);

        this.saturn = new celestialBody("Saturn");
        saturn.setColor(Color.valueOf("#cae8b0c"));
        bodies.add(saturn);

        this.titan = new celestialBody("Titan");
        titan.setColor(Color.valueOf("#f2a900"));
        bodies.add(titan);

        this.neptune = new celestialBody("Neptune");
        neptune.setColor(Color.valueOf("#d1e7e7"));
        bodies.add(neptune);

        this.uranus = new celestialBody("Uranus");
        uranus.setColor(Color.valueOf("#5b5ddf"));
        bodies.add(uranus);

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

        logic.moveCamera(camera); // if you want to make camera follow the probe - uncomment this
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

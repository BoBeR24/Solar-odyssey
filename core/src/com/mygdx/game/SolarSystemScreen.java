package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.math.Matrix3;

import java.util.ArrayList;

/**
 * Screen in which solar system simulation happens
 * */
public class SolarSystemScreen implements Screen {

    private final Odyssey game;
    private final OrthographicCamera camera;
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
//    private final Probe probe;

    private ArrayList<celestialBody> bodies = SolarSystem.planets; // list of all bodies
    private ArrayList<Probe> probes = SolarSystem.probes; // list of all bodies



    public SolarSystemScreen(final Odyssey game) {
        this.game = game;

        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // create a camera
        camera.zoom = 5f;
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

        for (int i = 0; i < 1; i++) {
            ProbeLauncher.launch(new Vector(40.0, -15.038462175, -3.168796));
        }

//        ProbeLauncher.launch(new Vector(40.0, -15.1, -3.1));
//        ProbeLauncher.launch(new Vector(45.0, -17.1, -5.1));

//
//        this.probe = new celestialBody("Probe");
//        probe.setLocation(earth.getLocation().x + earth.getRadius(), earth.getLocation().y - earth.getRadius(), earth.getLocation().z);
////        probe.setVelocity(earth.getVelocity().x + 40.0, earth.getVelocity().y - 15, earth.getVelocity().z);
//        probe.setVelocity(earth.getVelocity().x + 40.0, earth.getVelocity().y - 15, earth.getVelocity().z);
//        probe.setColor(Color.GREEN);
//        probe.setHeight(10);
//        probe.setWidth(10);
//        bodies.add(probe);
//        Launch.launchBunch(100, earth, new Vector(earth.getLocation().x + 1, earth.getLocation().y-1,0),
//                new Vector(earth.getLocation().x + 1, earth.getLocation().y - 1, 0),
//                Math.PI / 180, new Vector(40.0, -15.0, 0));

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
//        ScreenUtils.clear(0, 0, 0, 1); // trails on/off

        game.shape.setProjectionMatrix(camera.combined);

//        logic.moveCamera(camera); // if you want to make camera follow the probe - uncomment this

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

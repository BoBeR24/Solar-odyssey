package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Screen in which solar system simulation happens
 * */
public class SolarSystemScreen implements Screen {

    private final Odyssey game;
    private final OrthographicCamera camera;
    private final SimulationLogic logic;

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

    private ArrayList<celestialBody> bodies = SolarSystem.bodies; // list of all bodies
    


    public SolarSystemScreen(final Odyssey game) {
        this.game = game;

        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); // create a camera
        camera.zoom = 5f;
        camera.update(); // update camera
        game.shape.setProjectionMatrix(camera.combined);
        this.logic = new SimulationLogic(game); // class which updates state of the game


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
        mercury.setColor(Color.WHITE);
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

        // specify center of the system as center of the sun
    }

    /**
     * Called when screen appears
     * */
    @Override
    public void show() {
    }

    /**
     * renders game frames. Game logic happens here(in fact is a so-called Game Loop)
     * */
    @Override
    public void render(float delta) {
        game.shape.setProjectionMatrix(camera.combined);

        game.shape.begin(ShapeType.Filled);

        logic.update();

        game.shape.end();


//        game.batch.begin();

//        System.out.println(sun.getTexture().getWidth());
//        game.batch.draw(sun.getTexture(), centerScreenCords.x, centerScreenCords.y, sun.getTexture().getWidth(), sun.getTexture().getHeight());
//        game.batch.draw(mercury.getTexture(), centerScreenCords.x + (float)mercury.getLocation().x, centerScreenCords.y + (float)mercury.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());
//        game.batch.draw(venus.getTexture(), centerScreenCords.x + (float)venus.getLocation().x, centerScreenCords.y + (float)venus.getLocation().y, sun.getTexture().getWidth(), sun.getTexture().getHeight());
//        game.batch.draw(moon.getTexture(), centerScreenCords.x + (float)moon.getLocation().x, centerScreenCords.y + (float)moon.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());
//        game.batch.draw(mars.getTexture(), centerScreenCords.x + (float)mars.getLocation().x, centerScreenCords.y + (float)mars.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());
//        game.batch.draw(jupiter.getTexture(), centerScreenCords.x + (float)jupiter.getLocation().x, centerScreenCords.y + (float)jupiter.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());
//        game.batch.draw(saturn.getTexture(), centerScreenCords.x + (float)saturn.getLocation().x, centerScreenCords.y + (float)saturn.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());
//        game.batch.draw(titan.getTexture(), centerScreenCords.x + (float)titan.getLocation().x, centerScreenCords.y + (float)titan.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());

//        game.batch.draw(earth.getTexture(), centerScreenCords.x + (float)earth.getLocation().x, centerScreenCords.y + (float)earth.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());
//        game.batch.draw(earth.getTexture(), centerScreenCords.x + 250, centerScreenCords.y + 250,earth.getTexture().getWidth(), earth.getTexture().getHeight());
//        Vector3
//        earth.getLocation().set(earth.getLocation().multiply(1.0 / scaleFactor));
//        System.out.println(earth.getLocation().multiply(1.0 / scaleFactor));
//        game.batch.draw(earth.getTexture(), (float) (centerScreenCords.x + earth.getLocation().x / scaleFactor), (float) (centerScreenCords.y + earth.getLocation().y / scaleFactor), earth.getTexture().getWidth(), earth.getTexture().getHeight());
//        game.batch.draw(moon.getTexture(), (float) (centerScreenCords.x + moon.getLocation().x / scaleFactor), (float) (centerScreenCords.y + moon.getLocation().y / scaleFactor), moon.getTexture().getWidth(), moon.getTexture().getHeight());

//        game.batch.end();
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

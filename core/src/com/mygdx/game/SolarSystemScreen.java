package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.math.Vector3;

/**
 * Screen in which solar system simulation happens
 * */
public class SolarSystemScreen implements Screen {

    final Odyssey game;
    OrthographicCamera camera;
    private final Vector3 centerScreenCords;
    final celestialBody sun;
    final celestialBody mercury;
    final celestialBody venus;
    final celestialBody earth;
    final celestialBody moon;
    final celestialBody mars;
    final celestialBody jupiter;
    final celestialBody saturn;
    final celestialBody titan;
    final celestialBody neptune;
    final celestialBody uranus;

    final double scalingFactor = 407734; // pre-calculated scaling factor
    


    public SolarSystemScreen(final Odyssey game) {
        this.game = game;

        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); // create a camera
        camera.zoom = 5f;
        camera.update(); // update camera
        game.shape.setProjectionMatrix(camera.combined);


        this.sun = new celestialBody("Sun"); // add all bodies here
        this.mercury = new celestialBody("Mercury");
        this.venus = new celestialBody("Venus");
        this.earth = new celestialBody("Earth");
        this.moon = new celestialBody("Moon");
        this.mars = new celestialBody("Mars");
        this.jupiter = new celestialBody("Jupiter");
        this.saturn = new celestialBody("Saturn");
        this.titan = new celestialBody("Titan");
        this.neptune = new celestialBody("Neptune");
        this.uranus = new celestialBody("Uranus");

        // specify center of the system as center of the sun
        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - 200) / 2.0f ,
                (Gdx.graphics.getHeight() - 200) / 2.0f, 0);
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

        //  adding the Sun
        game.shape.setColor(Color.YELLOW);
        game.shape.ellipse(centerScreenCords.x, centerScreenCords.y, 218, 218);

        // adding Mercury
        game.shape.setColor(Color.valueOf("#C0C2C9"));
        game.shape.ellipse((float) (centerScreenCords.x + (mercury.getLocation().x / scalingFactor)), (float) (centerScreenCords.y + (mercury.getLocation().y / scalingFactor)), 12, 12);

        // adding Venus
        game.shape.setColor(Color.valueOf("#f7e4ab"));
        game.shape.ellipse((float) (centerScreenCords.x + (venus.getLocation().x / scalingFactor)), (float) (centerScreenCords.y + (venus.getLocation().y / scalingFactor)), 30, 30);

        // adding Earth
        game.shape.setColor(Color.BLUE);
        game.shape.ellipse((float) (centerScreenCords.x + (earth.getLocation().x / scalingFactor)), (float) (centerScreenCords.y + (earth.getLocation().y / scalingFactor)), 32, 32);

        // adding Moon
        game.shape.setColor(Color.WHITE);
        game.shape.ellipse((float) (centerScreenCords.x + (moon.getLocation().x / scalingFactor)), (float) (centerScreenCords.y + (moon.getLocation().y / scalingFactor)), 8, 8);

        // adding Mars
        game.shape.setColor(Color.valueOf("#D6723B"));
        game.shape.ellipse((float) (centerScreenCords.x + (mars.getLocation().x / scalingFactor)), (float) (centerScreenCords.y + (mars.getLocation().y / scalingFactor)), 17, 17);

        // adding Jupiter(this thing is huge)
        game.shape.setColor(Color.valueOf("#c99039"));
        game.shape.ellipse((float) (centerScreenCords.x + (jupiter.getLocation().x / scalingFactor)), (float) (centerScreenCords.y + (jupiter.getLocation().y / scalingFactor)), 351, 351);

        // adding Saturn
        game.shape.setColor(Color.valueOf("#ae8b0c"));
        game.shape.ellipse((float) (centerScreenCords.x + (saturn.getLocation().x / scalingFactor)), (float) (centerScreenCords.y + (saturn.getLocation().y / scalingFactor)), 292, 292);

        // adding Titan
        game.shape.setColor(Color.valueOf("#f2a900"));
        game.shape.ellipse((float) (centerScreenCords.x + (titan.getLocation().x / scalingFactor)), (float) (centerScreenCords.y + (titan.getLocation().y / scalingFactor)), 13, 13);

        // adding Uranus
        game.shape.setColor(Color.valueOf("#d1e7e7"));
        game.shape.ellipse((float) (centerScreenCords.x + (uranus.getLocation().x / scalingFactor)), (float) (centerScreenCords.y + (uranus.getLocation().y / scalingFactor)), 128, 128);

        // adding Neptune
        game.shape.setColor(Color.valueOf("#5b5ddf"));
        game.shape.ellipse((float) (centerScreenCords.x + (neptune.getLocation().x / scalingFactor)), (float) (centerScreenCords.y + (neptune.getLocation().y / scalingFactor)), 128, 128);


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
//        earth.getLocation().set(earth.getLocation().multiply(1.0 / scalingFactor));
//        System.out.println(earth.getLocation().multiply(1.0 / scalingFactor));
//        game.batch.draw(earth.getTexture(), (float) (centerScreenCords.x + earth.getLocation().x / scalingFactor), (float) (centerScreenCords.y + earth.getLocation().y / scalingFactor), earth.getTexture().getWidth(), earth.getTexture().getHeight());
//        game.batch.draw(moon.getTexture(), (float) (centerScreenCords.x + moon.getLocation().x / scalingFactor), (float) (centerScreenCords.y + moon.getLocation().y / scalingFactor), moon.getTexture().getWidth(), moon.getTexture().getHeight());

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
        sun.getTexture().dispose();
        mercury.getTexture().dispose();
        venus.getTexture().dispose();
        earth.getTexture().dispose();
        moon.getTexture().dispose();
        mars.getTexture().dispose();
        jupiter.getTexture().dispose();
        saturn.getTexture().dispose();
        titan.getTexture().dispose();
    }
}

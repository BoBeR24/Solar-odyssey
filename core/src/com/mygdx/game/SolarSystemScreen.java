package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    


    public SolarSystemScreen(final Odyssey game) {
        this.game = game;

        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080); // create a camera

        this.sun = new celestialBody("Sun"); // add all bodies here
        this.mercury = new celestialBody("Mercury");
        this.venus = new celestialBody("Venus");
        this.earth = new celestialBody("Earth");
        this.moon = new celestialBody("Moon");
        this.mars = new celestialBody("Mars");
        this.jupiter = new celestialBody("Jupiter");
        this.saturn = new celestialBody("Saturn");
        this.titan = new celestialBody("Titan");

        // specify center of the system as center of the sun
        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - sun.getTexture().getWidth()) / 2.0f ,
                (Gdx.graphics.getHeight() - sun.getTexture().getHeight()) / 2.0f, 0);
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
        game.batch.begin();

        game.batch.draw(sun.getTexture(), centerScreenCords.x, centerScreenCords.y, sun.getTexture().getWidth(), sun.getTexture().getHeight());
        game.batch.draw(mercury.getTexture(), centerScreenCords.x + (float)mercury.getLocation().x, centerScreenCords.y + (float)mercury.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());
        game.batch.draw(venus.getTexture(), centerScreenCords.x + (float)venus.getLocation().x, centerScreenCords.y + (float)venus.getLocation().y, sun.getTexture().getWidth(), sun.getTexture().getHeight());
        game.batch.draw(earth.getTexture(), centerScreenCords.x + (float)earth.getLocation().x, centerScreenCords.y + (float)earth.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());
        game.batch.draw(moon.getTexture(), centerScreenCords.x + (float)moon.getLocation().x, centerScreenCords.y + (float)moon.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());
        game.batch.draw(mars.getTexture(), centerScreenCords.x + (float)mars.getLocation().x, centerScreenCords.y + (float)mars.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());
        game.batch.draw(jupiter.getTexture(), centerScreenCords.x + (float)jupiter.getLocation().x, centerScreenCords.y + (float)jupiter.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());
        game.batch.draw(saturn.getTexture(), centerScreenCords.x + (float)saturn.getLocation().x, centerScreenCords.y + (float)saturn.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());
        game.batch.draw(titan.getTexture(), centerScreenCords.x + (float)titan.getLocation().x, centerScreenCords.y + (float)titan.getLocation().y,sun.getTexture().getWidth(), sun.getTexture().getHeight());

        game.batch.end();
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

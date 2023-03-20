package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class SolarSystemScreen implements Screen {

    final Odyssey game;
    OrthographicCamera camera;
    private final Vector3 centerScreenCords;


    final celestialBody sun;


    public SolarSystemScreen(final Odyssey game) {
        this.game = game;

        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 1024);

        this.sun = new celestialBody("Sun");

        // specify center of the system as center of the sun
        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - sun.getTexture().getWidth()) / 2.0f ,
                (Gdx.graphics.getHeight() - sun.getTexture().getHeight()) / 2.0f, 0);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();

        game.batch.draw(sun.getTexture(), centerScreenCords.x, centerScreenCords.y, sun.getTexture().getWidth(), sun.getTexture().getHeight());

        game.batch.end();
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

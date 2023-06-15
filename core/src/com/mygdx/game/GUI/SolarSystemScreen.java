package com.mygdx.game.GUI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameLogic.SimulationLogic;
import com.mygdx.game.GameLogic.State;
import com.mygdx.game.PhysicsEngine.PhysicsUtils;


/**
 * Screen in which solar system simulation happens
 * */
public class SolarSystemScreen implements Screen {

    private final Odyssey game;
    public static final OrthographicCamera camera = new OrthographicCamera();
    private final SimulationLogic logic;
    public static State state = State.RUNNING;
    private SpriteBatch batch;
    private BitmapFont font;
    private String date;
    private Clock clock;
    private float labelX;
    private float labelY;
    DateTimeFormatter FORMATTER;




    public SolarSystemScreen(final Odyssey game) {
        this.game = game;

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // create a camera
        camera.zoom = 2f;
        camera.update(); // update camera

        game.shape.setProjectionMatrix(camera.combined);

        this.logic = new SimulationLogic(game); // initialize our simulation
        batch = new SpriteBatch();
        font = new BitmapFont(); 
        float fontScale = 2f; 
        labelX = Gdx.graphics.getWidth() - 500; 
        labelY = Gdx.graphics.getHeight() - 100;
        font.getData().setScale(fontScale);
        clock = new Clock(10, 4, 2023); // Initialize the Clock with the desired starting date
        FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date="Date of launch: April 10th";
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

        logic.moveCameraToProbe(camera); // if you want to make camera follow the probe - uncomment this
        game.shape.setProjectionMatrix(camera.combined);

        game.shape.begin(ShapeType.Filled);


        batch.begin();
        font.draw(batch, date, labelX, labelY); 

        //timepassed=timepassed+PhysicsUtils.STEPSIZE;
        clock.updateTime(PhysicsUtils.STEPSIZE); 

        LocalDateTime currentTime = clock.getDate();
        String formattedTime = currentTime.format(FORMATTER);

        font.draw(batch, formattedTime, labelX, labelY-50); 
        // font.draw(batch, Integer.toString(clock.get), labelX, labelY-50); 
        font.draw(batch,"Days passed: " + Integer.toString(clock.getDaysPassed()), labelX, labelY-150);
        font.draw(batch,Integer.toString(clock.getHours()) + ":" + Integer.toString(clock.getMinutes()) + ":" + Integer.toString(clock.getSeconds()), labelX, labelY-100);
        
        batch.end();
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
        batch.dispose();
        font.dispose();
        logic.close();
    }
}

package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GUI.Odyssey;
import com.mygdx.game.GUI.SolarSystemScreen;
import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Objects.celestialBody;
import com.mygdx.game.PhysicsEngine.Function;
import com.mygdx.game.PhysicsEngine.PhysicsUtils;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;
import com.mygdx.game.Solvers.Solver;
import com.mygdx.game.SupportiveClasses.Timer;

public class LandingLogic {
    private Odyssey game;
    private final int distFactor = SolarSystem.DIST_FACTOR; // pre-calculated scaling factor
    private final Vector3 centerScreenCords;

    //31536000 seconds in 1 year
    private final Timer timer;
    // Variable which contains function to use in further calculations
    private Function function;
    private Solver solver;
    private double minTitanDistance = Double.MAX_VALUE;
    int counter = 0;
    public LandingLogic(Game game){
        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - 200) / 2.0f ,
                (Gdx.graphics.getHeight() - 200) / 2.0f, 0);

        this.timer = new Timer(31536000, PhysicsUtils.STEPSIZE); // set up timer for 2 years by default

    }

    /**
     * updates the current state of the simulation. Draws all objects
     * */
    public void update(){


    }

    /** apply previously calculated states to all objects at the same time(to be sure that all
     calculations happen in one state)
     * */
    private void applyNewState() {
        for (Body body : SolarSystem.bodies) {
            body.update();
        }
    }

    /** redraws all sprites and objects
     * */
    private void redrawScene() {
        // for all bodies except the last one(which is probe)
        for (int i = 0; i < SolarSystem.bodies.size() - 1; i++) {
            celestialBody planet = (celestialBody) SolarSystem.bodies.get(i);
            game.shape.setColor(planet.getColor());
            game.shape.ellipse((float) (centerScreenCords.x + (planet.getLocation().x / distFactor) - (planet.getWidth() / 2)), (float) (centerScreenCords.y + (planet.getLocation().y / distFactor) - (planet.getHeight()) / 2), planet.getWidth(), planet.getHeight());
        }

        game.shape.setColor(Color.VIOLET);
        game.shape.ellipse((float) (centerScreenCords.x + (SolarSystem.probe.getLocation().x / distFactor) - 5),
                (float) (centerScreenCords.y + (SolarSystem.probe.getLocation().y / distFactor) - 5),
                10, 10);

    }

    /** method to keep camera centered at the first probe position(so camera follows the first probe)
     * */
    public void moveCameraToProbe(OrthographicCamera camera){
        if (SolarSystem.probe != null) {
            Vector toFollow = SolarSystem.bodies.get(SystemProperties.PROBE).getLocation(); // our custom vector
            Vector3 toFollow_gdx = new Vector3(centerScreenCords.x + (float) (toFollow.x / distFactor), centerScreenCords.y + (float) (toFollow.y / distFactor), 0);

            camera.position.set(toFollow_gdx);
            camera.update();
        }

    }

    /** method to pause the simulation by switching current state of the game to paused
     * */
    public void pause() {
        SolarSystemScreen.state = State.PAUSED;
    }

    /** method to unpause the simulation by switching current state of the game to running
     * */
    public void unpause() {
        SolarSystemScreen.state = State.RUNNING;
    }

    /**
     * method for disposing all visual elements(don't have use for now)
     * */
    public void close(){
        System.out.println("Thank you");
    }
}

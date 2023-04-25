package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * class which contains all logics for simulation running process
 * */
public class SimulationLogic {
    private Odyssey game;
    private final int scaleFactor = SolarSystem.DIST_FACTOR; // pre-calculated scaling factor
    private final Vector3 centerScreenCords;
//    private int timeCounter = 0;
//    private int timeDesired = 31536000;
    //31536000 seconds in 1 year
    private Timer timer;
    private int range = 0;
    private Probe best_Probe;
    private double minTitanDistance = Double.MAX_VALUE;


    public SimulationLogic(final Odyssey game) {
        this.game = game;
        this.timer = new Timer(31536000); // set up timer for 1 year by default
//        this.timer = new Timer(90); // set up timer for 1 year by default

//        int temp = timeDesired / PhysicsUtils.STEPSIZE;
                //Calculates range in case the step size is not a multiple of the point in time
//        range = timeDesired - PhysicsUtils.STEPSIZE * temp; (do we need this range approach?)

        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - 200) / 2.0f ,
                (Gdx.graphics.getHeight() - 200) / 2.0f, 0);
    }

    /**
     * updates the current state of the simulation. Draws all objects
     * */
    public void update(){
        for (int i = 0; i < 600; i++) { // amount of calculations per frame(to speed up the simulation)
            // Determines what happens when the Solar System is PAUSED or RUNNING
            switch (SolarSystemScreen.state) {

                case RUNNING:
//                    timeCounter += PhysicsUtils.STEPSIZE;
                    timer.iterate(PhysicsUtils.STEPSIZE);

                    for (celestialBody planet : SolarSystem.planets) { // first update positions and velocities for planet and save them to temp arrays
                        PhysicsUtils.calculateNextState(planet);
                    }

                    for (Probe probe : SolarSystem.probes) { // calculates next positions for probes
                        PhysicsUtils.calculateNextState(probe);

                        // I still don't like this part, so if someone have any ideas how to make it better
                        // please enlighten me
                        if (probe.getDistanceToTitan() < minTitanDistance) {
                            minTitanDistance = probe.getDistanceToTitan(); // updates best distance to titan so far
                            best_Probe = probe; // saves probe which reached best distance to the Titan
                        }
                    }

//                    if ((timeCounter == timeDesired) || (timeCounter + range == timeDesired) || (timeCounter - range == timeDesired)) {
                    // Pauses when point in time is reached and displays information about probe
                    if (timer.isTimeReached() || best_Probe.isTitanReached()) {
                        System.out.println("Time taken(in seconds): " + timer.getTimePassed());
                        System.out.println("Minimal distance to Titan center " + minTitanDistance);
                        best_Probe.displayData();
                        pause();
                    }

                    applyNewState(); // update states of objects

                default:
                    break;

            }
        }

        redrawScene(); // redraw all entities of the system

    }

    /** apply previously calculated states to all objects at the same time(to be sure that all
     calculations happen in one state)
     * */
    public void applyNewState() {
        // apply all calculated positions and velocities
        for (celestialBody planet : SolarSystem.planets) {
            planet.setLocation(SystemProperties.coordinates_nextState[planet.getId()]);
            planet.setVelocity(SystemProperties.velocities_nextState[planet.getId()]);
        }
    }


    /** redraws all sprites and objects
     * */
    public void redrawScene() {
        for (celestialBody planet : SolarSystem.planets) {
            game.shape.setColor(planet.getColor());
            game.shape.ellipse((float) (centerScreenCords.x + (planet.getLocation().x / scaleFactor) - (planet.getWidth() / 2)), (float) (centerScreenCords.y + (planet.getLocation().y / scaleFactor) - (planet.getHeight()) / 2), planet.getWidth(), planet.getHeight());
        }

        for (Probe probe : SolarSystem.probes) {
            game.shape.setColor(Color.VIOLET);
            game.shape.ellipse((float) (centerScreenCords.x + (probe.getLocation().x / scaleFactor) - 5),
                    (float) (centerScreenCords.y + (probe.getLocation().y / scaleFactor) - 5),
                    10, 10);
        }
    }

    /** method to keep camera centered at the first probe position(so camera follows the first probe)
     * */
    public void moveCameraToProbe(OrthographicCamera camera){
        Vector toFollow = SolarSystem.probes.get(0).getLocation(); // our custom vector
        Vector3 toFollow_gdx = new Vector3(centerScreenCords.x + (float) (toFollow.x / scaleFactor), centerScreenCords.y + (float) (toFollow.y / scaleFactor), 0);

        camera.position.set(toFollow_gdx);
        camera.update();
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

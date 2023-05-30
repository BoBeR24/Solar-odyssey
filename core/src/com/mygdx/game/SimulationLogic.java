package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.solvers.EnhancedEuler;
import com.mygdx.game.solvers.EulerSolver;
import com.mygdx.game.solvers.RK4;

/**
 * class which contains all logics for simulation running process
 * */
public class SimulationLogic {
    private Odyssey game;
    private final int distFactor = SolarSystem.DIST_FACTOR; // pre-calculated scaling factor
    private final Vector3 centerScreenCords;

    //31536000 seconds in 1 year
    private Timer timer;
    private double minTitanDistance = Double.MAX_VALUE;


    public SimulationLogic(final Odyssey game) {
        this.game = game;
        this.timer = new Timer(31536000); // set up timer for 1 year by default

        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - 200) / 2.0f ,
                (Gdx.graphics.getHeight() - 200) / 2.0f, 0);

        // You can specify path to the file to read. By default it is set to be path to "values.txt"
        DataReader dataReader = new DataReader();
        dataReader.read();

        SystemInitializer.fillSystemWithPlanets(); // adds planets and the Sun to the system

        ProbeLauncher.launch(new Vector(41.2384, -15.006862175, -3.183)); // probe that hits titan(it doesn't)

    }

    /**
     * updates the current state of the simulation. Draws all objects
     * */
    public void update(){
        for (int i = 0; i < 600; i++) { // amount of calculations per frame(to speed up the simulation)
            // Determines what happens when the Solar System is PAUSED or RUNNING
            switch (SolarSystemScreen.state) {

                case RUNNING:
                    timer.iterate(PhysicsUtils.STEPSIZE);
//                    RK4.calculateNextState(SolarSystem.bodies);
//                    EnhancedEuler.calculateNextState(SolarSystem.bodies);
                    EulerSolver.calculateNextState(SolarSystem.bodies);

                    // I still don't like this part, so if someone have any ideas how to make it better
                    // please enlighten me

                    // Pauses when point in time is reached and displays information about probe

                    applyNewState(); // update states of objects

                    if (SolarSystem.probe.getDistanceToTitan() < minTitanDistance) {
                        minTitanDistance = SolarSystem.probe.getDistanceToTitan(); // updates best distance to titan so far
                    }

                    if (timer.isTimeReached() || (SolarSystem.probe.isTitanReached())) {
                        SolarSystem.probe.displayData();
                        System.out.println("Minimal distance to Titan during whole simulation: " + minTitanDistance);

//                        System.out.println("Percentage Error: " + PhysicsUtils.relativeError(SolarSystem.bodies.get(SystemProperties.EARTH).getLocation()));
                        pause();
                    }


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
        for (Body body : SolarSystem.bodies) {
            body.update();
        }
    }


    /** redraws all sprites and objects
     * */
    public void redrawScene() {
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
            Vector toFollow = SolarSystem.bodies.get(SystemProperties.SUN).getLocation(); // our custom vector
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

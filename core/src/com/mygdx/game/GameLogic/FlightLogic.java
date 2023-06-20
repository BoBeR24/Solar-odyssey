package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GUI.LandingScreen;
import com.mygdx.game.GUI.Odyssey;
import com.mygdx.game.GUI.SolarSystemScreen;
import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Objects.celestialBody;
import com.mygdx.game.PhysicsEngine.CameraUtils;
import com.mygdx.game.PhysicsEngine.Function;
import com.mygdx.game.PhysicsEngine.NewtonForce;
import com.mygdx.game.PhysicsEngine.PhysicsUtils;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;
import com.mygdx.game.Solvers.Solver;
import com.mygdx.game.SupportiveClasses.DataReader;
import com.mygdx.game.SupportiveClasses.Timer;
import com.mygdx.game.Solvers.RK4;

/**
 * class which contains all logics for simulation running process
 * */
public class FlightLogic {
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


    public FlightLogic(final Odyssey game) {
        this.game = game;
        this.timer = new Timer(31536000 * 2, PhysicsUtils.STEPSIZE); // set up timer for 2 years by default

        this.centerScreenCords = SolarSystemScreen.centerScreenCords;

        // You can specify path to the file to read. By default, it is set to be path to "values.txt"
        DataReader dataReader = new DataReader();
        dataReader.read();

        SystemInitializer.fillSystemWithPlanets(); // adds planets and the Sun to the system

        ProbeLauncher.launchProbe(new Vector(41.2384, -15.006862175, -3.183)); // initialize probe launch

        // Choose a function we are going to use
        this.function = new NewtonForce();

        // Choose a solver we want to use
        this.solver = new RK4();
//        this.solver = new EnhancedEuler();
//        this.solver = new EulerSolver();

        // Set up hill climbing algorithm
        HillClimbing.timer = this.timer;
        HillClimbing.probe = SolarSystem.probe;

    }

    /**
     * updates the current state of the simulation. Draws all objects
     * */
    public void update(){
        for (int i = 0; i < 600; i++) { // amount of calculations per frame(to speed up the simulation)
            // Determines what happens when the Solar System is PAUSED or RUNNING
            switch (SolarSystemScreen.state) {

                case RUNNING:
//                    if (counter >= 6) {
//                        pause();
//                    }
//                    counter++;
//                    System.out.println(SolarSystem.bodies.get(SystemProperties.EARTH).getLocation());

                    solver.calculateNextState(SolarSystem.bodies, this.function, timer.getTimePassed(), PhysicsUtils.STEPSIZE);

                    HillClimbing.hillClimb();

                    applyNewState(); // update states of objects
//                    System.out.println(SolarSystem.bodies.get(SystemProperties.EARTH).getLocation());

                    if (SolarSystem.probe.getDistanceToTitan() < minTitanDistance) {
                        minTitanDistance = SolarSystem.probe.getDistanceToTitan(); // updates best distance to titan so far
                    }

                    // Pauses when point in time is reached and displays information about probe
                    if (timer.isTimeReached() || (SolarSystem.probe.isTitanReached())) {
                        SolarSystem.probe.displayData();
                        System.out.println("Minimal distance to Titan during whole simulation: " + minTitanDistance);

//                        System.out.println("Percentage Error: " + PhysicsUtils.relativeError(SolarSystem.bodies.get(SystemProperties.EARTH).getLocation()));
                        this.game.getScreen().pause();
                    }

                    if (HillClimbing.isOnTitanOrbit) {
                        System.out.println("Orbit has been reached. Switching to landing scene");

                        this.game.setScreen(new LandingScreen(this.game));
                    }

                    HillClimbing.hasCompletedIteration = true;

                default:
                    break;

            }
        }

        redrawScene(); // redraw all entities of the system

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

    private void switchScene() {

    }

    /**
     * method for disposing all visual elements(don't have use for now)
     * */
    public void close(){
        System.out.println("Thank you");
    }
}

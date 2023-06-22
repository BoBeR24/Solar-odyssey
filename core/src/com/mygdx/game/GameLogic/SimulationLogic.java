package com.mygdx.game.GameLogic;

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
public class SimulationLogic {
    private Odyssey game;
    private final int distFactor = SolarSystem.DIST_FACTOR; // pre-calculated scaling factor
    private final Vector3 centerScreenCords;
    private final float TIME;

    private final int SECONDS_IN_YEAR = 31536000;
    private final Timer timer;
    // Variable which contains function to use in further calculations
    private Function function;
    private Solver solver;
    private double minTitanDistance = Double.MAX_VALUE;


    public SimulationLogic(final Odyssey game) {
        this.game = game;
        this.TIME = SECONDS_IN_YEAR * 2;
        this.timer = new Timer(TIME * 2, PhysicsUtils.STEPSIZE); // set up timer for 2 years by default

        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - 200) / 2.0f ,
                (Gdx.graphics.getHeight() - 200) / 2.0f, 0);

        // You can specify path to the file to read. By default, it is set to be path to "values.txt"
        DataReader dataReader = new DataReader();
        dataReader.read();

        SystemInitializer.fillSystemWithPlanets(); // adds planets and the Sun to the system

        ProbeLauncher.launch(new Vector(0, 0, 0)); // initialize probe launch

        // Choose a function we are going to use
        this.function = new NewtonForce();

        // Choose a solver we want to use
        this.solver = new RK4();

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

                    solver.calculateNextState(SolarSystem.bodies, this.function, timer.getTimePassed());
                    // EnhancedEuler.calculateNextState(SolarSystem.bodies);
                //    EulerSolver.calculateNextState(SolarSystem.bodies);


                    HillClimbing.hillClimb();
                    timer.iterate();


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

    public void resetTimer(){
        timer.reset();
    }
}

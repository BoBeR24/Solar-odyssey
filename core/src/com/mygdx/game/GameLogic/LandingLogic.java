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
import com.mygdx.game.PhysicsEngine.NewtonForce;
import com.mygdx.game.PhysicsEngine.PhysicsUtils;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;
import com.mygdx.game.Solvers.RK4;
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

    public LandingLogic(Odyssey game){
        this.game = game;

        this.timer = new Timer(31536000, PhysicsUtils.STEPSIZE); // set up timer for 1 year by default

        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - 200) / 2.0f ,
                (Gdx.graphics.getHeight() - 200) / 2.0f, 0);

        SolarSystem.resetSystem();

        // add only a titan to list of bodies(as landing scene only considers rocket and Titan)
        Body titan = new celestialBody(SystemProperties.TITAN);

        SolarSystem.bodies.add(titan);

        ProbeLauncher.launchLandingModule(new Vector(0.0, 0.0, 0.0), new Vector(0.0, 0.0, 0.0), 2000); // initialize probe launch

         this.function = new NewtonForce();

        // Choose a solver we want to use
        this.solver = new RK4();
//        this.solver = new EnhancedEuler();
//        this.solver = new EulerSolver();
    }

    /**
     * updates the current state of the simulation. Draws all objects
     * */
    public void update(){
        //TODO decide how to represent landing pad in our code

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

    /**
     * method for disposing all visual elements(don't have use for now)
     * */
    public void close(){
        System.out.println("Thank you");
    }
}

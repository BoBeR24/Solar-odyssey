package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GUI.LandingScreen;
import com.mygdx.game.GUI.Odyssey;
import com.mygdx.game.GUI.SolarSystemScreen;
import com.mygdx.game.Objects.*;
import com.mygdx.game.PhysicsEngine.Function;
import com.mygdx.game.PhysicsEngine.NewtonForce;
import com.mygdx.game.PhysicsEngine.PhysicsUtils;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;
import com.mygdx.game.Solvers.RK4;
import com.mygdx.game.Solvers.Solver;
import com.mygdx.game.SupportiveClasses.DataReader;
import com.mygdx.game.SupportiveClasses.Timer;

import java.io.IOException;

public class LandingLogic {
    private Odyssey game;
    private final int distFactor;
    private final int sizeFactor;
    private final Vector3 centerScreenCords;

    //31536000 seconds in 1 year
    private final Timer timer;
    // Variable which contains function to use in further calculations
    private final DataReader dataReader;
    private double counter;
    private Function function;
    private Solver solver;

    public LandingLogic(Odyssey game){
        this.game = game;

        this.timer = new Timer(31536000, PhysicsUtils.STEPSIZE); // set up timer for 1 year by default

        this.centerScreenCords = LandingScreen.centerScreenCords;

        SolarSystem.DIST_FACTOR = 2;
        distFactor = SolarSystem.DIST_FACTOR;

        SolarSystem.SIZE_FACTOR = 2;
        sizeFactor = SolarSystem.SIZE_FACTOR;

        SolarSystem.resetSystem();

        // add landing pad
        SolarSystem.bodies.add(new LandingPad());

        // add only titan to list of bodies(as landing scene only considers landing module, landing pad and Titan)
        SolarSystem.bodies.add(new Titan());

        ProbeLauncher.launchLandingModule(new Vector(0.0, 0.0, 0.0), new Vector(0.0, 0.0, 0.0), 2000); // initialize probe launch

        dataReader = new DataReader();
//        this.function = new NewtonForce();
//
//        Choose a solver we want to use
//        this.solver = new RK4();
//        this.solver = new EnhancedEuler();
//        this.solver = new EulerSolver();
    }

    /**
     * updates the current state of the simulation. Draws all objects
     * */
    public void update(){
        for (int i = 0; i < 50; i++) {

            switch (LandingScreen.state) {
                case RUNNING:
                    try {
                        Vector nextMove = dataReader.readLandingMovement();
                        // in landing scene everything is considered to be in 2D, so we always set z = 0
                        SolarSystem.landingModule.setLocation(nextMove.x, nextMove.y, 0.0);

                        // z axis of a nextMove vector is used to store rotation
                        SolarSystem.landingModule.setRotation(nextMove.z);

                    } catch (IOException e) {
                        this.game.getScreen().pause();
                        System.out.println("Simulation has ended");
                    }

                default:
                    break;
            }
        }

        redrawScene();
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
        // draw landing pad
        game.shape.setColor(Color.RED);
        game.shape.rect((float) (centerScreenCords.x + (SolarSystem.bodies.get(0).getLocation().x / distFactor) -
                        ((LandingPad) SolarSystem.bodies.get(0)).getWidth() / sizeFactor / 2.0f),
                (float) (centerScreenCords.y + (SolarSystem.bodies.get(0).getLocation().y / distFactor) -
                        ((LandingPad) SolarSystem.bodies.get(0)).getHeight() / sizeFactor / 2.0f),
                (float) ((LandingPad) SolarSystem.bodies.get(0)).getWidth() / sizeFactor, (float) ((LandingPad) SolarSystem.bodies.get(0)).getHeight() / sizeFactor);

        // draw Titan
        game.shape.setColor(Color.valueOf("#f2a900"));
        game.shape.ellipse((float) (centerScreenCords.x + (SolarSystem.bodies.get(1).getLocation().x / distFactor) -
                        ((Titan) SolarSystem.bodies.get(1)).getWidth() / sizeFactor / 2.0f),
                (float) (centerScreenCords.y + (SolarSystem.bodies.get(1).getLocation().y / distFactor) - ((Titan) SolarSystem.bodies.get(1)).getHeight() / sizeFactor / 2.0f),
                (float) ((Titan) SolarSystem.bodies.get(1)).getWidth() / sizeFactor, (float) ((Titan) SolarSystem.bodies.get(1)).getHeight() / sizeFactor);

        // draw landing module
        game.shape.setColor(Color.PINK);
        game.shape.ellipse((float) (centerScreenCords.x + (SolarSystem.landingModule.getLocation().x / distFactor) -
                        SolarSystem.landingModule.getWidth() / sizeFactor / 2.0f),
                (float) (centerScreenCords.y + (SolarSystem.landingModule.getLocation().y / distFactor) -
                        SolarSystem.landingModule.getHeight() / sizeFactor / 2.0f),
                (float) SolarSystem.landingModule.getWidth() / sizeFactor, (float) SolarSystem.landingModule.getHeight() / sizeFactor);

        // identify location of antenna(to visualize direction of the rocket)
        Vector antenna = new Vector(0.0, 40.0, 0.0);
//        antenna.y = antenna.y + 40;
//        System.out.println(antenna + " a");

        antenna.rotate(SolarSystem.landingModule.getRotation(), 'z');
//        System.out.println(antenna + " b");
        antenna = antenna.add(SolarSystem.landingModule.getLocation());

//        System.out.println(SolarSystem.landingModule.getRotation());

        game.shape.setColor(Color.YELLOW);
        game.shape.ellipse((float) (centerScreenCords.x + (antenna.x / distFactor) -
                        40 / sizeFactor / 2.0f),
                (float) (centerScreenCords.y + (antenna.y / distFactor) -
                        40 / sizeFactor / 2.0f),
                (float) 40 / sizeFactor, (float) 40 / sizeFactor);
    }

    /**
     * method for disposing all visual elements(don't have use for now)
     * */
    public void close(){
        System.out.println("Thank you");
    }
}

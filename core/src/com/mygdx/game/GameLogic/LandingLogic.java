package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GUI.InfoLabel;
import com.mygdx.game.GUI.LandingScreen;
import com.mygdx.game.GUI.Odyssey;
import com.mygdx.game.Objects.*;
import com.mygdx.game.PhysicsEngine.Controller;
import com.mygdx.game.PhysicsEngine.Function;
import com.mygdx.game.PhysicsEngine.PhysicsUtils;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Solvers.Solver;
import com.mygdx.game.SupportiveClasses.DataReader;
import com.mygdx.game.SupportiveClasses.Timer;

import java.io.IOException;

public class LandingLogic {
    private final float distFactor;
    private final float sizeFactor;
    private final Vector3 centerScreenCords;
    private final InfoLabel label;
    private final Timer timer;
    // Variable which contains function to use in further calculations
    private final DataReader dataReader;
    private Odyssey game;


    public LandingLogic(Odyssey game) {
        this.game = game;

        dataReader = new DataReader();
        // we still need to execute readFlightScene to get radii and masses of objects(e.g. for Titan)
        dataReader.readFlightScene();

        this.timer = new Timer(31536000, 1, 2023, 10, 2); // set up timer for 1 year by default

        this.centerScreenCords = LandingScreen.centerScreenCords;

        SolarSystem.DIST_FACTOR = 0.35f;
        distFactor = SolarSystem.DIST_FACTOR;

        SolarSystem.SIZE_FACTOR = 0.35f;
        sizeFactor = SolarSystem.SIZE_FACTOR;

        SolarSystem.resetSystem();

        // add only titan to list of bodies(as landing scene only considers landing module, landing pad and Titan)
        SolarSystem.bodies.add(new Titan());

        label = new InfoLabel(game, timer);

        ProbeLauncher.launchLandingModule(new Vector(0.0, 0.0, 0.0), new Vector(0.0, 0.0, 0.0), 2000); // initialize landing module launch

        try {
            Controller.run(timer.stepSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * updates the current state of the simulation. Draws all objects
     */
    public void update() {
        for (int i = 0; i < 5; i++) {

            switch (LandingScreen.state) {
                case RUNNING:
                    try {
                        Vector nextMove = dataReader.readLandingMovement();
                        // in landing scene everything is considered to be in 2D, so we always set z = 0
                        SolarSystem.landingModule.setLocation(nextMove.x, nextMove.y, 0.0);

                        // z axis of a nextMove vector is used to store rotation
                        SolarSystem.landingModule.setRotation(nextMove.z);
                        timer.iterate();

                    } catch (IOException e) {
                        // if dataReader returns an error means file is corrupted or there is no more lines to read in it.
                        // Either way we end our simulation after that
                        this.game.getScreen().pause();
                        System.out.println("Simulation has ended");
                    }

                default:
                    break;
            }
        }

        redrawScene();
    }

    /**
     * redraws all sprites and objects
     */
    private void redrawScene() {
        // draw landing pad
        game.shape.setColor(Color.RED);
        game.shape.rect(centerScreenCords.x - 20 / sizeFactor / 2.0f,
                centerScreenCords.y - 4 / sizeFactor / 2.0f - 600,
                20 / sizeFactor, 4 / sizeFactor);

        // draw Titan
        game.shape.setColor(Color.valueOf("#f2a900"));
        game.shape.ellipse((float) (centerScreenCords.x + (SolarSystem.bodies.get(0).getLocation().x / distFactor) -
                        ((Titan) SolarSystem.bodies.get(0)).getWidth() / sizeFactor / 2.0f),
                (float) (centerScreenCords.y + (SolarSystem.bodies.get(0).getLocation().y / distFactor) - ((Titan) SolarSystem.bodies.get(0)).getHeight() / sizeFactor / 2.0f - 600),
                (float) ((Titan) SolarSystem.bodies.get(0)).getWidth() / sizeFactor, (float) ((Titan) SolarSystem.bodies.get(0)).getHeight() / sizeFactor);


        // draw landing module
        game.shape.setColor(Color.PINK);
        game.shape.ellipse((float) (centerScreenCords.x + (SolarSystem.landingModule.getLocation().x / distFactor) -
                        SolarSystem.landingModule.getWidth() / sizeFactor / 2.0f),
                (float) (centerScreenCords.y + (SolarSystem.landingModule.getLocation().y / distFactor) -
                        SolarSystem.landingModule.getHeight() / sizeFactor / 2.0f - 600),
                SolarSystem.landingModule.getWidth() / sizeFactor, SolarSystem.landingModule.getHeight() / sizeFactor);

        // identify location of antenna(to visualize direction of the rocket)
        Vector antenna = new Vector(0.0, SolarSystem.landingModule.getHeight() / 2.0, 0.0);

        antenna.rotate(SolarSystem.landingModule.getRotation(), 'z');
        antenna = antenna.add(SolarSystem.landingModule.getLocation());

        // draw antenna
        game.shape.setColor(Color.YELLOW);
        game.shape.ellipse((float) (centerScreenCords.x + (antenna.x / distFactor) -
                        SolarSystem.landingModule.getHeight() / 2.0 / sizeFactor / 2.0f),
                (float) (centerScreenCords.y + (antenna.y / distFactor) -
                        SolarSystem.landingModule.getHeight() / 2.0 / sizeFactor / 2.0f - 600),
                (float) (SolarSystem.landingModule.getHeight() / 2.0 / sizeFactor), (float) (SolarSystem.landingModule.getHeight() / 2.0 / sizeFactor));

        // draw time label
        label.draw();
    }

    /**
     * method for disposing all visual elements(don't have use for now)
     */
    public void close() {
        System.out.println("Thank you");
    }
}

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
    private boolean titanReached = false;
    private celestialBody titan = new celestialBody("Titan");


    public SimulationLogic(final Odyssey game) {
        this.game = game;

        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - 200) / 2.0f ,
                (Gdx.graphics.getHeight() - 200) / 2.0f, 0);

        for (celestialBody planet : SolarSystem.planets) {
            if (planet.getName().equals("Titan")) {
                this.titan = planet;
                break;
            }
        }
    }

    /**
     * updates the current state of the simulation. Draws all objects
     * */
    public void update(){

        for (celestialBody planet : SolarSystem.planets) { // updates positions for planets
            PhysicsUtils.updateBody(planet);
        }

//        int counter = 0;
//        for (Probe probe : SolarSystem.probes) { // updates positions for probes
////            PhysicsUtils.updateBody(probe);
//            counter++;
//            if (counter == 2) {
//                System.out.println((centerScreenCords.x + (probe.getLocation().x / scaleFactor)));
//                System.out.println((centerScreenCords.y + (probe.getLocation().y / scaleFactor)));
////                System.out.println(probe.getLocation().x);
//            }
//
//            game.shape.setColor(Color.VIOLET);
//            game.shape.ellipse((float) (centerScreenCords.x + (probe.getLocation().x / scaleFactor) - (10 / 2)),
//                    (float) (centerScreenCords.y + (probe.getLocation().y / scaleFactor) - (10 / 2)),
//                    250, 250);
//        }
//
//        switch (Launch.BunchLaunchPaths(SolarSystem.probes, titan)) {
//            case 0:
//                break;
//
//            case 1:
//                System.out.println("fucking yes");
//                break;
//
//            case 2:
//                System.out.println("fucking no");
//                break;
//        }


        for (celestialBody body : SolarSystem.planets) {
            body.getLocation().set(PhysicsUtils.coordinates_nextState[body.getId()]);
            body.getVelocity().set(PhysicsUtils.velocities_nextState[body.getId()]);

            game.shape.setColor(body.getColor());
            game.shape.ellipse((float) (centerScreenCords.x + (body.getLocation().x / scaleFactor) - (body.getWidth() / 2)),
                    (float) (centerScreenCords.y + (body.getLocation().y / scaleFactor) - (body.getHeight()) / 2),
                    body.getWidth(), body.getHeight());
        }

    }

    public void moveCamera(OrthographicCamera camera){
        Vector toFollow = SolarSystem.planets.get(11).getLocation(); // our custom vector
        Vector3 toFollow_gdx = new Vector3(centerScreenCords.x + (float) (toFollow.x / scaleFactor), centerScreenCords.y + (float) (toFollow.y / scaleFactor), 0);

        camera.position.set(toFollow_gdx);
        camera.update();
    }

    /**
     * method for disposing all visual elements(don't have use for now)
     * */
    public void close(){
        System.out.println("Thank you");
    }
}

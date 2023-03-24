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
    private int counter = 0;
    private int timeDesired = 31536000;
    //31536000 seconds in 1 year
    private int range = 0;
    private boolean titanReached = false;
    private celestialBody titan = new celestialBody("Titan");
    private Probe best_Probe;
    double distance;


    public SimulationLogic(final Odyssey game) {
        this.game = game;

        int temp = timeDesired/PhysicsUtils.STEPSIZE;

        //Calculates range in case the step size is not a multiple of the point in time
        range = timeDesired - PhysicsUtils.STEPSIZE*temp;

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
        for (int i = 0; i < 600; i++) { // amount of calculations per frame(to speed up the simulation)
            //Determines what happens when the Solar System is PAUSED or RUNNING
            switch (SolarSystemScreen.state) {

                case RUNNING:
                    counter += PhysicsUtils.STEPSIZE;

                    for (celestialBody planet : SolarSystem.planets) { // first update positions and velocities for planet and save them to temp arrays
                        PhysicsUtils.updateBody(planet);
                    }

                    for (Probe probe : SolarSystem.probes) { // updates positions for probes
                        PhysicsUtils.updateBody(probe);


                        Vector dist_v = PhysicsUtils.distanceToTitan(probe, titan);
                        double dist = dist_v.magnitude();



                        if (dist <= titan.getRadius() + 300) {
                            titanReached = true;
                            best_Probe = probe; // saves probe which did reach Titan(for testing with multiple probes)
                        }

                    }

                    //Pauses when point in time is reached and displays information about probe
                    if ((counter == timeDesired) || (counter + range == timeDesired) || (counter - range == timeDesired)) {
                        System.out.println("Calender is Same");
                        SolarSystemScreen.state = State.PAUSED;
//                        System.out.println(best_dist);
//                        System.out.println(best_distV + " v");
//                        System.out.println(best_Probe.getVStart() + "start");
                        System.out.println("The Position of the Probe is: " + SolarSystem.probes.get(0).getLocation());
                        System.out.println("The Velocity of the Probe is: " + SolarSystem.probes.get(0).getVelocity());
                    } else if (titanReached) {
                        SolarSystemScreen.state = State.PAUSED;
                        System.out.println("Time taken(in seconds): " + counter);
                        System.out.println("Distance to Titan center: " + distance);
                        System.out.println("The Position of the Probe is: " + best_Probe.getLocation());
                        System.out.println("The Velocity of the Probe is: " + best_Probe.getVelocity());
                        System.out.println("Initial position(relative to the Sun): " + best_Probe.getPStart());
                        System.out.println("Initial velocity(relative to Earth): " + best_Probe.getVStart());
                    }

                default:
                    break;

            }
        }

        redrawScene(); // redraw all entities of the system

    }

    public void moveCamera(OrthographicCamera camera){
        Vector toFollow = SolarSystem.probes.get(0).getLocation(); // our custom vector
        Vector3 toFollow_gdx = new Vector3(centerScreenCords.x + (float) (toFollow.x / scaleFactor), centerScreenCords.y + (float) (toFollow.y / scaleFactor), 0);

        camera.position.set(toFollow_gdx);
        camera.update();
    }

    public void redrawScene() {
        for (celestialBody planet : SolarSystem.planets) {
            // apply all calculated positions and velocities
            planet.getLocation().set(PhysicsUtils.coordinates_nextState[planet.getId()]);
            planet.getVelocity().set(PhysicsUtils.velocities_nextState[planet.getId()]);

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

    /**
     * method for disposing all visual elements(don't have use for now)
     * */
    public void close(){
        System.out.println("Thank you");
    }
}

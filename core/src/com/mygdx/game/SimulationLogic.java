package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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

    public SimulationLogic(final Odyssey game) {
        this.game = game;

        int temp = timeDesired/PhysicsUtils.STEPSIZE;

        //Calculates range in case the step size is not a multiple of the point in time
        range = timeDesired - PhysicsUtils.STEPSIZE*temp;

        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - 200) / 2.0f ,
                (Gdx.graphics.getHeight() - 200) / 2.0f, 0);

    }

    /**
     * updates the current state of the simulation. Draws all objects
     * */
    public void update(){

        //Determines what happens when the Solar System is PAUSED or RUNNING
        switch (SolarSystemScreen.state){

            case RUNNING:
            counter += PhysicsUtils.STEPSIZE;

            //Pauses when point in time is reached and displays information about probe
            if((counter == timeDesired) || (counter+range == timeDesired) || (counter-range == timeDesired)){
                System.out.println("Calender is Same");
                SolarSystemScreen.state = State.PAUSED;
                System.out.println("The Position of the Probe is: (" + SystemProperties.coordinates[11].x + ", " + SystemProperties.coordinates[11].y + ", " + SystemProperties.coordinates[11].z + ")");
                System.out.println("The Velocity of the Probe is: (" + SystemProperties.velocities[11].x + ", " + SystemProperties.velocities[11].y + ", " + SystemProperties.velocities[11].z + ")");
            }

            for (celestialBody body : SolarSystem.bodies) {
                PhysicsUtils.updateBody(body);
            }

            break;

            default:
                break;
        }

        for (celestialBody body : SolarSystem.bodies) {
            body.getLocation().set(PhysicsUtils.coordinates_nextState[body.getId()]);
            body.getVelocity().set(PhysicsUtils.velocities_nextState[body.getId()]);

            game.shape.setColor(body.getColor());
            game.shape.ellipse((float) (centerScreenCords.x + (body.getLocation().x / scaleFactor) - (body.getWidth() / 2)), (float) (centerScreenCords.y + (body.getLocation().y / scaleFactor) - (body.getHeight()) / 2), body.getWidth(), body.getHeight());
        }

    }

    public void moveCamera(OrthographicCamera camera){
        Vector toFollow = SolarSystem.bodies.get(11).getLocation(); // our custom vector
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

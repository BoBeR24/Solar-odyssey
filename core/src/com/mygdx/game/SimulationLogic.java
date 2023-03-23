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


    public SimulationLogic(final Odyssey game) {
        this.game = game;

        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - 200) / 2.0f ,
                (Gdx.graphics.getHeight() - 200) / 2.0f, 0);


    }

    /**
     * updates the current state of the simulation. Draws all objects
     * */
    public void update(){

        for (celestialBody planet : SolarSystem.planets) { // updates positions for planets
            PhysicsUtils.updateBody(planet);
        }

        for (Probe probe : SolarSystem.probes) { // updates positions for probes
            PhysicsUtils.updateBody(probe);
        }

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

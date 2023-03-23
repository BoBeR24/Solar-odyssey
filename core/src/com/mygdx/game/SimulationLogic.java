package com.mygdx.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.plaf.TreeUI;

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
    private final FileWriter fileWriter;
    private static BufferedWriter writer;
    private static boolean closed = false;
    private static boolean[] visited = new boolean[12];


    public SimulationLogic(final Odyssey game) throws IOException {
        this.game = game;
        fileWriter = new FileWriter("core\\src\\com\\mygdx\\game\\Coordinates.txt");
        writer = new BufferedWriter(fileWriter);

        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - 200) / 2.0f ,
                (Gdx.graphics.getHeight() - 200) / 2.0f, 0);

    }

    /**
     * updates the current state of the simulation. Draws all objects
     * @throws IOException
     * */
    public void update() throws IOException{

        for (celestialBody body : SolarSystem.bodies) {
            PhysicsUtils.updateBody(body);
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

    public static void fileclose() throws IOException{
        writer.close();
        closed = true;
    }

    public static void fileupdate(int index) throws IOException{
        boolean allVisit = true;
        String coordinatesX;
        String coordinatesY;
        String coordinatesZ;
        if(closed == false){

            for(int i = 0; i < visited.length; i++){
                if(visited[i] == false)
                    allVisit = false;
            }

            if(allVisit != false){
                writer.newLine();
                visited = new boolean[12];
            }
            coordinatesX = String.valueOf(SystemProperties.coordinates[index].x);
            coordinatesY = String.valueOf(SystemProperties.coordinates[index].y);
            coordinatesZ = String.valueOf(SystemProperties.coordinates[index].z);
            writer.write(coordinatesX + " " + coordinatesY + " " + coordinatesZ + " ");

            visited[index] = true; 
        }
    }
}

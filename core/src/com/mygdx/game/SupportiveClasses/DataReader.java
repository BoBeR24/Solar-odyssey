package com.mygdx.game.SupportiveClasses;


import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.SystemProperties;

import javax.annotation.processing.Filer;
import java.io.*;

public class DataReader {
    private final Vector[] coordinates;
    private final Vector[] velocities;
    private final double[] masses;
    private final int[] radii;
    private final File fileForFlightScene;
    private final File fileForLandingScene;
    private static BufferedReader flightReader;
    private static BufferedReader landingReader;

    /**
     * Class that reads input values from a file
     */
    public DataReader(File forFlightScene, File forLandingScene) {
        this.coordinates = SystemProperties.initCoordinates;
        this.velocities = SystemProperties.initVelocities;
        this.masses = SystemProperties.masses;
        this.radii = SystemProperties.radii;

        this.fileForFlightScene = forFlightScene;
        this.fileForLandingScene = forLandingScene;
    }

    public DataReader() {
        this(new File("C:\\JavaWorkspace\\Odyssey\\core\\src\\com\\mygdx\\game\\SupportiveClasses\\values.txt"),
                new File("C:\\JavaWorkspace\\Odyssey\\core\\src\\com\\mygdx\\game\\SupportiveClasses\\coordinates.txt"));
    }

    /**
     * reads initial state of objects for flight scene
     */
    public void readFlightScene() {

        try {
            flightReader = new BufferedReader(new FileReader(this.fileForFlightScene));
            String line;
            int lineCounter = 0;

            while ((line = flightReader.readLine()) != null) {

                String[] textLine = line.split(" ");

                double one = Double.parseDouble(textLine[1]);
                Vector coordinate = new Vector(one, Double.parseDouble(textLine[2]), Double.parseDouble(textLine[3]));
                Vector velocity = new Vector(Double.parseDouble(textLine[4]), Double.parseDouble(textLine[5]), Double.parseDouble(textLine[6]));

                coordinates[lineCounter] = coordinate;
                velocities[lineCounter] = velocity;
                masses[lineCounter] = Double.parseDouble(textLine[7]);
                radii[lineCounter] = Integer.parseInt(textLine[8]);

                lineCounter++;
            }

            flightReader.close();

            if (lineCounter != 11) {
                throw new IndexOutOfBoundsException();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("Amount of planets is not valid. There should be exactly 11 planets");
        }
    }

    /**
     * works the same way as for Flight scene. This method reads state of all objects in each state.
     * So it reads whole lifecycle of the system
     */
    public Vector readLandingMovement() throws IOException {
        if (landingReader == null) {
            landingReader = new BufferedReader(new FileReader(this.fileForLandingScene));
        }

        if (!landingReader.ready()) {
            landingReader.close();

            throw new IOException();
        }

        String line = landingReader.readLine().trim();

        if (line.isEmpty()) {
            landingReader.close();

            throw new IOException();
        }

        String[] arr = line.split(" ");

        return new Vector(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), Double.parseDouble(arr[2]));
    }
}

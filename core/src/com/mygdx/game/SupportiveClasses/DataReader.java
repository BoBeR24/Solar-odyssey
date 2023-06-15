package com.mygdx.game.SupportiveClasses;


import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.SystemProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {
    public Vector[] coordinates;
    public Vector[] velocities;
    public double[] masses;
    public double[] radii;
    public File file;

    /** Class that reads input values from a file
     * */
    public DataReader(File file) {
        this.coordinates = SystemProperties.initCoordinates;
        this.velocities = SystemProperties.initVelocities;
        this.masses = SystemProperties.masses;
        this.radii = SystemProperties.radii;

        this.file = file;
    }
    public DataReader() {
        this(new File("C:\\Users\\Surface\\Desktop\\MU material\\Project 1-2\\code\\Solar-odyssey\\core\\src\\com\\mygdx\\game\\SupportiveClasses\\values.txt"));
    }

    public void read() {

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int lineCounter = 0;

            while ((line = br.readLine()) != null) {

                String[] textLine = line.split(" ");

                double one = Double.parseDouble(textLine[1]);
                Vector coordinate = new Vector(one, Double.parseDouble(textLine[2]), Double.parseDouble(textLine[3]));
                Vector velocity = new Vector(Double.parseDouble(textLine[4]), Double.parseDouble(textLine[5]), Double.parseDouble(textLine[6]));

                coordinates[lineCounter] = coordinate;
                velocities[lineCounter] = velocity;
                masses[lineCounter] = Double.parseDouble(textLine[7]);
                radii[lineCounter] = Double.parseDouble(textLine[8]);

                lineCounter++;
            }

            br.close();

            if (lineCounter != 11) {
                throw new IndexOutOfBoundsException();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Amount of planets is not valid. There should be exactly 11 planets");
        }
    }
}

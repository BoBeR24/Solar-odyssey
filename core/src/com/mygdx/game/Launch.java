package com.mygdx.game;

import java.util.ArrayList;

public class Launch {
    
    // creates a vector for a probe that describes the velocity at t=0
    public static Vector launch(celestialBody body, Vector relativeV){
        return relativeV.add(body.getVelocity());
    }

    final static double EARTHRADIUS = 6370;
    final static double TITANRADIUS = 2575;

    
    // Launches an amount of probes
    public static ArrayList<Probe> launchBunch(int amount, celestialBody earth, Vector longBoundryP, Vector shortBoundryP, double width, Vector v){

        // making sure that the z factor is set to the same value
        double zValue = longBoundryP.z;
        shortBoundryP.set(shortBoundryP.x, shortBoundryP.y, zValue);

        // making sure that the launching positions are on earth
        if (longBoundryP.magnitude() - EARTHRADIUS != 0){
            longBoundryP.multiply(EARTHRADIUS/longBoundryP.magnitude());
        }

        if (shortBoundryP.magnitude() - EARTHRADIUS != 0){
            shortBoundryP.multiply(EARTHRADIUS/shortBoundryP.magnitude());
        }

        // setting the angles
        double alphaLong = Math.asin(longBoundryP.y / longBoundryP.magnitude());
        double alphaShort = Math.asin(shortBoundryP.y / shortBoundryP.magnitude()) + 2*Math.PI;
        double range = alphaShort - alphaLong; // range between long and short
        double siteRange = range / (amount-1); // range between sites over the x,y plane

//        SystemProperties.probes = new Probe[amount]; // specify amount of probes in the system
        ArrayList<Probe> probes = SolarSystem.probes; // save to local variable

        for (int i = 0; i < amount; i++){

            Probe probe = new Probe();
            // set the x,y

            //randomize the z to the width and compromise with the y
            double random = Math.random() * 2 - 1; // generate number between -1 and 1
            double alphaZ = width * random;
            double z = Math.sin(alphaZ) * EARTHRADIUS;


            Vector position = new Vector(Math.cos(alphaLong + i * siteRange), Math.sin(alphaLong + i * siteRange), z);
            position = position.multiply(EARTHRADIUS / position.magnitude());

            probe.setLocation(position.x, position.y, position.z);
            probe.setPStart(probe.getLocation());

            v = probe.getLocation().multiply((v.magnitude()) / (probe.getLocation().magnitude()));

            probe.setVelocity(launch(earth, v).x, launch(earth, v).y, launch(earth, v).z);
            probe.setVSRART(probe.getVelocity());

            probes.add(probe);
        }

        return probes;
    }

    // keeps track of launched probes and evaluates them.
    // returns a vector if all probes missed, or null of at least one is still getting closer.
    public static Vector[] BunchLaunchPaths(Probe[] probes, celestialBody titan){
        boolean[] isDrifting = new boolean[probes.length];

        for (int i = 0; i < probes.length; i++){
            double distanceOld = probes[i].getdistanceToTitan();
            double distanceCurrent = Math.abs((probes[i].getLocation().add(titan.getLocation().multiply(-1))).magnitude()); // distances to titan

            if (distanceCurrent <= TITANRADIUS + 300){
                System.out.println("TITAN HIT");
                System.out.println("launch location :" + probes[i].getPStart().toString());
                System.out.println("launch velocity :" + probes[i].getVStart().toString());
            }
            else if (distanceCurrent > distanceOld){
                probes[i].setDistanceToTitan(distanceCurrent);
            } else {
                isDrifting[i] = true;
            }
        }

        boolean allAreDrifting = true;
        for (int i = 0; i < isDrifting.length; i++){
            if (!isDrifting[i]){
                allAreDrifting = false;
            }
        }
        if (allAreDrifting){
            return evaluate(probes, titan);
        } else return null;
        
    }

    // evaluate and returns the longbound 
    public static Vector[] evaluate(Probe[] probes, celestialBody titan){
        Probe longBound = null;
        Probe shortBound = null;
        for (int i = 0; i < probes.length; i++){
            if (probes[i].getLocation().magnitude() < titan.getLocation().magnitude()){
                if (shortBound == null || probes[i].getdistanceToTitan() < shortBound.getdistanceToTitan()){
                    shortBound = probes[i];
                }
            } else{
                if (longBound == null || probes[i].getdistanceToTitan() < longBound.getdistanceToTitan()){
                    longBound = probes[i];
                }
            }
        }
        Vector v = new Vector(0, 0, 0);
        v = longBound.getVStart().add(shortBound.getVStart()).multiply(0.5);
        return new Vector[] {longBound.getPStart(), shortBound.getPStart(), v};
    }
}

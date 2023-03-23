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
            longBoundryP = longBoundryP.multiply(EARTHRADIUS/longBoundryP.magnitude());
        }

        if (shortBoundryP.magnitude() - EARTHRADIUS != 0){
            shortBoundryP = shortBoundryP.multiply(EARTHRADIUS/shortBoundryP.magnitude());
//            System.out.println(shortBoundryP.magnitude());
        }

        // setting the angles
        double alphaLong = Math.asin(longBoundryP.y / longBoundryP.magnitude());
        System.out.println(alphaLong + " long");
        double alphaShort = Math.asin(shortBoundryP.y / shortBoundryP.magnitude());
        System.out.println(alphaShort + " short");

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


            Vector position = new Vector(Math.cos(alphaLong + i * siteRange) + earth.getLocation().x, Math.sin(alphaLong + i * siteRange) + earth.getLocation().y, z);
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
    public static int BunchLaunchPaths(ArrayList<Probe> probes, celestialBody titan){
        boolean[] areDrifting = new boolean[probes.size()];

        for (int i = 0; i < probes.size(); i++){
            double distanceOld = probes.get(i).getdistanceToTitan();
            double distanceCurrent = Math.abs((probes.get(i).getLocation().add(titan.getLocation().multiply(-1))).magnitude()); // distances to titan

            if (distanceCurrent <= TITANRADIUS + 300){
                System.out.println("TITAN HIT");
                System.out.println("launch location :" + probes.get(i).getPStart().toString());
                System.out.println("launch velocity :" + probes.get(i).getVStart().toString());
                return 1; // WE REACHED TITAN!!!
            }
            else if (distanceCurrent < distanceOld){
                probes.get(i).setDistanceToTitan(distanceCurrent);
            } else {
                areDrifting[i] = true;
            }
        }

        boolean allAreDrifting = true;

        for (boolean isDrifting : areDrifting) {
            if (!isDrifting) {
                allAreDrifting = false;
            }
        }

        if (allAreDrifting){
            return 2; // all probes are drifting away
        } else {
            return 0; // everything is fine, continue
        }
        
    }

    // evaluate and returns the longbound 
    public static Vector[] evaluate(ArrayList<Probe> probes, celestialBody titan){
        Probe longBound = null;
        Probe shortBound = null;
        for (int i = 0; i < probes.size(); i++){
            if (probes.get(i).getLocation().magnitude() < titan.getLocation().magnitude()){
                if (shortBound == null || probes.get(i).getdistanceToTitan() < shortBound.getdistanceToTitan()){
                    shortBound = probes.get(i);
                }
            } else{
                if (longBound == null || probes.get(i).getdistanceToTitan() < longBound.getdistanceToTitan()){
                    longBound = probes.get(i);
                }
            }
        }
        Vector v = new Vector(0, 0, 0);
        v = longBound.getVStart().add(shortBound.getVStart()).multiply(0.5);
        return new Vector[] {longBound.getPStart(), shortBound.getPStart(), v};
    }
}

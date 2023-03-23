package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class Launch {
    
    // creates a vector for a probe that describes the velocity at t=0
    public static Vector launch(celestialBody body, Vector relativeV){
        return relativeV.add(body.getVelocity());
    }

    final static double EARTHRADIUS = 6370;
    final static double TITANRADIUS = 2575;

    
    // Launches an amount of probes
    public static Probe[] launchBunch(int amount, celestialBody earth, Vector longBoundryP, Vector shortBoundryP, int with, Vector v){
        
        // making sure that the z factor is set to the same value
        double zValue = longBoundryP.z;
        shortBoundryP.set(shortBoundryP.x, shortBoundryP.y, zValue);

        // making sure that the launching positions are on earth
        if (longBoundryP.magnitude() - EARTHRADIUS != 0){longBoundryP.multiply(EARTHRADIUS/longBoundryP.magnitude());}
        if (shortBoundryP.magnitude() - EARTHRADIUS != 0){shortBoundryP.multiply(EARTHRADIUS/shortBoundryP.magnitude());}

        // setting the angles
        double alphaLong = Math.asin(longBoundryP.y / longBoundryP.magnitude());
        double alphashort = Math.asin(shortBoundryP.y / shortBoundryP.magnitude()) + 2*Math.PI;
        double range = alphashort - alphaLong; // range between long and short
        double siteRange = range / (amount-1); // range between sites over the x,y plane

        Probe[] probes = new Probe[amount];

        for (int i = 0; i < amount; i++){

            // set the x,y
            probes[i] = new Probe("probe");
            probes[i].setColor(Color.VIOLET);
            probes[i].setLocation(Math.cos(alphaLong + i*siteRange), Math.sin(alphaLong + i*siteRange), 0);
            
            //randomize the z to the width
            double random = Math.random()*2 - 1; // generate numbver between -1 and 1
            double alphaZ = with*random;
            double y = Math.cos(alphaZ)*EARTHRADIUS;
            double z = Math.sin(alphaZ)*EARTHRADIUS;
            
            probes[i].setLocation(probes[i].getLocation().x + earth.getLocation().x, y + earth.getLocation().y, z + earth.getLocation().z);
            probes[i].setPSTART(probes[i].getLocation());
            probes[i].setVelocity(launch(earth, v).x, launch(earth, v).y, launch(earth, v).z);
            probes[i].setVSRART(probes[i].getVelocity());
        }
        return probes;
    }
    // keeps track of launched probes and evaluates them.
    public static Vector[] BunchLaunchPaths(Probe[] probes, celestialBody titan){
        boolean[] isDrifting = new boolean[probes.length];
        for (int i = 0; i < probes.length; i++){
            double distanceOld = probes[i].getdistanceToTitan();
            double distanceCurrent = Math.abs((probes[i].getLocation().add(titan.getLocation().multiply(-1))).magnitude()); // distances to titan
            if (distanceCurrent <= TITANRADIUS){
                System.out.println("TITAN HIT");
                System.out.println("launch location :" + probes[i].getPStrart().toString());
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
        return new Vector[] {longBound.getPStrart(), longBound.getVStart(), shortBound.getPStrart(), shortBound.getVStart()};
    }
}

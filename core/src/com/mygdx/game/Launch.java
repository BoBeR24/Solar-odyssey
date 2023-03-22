package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class Launch {
    
    // creates a vector for a probe that describes the velocity at t=0
    public static Vector launch(celestialBody body, Vector relativeV){
        return relativeV.add(body.getVelocity());
    }

    final static double EARTHRADIUS = 6370;

    
    // Launches an amount of probes
    public static Probe[] launchBunch(int amount, celestialBody earth, Vector longBoundryP, Vector shortBoundryP, int with, Vector v){
        
        // making sure that the z factor is set to 0
        longBoundryP.set(longBoundryP.x, longBoundryP.y, 0);
        shortBoundryP.set(shortBoundryP.x, shortBoundryP.y, 0);

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
            
            probes[i].setVelocity(launch(earth, v).x, launch(earth, v).y, launch(earth, v).z);

        }
        return probes;
    }
}

package com.mygdx.game;

public class RK4 {

    private static Vector[][] k_Values;
    private static Vector zVector = new Vector(0,0,0);
    static int increment = 0;

    //TODO Pass by Value, Check whether at final equation, it is times stepsize

    public static void calculate(int Stepsize){

        double STEPSIZE = (double) Stepsize;
        
        k_Values = new Vector[11][10];

        for (celestialBody planet : SolarSystem.planets) {
            k_Values[planet.getId()][8] = planet.getLocation().copy();
            k_Values[planet.getId()][9] = planet.getVelocity().copy();
            k1calculate(planet, STEPSIZE);
        }
        for (celestialBody planet : SolarSystem.planets) {
            planet.setLocation(k_Values[planet.getId()][0]);
            planet.setVelocity(k_Values[planet.getId()][1]);

        }

        for (celestialBody planet : SolarSystem.planets) {
            k2calculate(planet, STEPSIZE/2.0);
        }
        for (celestialBody planet : SolarSystem.planets) {
            planet.setLocation(k_Values[planet.getId()][2]);
            planet.setVelocity(k_Values[planet.getId()][3]);
        }

        for (celestialBody planet : SolarSystem.planets) {
            k3calculate(planet, STEPSIZE/2.0);
        }
        for (celestialBody planet : SolarSystem.planets) {
            planet.setLocation(k_Values[planet.getId()][4]);
            planet.setVelocity(k_Values[planet.getId()][5]);
        }

        for (celestialBody planet : SolarSystem.planets) {
            k4calculate(planet, STEPSIZE*2.0);
        }
        for (celestialBody planet : SolarSystem.planets) {
            planet.setLocation(k_Values[planet.getId()][6]);
            planet.setVelocity(k_Values[planet.getId()][7]);
        }

        for (celestialBody planet : SolarSystem.planets) {

            Vector kEquationPos = k_Values[planet.getId()][0].add(k_Values[planet.getId()][2].multiply(2.0)).add(k_Values[planet.getId()][4].multiply(2.0).add(k_Values[planet.getId()][6])).multiply(0.16666666666*STEPSIZE);
            Vector kEquationVel = k_Values[planet.getId()][1].add(k_Values[planet.getId()][3].multiply(2.0)).add(k_Values[planet.getId()][5].multiply(2.0).add(k_Values[planet.getId()][7])).multiply(0.16666666666*STEPSIZE);
            planet.setLocation(kEquationPos.add(k_Values[planet.getId()][8]));
            planet.setVelocity(kEquationVel.add(k_Values[planet.getId()][9]));

        }

    }

    public static void k1calculate(Body body, double STEPSIZE){
        Vector force = PhysicsUtils.allForce(body);
        k_Values[body.getId()][0] = updateCoordinate(body, STEPSIZE, zVector, 1.0);
        k_Values[body.getId()][1] = updateVelocity(body, force, STEPSIZE, zVector, 1.0);
    }

    public static void k2calculate(Body body, double STEPSIZE){
        Vector force = PhysicsUtils.allForce(body);
        k_Values[body.getId()][2] = updateCoordinate(body, STEPSIZE,  k_Values[body.getId()][0], 2.0);
        k_Values[body.getId()][3] = updateVelocity(body, force, STEPSIZE, k_Values[body.getId()][1], 2.0);
    }

    public static void k3calculate(Body body, double STEPSIZE){
        Vector force = PhysicsUtils.allForce(body);
        k_Values[body.getId()][4] = updateCoordinate(body, STEPSIZE, k_Values[body.getId()][2], 2.0);
        k_Values[body.getId()][5] = updateVelocity(body, force, STEPSIZE, k_Values[body.getId()][3], 2.0);
    }

    public static void k4calculate(Body body, double STEPSIZE){
        Vector force = PhysicsUtils.allForce(body);
        k_Values[body.getId()][6] = updateCoordinate(body, STEPSIZE*2, k_Values[body.getId()][4], 1.0);
        k_Values[body.getId()][7] = updateVelocity(body, force, STEPSIZE*2, k_Values[body.getId()][5], 1.0);
    }

    /** update coordinates of the body
     * */
    private static Vector updateCoordinate(Body body, double STEPSIZE, Vector addVector, double scale){
        return new Vector(
                ((body.getLocation().x + (addVector.x/scale)) + body.getVelocity().x * STEPSIZE),
                ((body.getLocation().y + (addVector.y/scale)) + body.getVelocity().y * STEPSIZE),
                ((body.getLocation().z + (addVector.z/scale)) + body.getVelocity().z * STEPSIZE));
    }

    /** update velocity of the body
     * */
    private static Vector updateVelocity(Body body, Vector forcesSum, double STEPSIZE, Vector addVector, double scale){
        return new Vector(
                ((body.getVelocity().x + (addVector.x/scale)) + (forcesSum.x * STEPSIZE) / body.getMass()),
                ((body.getVelocity().y + (addVector.y/scale)) + (forcesSum.y * STEPSIZE) / body.getMass()),
                ((body.getVelocity().z + (addVector.z/scale)) + (forcesSum.z * STEPSIZE) / body.getMass()));
    }

}

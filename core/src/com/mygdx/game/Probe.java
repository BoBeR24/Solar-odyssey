package com.mygdx.game;

public class Probe implements Body{
//    private String name;
    private int id = SystemProperties.PROBE; // same id is reserved for all probes(so all probes have the same id)
    private double mass;
    private Vector location;
    private Vector velocity;
    private Vector pStart; // To track the starting position of the probe
    private Vector vStart; // To track the starting velocity of the probe
    private double distanceToTitan; // closest the probe has gotten to titan
    private boolean titanReached;


    Probe(Vector starterVelocity, Vector starterLocation){
        this.mass = 50000;
        this.location = SystemProperties.coordinates[3].add(new Vector(SystemProperties.radii[3], 0, 0)); // default position
        this.velocity = starterLocation; // default velocity
//        this.velocity = SystemProperties.velocities[3]; // default velocity

        this.pStart = new Vector(this.location);
        this.vStart = new Vector(starterVelocity);
    }

    Probe(Vector starterVelocity) {
        this(starterVelocity, SystemProperties.velocities[3]);
    }

    @Override
    public void setLocation(double x, double y, double z){
        this.location = new Vector(x, y, z);

        // if the location of the probe changes - distance to Titan also changes
        updateDistanceToTitan();
    }

    public void setLocation(Vector vector){
        this.setLocation(vector.x, vector.y, vector.z);
    }

    @Override
    public void setVelocity(double x, double y, double z){
        this.velocity = new Vector(x, y, z);
    }

    public void setVelocity(Vector vector){
        this.setVelocity(vector.x, vector.y, vector.z);
    }

    @Override
    public Vector getLocation(){
        return this.location;
    }

    @Override
    public Vector getVelocity(){
        return this.velocity;
    }

    @Override
    public double getMass(){
        return this.mass;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Probe clone(){
        return new Probe(new Vector(this.velocity), new Vector(this.location));
    }

    public Vector getPStart(){
        return pStart;
    }

    public Vector getVStart(){
        return this.vStart;
    }

    public double getDistanceToTitan(){
        return this.distanceToTitan;
    }

    public boolean isTitanReached() {
        return titanReached;
    }

    /** calculates distance to Titans surface relative to the current position of the probe
     *  and updates corresponding field.
     *  If after the update titan is reached we also update corresponding field
     * */
    public void updateDistanceToTitan(){
        // gets titan object
        celestialBody titan = SolarSystem.planets.get(SystemProperties.TITAN);

        this.distanceToTitan = this.location.subtract(titan.getLocation()).magnitude();

        // if distance to titans surface is less or equal to 300 km we consider that titan is reached
        if (distanceToTitan <= titan.getRadius() + 300) {
            titanReached = true;
        }
    }

    /** display in terminal current data about the probe(distance to titan, if Titan is reached or not,
     * current location, current velocity,
     * initial location and initial velocity)
     * */
    public void displayData() {
        System.out.println("Distance to Titan center: " + this.distanceToTitan);
        System.out.println("Is Titan reached: " + titanReached);
        System.out.println("The Position of the Probe is: " + this.location);
        System.out.println("The Velocity of the Probe is: " + this.velocity);
        System.out.println("Initial position(relative to the Sun): " + this.pStart);
        System.out.println("Initial velocity(relative to Earth): " + this.vStart);
    }
}

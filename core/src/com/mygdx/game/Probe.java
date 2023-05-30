package com.mygdx.game;

public class Probe implements Body{
    private int id = SystemProperties.PROBE; // same id is reserved for all probes(so all probes have the same id)
    private double mass;
    private Vector location;
    private Vector nextLocation;
    private Vector velocity;
    private Vector nextVelocity;
    private Vector pStart; // To track the starting position of the probe
    private Vector vStart; // To track the starting velocity of the probe
    private double distanceToTitan; // closest the probe has gotten to titan
    private boolean titanReached;

    /** Creating Probe with specified starter velocity and location
     * @param starterVelocity - initial velocity of the probe relative to Earth
     * @param starterLocation - initial location of the probe relative to Earth
     * */
    Probe(Vector starterVelocity, Vector starterLocation){
        this.mass = 50000;
        // add relative location and velocity to Earths initial condition to get absolute values
        this.location = SystemProperties.initCoordinates[SystemProperties.EARTH].add(starterLocation);
        this.velocity = SystemProperties.initVelocities[SystemProperties.EARTH].add(starterVelocity);

        // let next states initially be same as current(they will be changed anyway)
        this.nextLocation = new Vector(this.location);
        this.nextVelocity = new Vector(this.velocity);

        this.pStart = new Vector(this.location);
        this.vStart = new Vector(this.velocity);

        this.distanceToTitan =  this.calculateDistanceToTitan(); // calculate initial distance to titan
    }

    /** Creating probe with default starting location (which is on the right side of earth)
     * and specified starter velocity
     * @param starterVelocity - initial velocity of the probe
     * */
    Probe(Vector starterVelocity) {
        this(starterVelocity, new Vector(SystemProperties.radii[SystemProperties.EARTH], 0, 0));
    }

    Probe() {
        this.mass = 50000;
    }

    /** !!!This clone method doesn't do a full copy, it only transfers essential characteristics,
     *  such as location and velocity
     * */
    @Override
    public Probe clone(){
        Probe cloned_probe = new Probe(new Vector(0.0, 0.0, 0.0));

        cloned_probe.setLocation(new Vector(this.location));
        cloned_probe.setVelocity(new Vector(this.velocity));

        cloned_probe.setNextLocation(new Vector(this.nextLocation));
        cloned_probe.setNextVelocity(new Vector(this.nextVelocity));

        return cloned_probe;
    }

    @Override
    public void update() {
        this.setLocation(this.nextLocation);
        this.setVelocity(this.nextVelocity);
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
    public double calculateDistanceToTitan(){
        // gets titan object
        celestialBody titan = (celestialBody) SolarSystem.bodies.get(SystemProperties.TITAN);

        double dist = this.location.subtract(titan.getLocation()).magnitude();

        // if distance to titans surface is less or equal to 300 km we consider that titan is reached
        if (dist <= titan.getRadius() + 300) {
            titanReached = true;
        }

        return dist;
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

    @Override
    public void setLocation(double x, double y, double z){
        this.location.x = x;
        this.location.y = y;
        this.location.z = z;

        // if the location of the probe changes - distance to Titan also changes
        this.distanceToTitan = this.calculateDistanceToTitan();
    }

    public void setLocation(Vector vector){
        this.setLocation(vector.x, vector.y, vector.z);
    }

    @Override
    public void setVelocity(double x, double y, double z){
        this.velocity.x = x;
        this.velocity.y = y;
        this.velocity.z = z;
    }

    @Override
    public void setNextLocation(double x, double y, double z) {
        this.nextLocation.x = x;
        this.nextLocation.y = y;
        this.nextLocation.z = z;
    }

    public void setNextLocation(Vector vector) {
        this.setNextLocation(vector.x, vector.y, vector.z);
    }
    @Override
    public void setNextVelocity(double x, double y, double z) {
        this.nextVelocity.x = x;
        this.nextVelocity.y = y;
        this.nextVelocity.z = z;
    }

    public void setNextVelocity(Vector vector) {
        this.setNextVelocity(vector.x, vector.y, vector.z);
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
    public Vector getNextLocation() {
        return this.nextLocation;
    }

    @Override
    public Vector getNextVelocity() {
        return this.nextVelocity;
    }

    @Override
    public double getMass(){
        return this.mass;
    }

    @Override
    public int getId() {
        return id;
    }
}

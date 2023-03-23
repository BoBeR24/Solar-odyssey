package com.mygdx.game;

public interface Body {
    void setLocation(double x, double y, double z);
    void setVelocity(double x, double y, double z);
    Vector getLocation();

    Vector getVelocity();

    double getMass();
    String getName();

}

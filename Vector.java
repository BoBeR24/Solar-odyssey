public class Vector {
    double x;
    double y;
    double z;

    public Vector(double x, double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public void Set(double x, double y, double z){
        this.x =x;
        this.y = y;
        this.z =z;
    }
    /**
     * Updates current values with values from input
     **/
    public Vector set(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;

        return this;
    }
    public Vector Add(Vector a, Vector b){
        return new Vector((a.getX()+b.getX()),(a.getY()+b.getY()),(a.getZ()+b.getZ()));
    }

    /**
     * Applies mathematical operation of summing two vectors
     **/
    public Vector add(Vector b){
        return set((this.x + b.x), (this.y + b.y), (this.z + b.z));
    }

    
    /**
     * Overrides toString function to print vector
     **/
    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
    public double getX(){return x;}
    public double getY(){return y;}
    public double getZ(){return z;}

    public double getLength(){
        return Math.sqrt((x*x)+(y*y)+(z*z));
    }
}
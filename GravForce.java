public class GravForce extends SystemProperties{

    final public double Gravcst = 6.6743e10;
    final public double ProbeMass = 50000;
    public String planet;
    public Vector finalForce;

    public static void main(String[] args) {
       GravForce test = new GravForce("Earth");
       System.out.println(test.gravForceVector());
       test.newVelocity();
       System.out.println(test.gravForceVector());

       //GravForce test1 = new GravForce("Mercury");
       //System.out.println(test1.gravForceVector());
               

        
    }



    public GravForce(String planet){
        this.planet = planet;

    }


    /*public Vector GravForceSun(){
       /*int index = entities.get("Earth");
        //first part of the formula
       double firstPart = Gravcst*ProbeMass*masses[index];
       Vector Xi = coordinates[index];
       Vector numerateur = new Vector(0, 0, 0);
       // in the add we need to put the coordinate vector of the probe, but we don't have it
       Vector sum = numerateur.Add(Xi, Xi/*need to have the coordinate of the probe and add -[probe cordinate] );


       double X1 = Xi.getX();
       double Y1 = Xi.getY();
       double Z1 = Xi.getZ();
       /*then need the position of the probe gonna put some random value for now 
       double Xmin = X1 - 1;
       double Ymin = Y1 - 1;
       double Zmin = Z1 - 1;
       Xmin = Xmin*Xmin;
       Ymin = Ymin*Ymin;
       Zmin = Zmin*Zmin;
       double denom = Math.sqrt(Xmin+Ymin+Zmin);
       denom = denom*denom*denom;

       double multplicateur = firstPart/denom;
       double a = sum.getX();
       double b = sum.getY();
       double c = sum.getZ();

       a = a*multplicateur;
       b = b*multplicateur;
       c = c*multplicateur;

       Vector finalForce = new Vector(a, b, c);
       System.out.println(finalForce.toString());

       return finalForce;

        return null;
 
    }*/

    public Vector gravForceVector(){
        
        int index = entities.get(planet);
        String []entiteStrings = {"Sun","Mercury", "Venus", "Earth", "Moon", "Mars","Jupiter", "Saturn", "Titan"};
        //String entites2 = "";
        //int index2 = entities.get(entites2);
        Vector finalForce = new Vector(0, 0, 0);

        for (int i = 0; i < entiteStrings.length; i++) {
           
           int index2 = entities.get(entiteStrings[i]);
           double firstPart =  Gravcst*masses[index]*masses[index2];

           Vector position1 = coordinates[index];
           
           double x1 = position1.x;
           double y1 = position1.y;
           double z1 = position1.z;
           

           Vector position2 = coordinates[index2];

           double x2 = position2.x;
           double y2 = position2.z;
           double z2 = position2.z;

           double X1minX2 = x1-x2;
           double Y1minY2 = y1-y2;
           double Z1minZ2 = z1-z2;

           X1minX2 = X1minX2*X1minX2;
           Y1minY2 = Y1minY2*Y1minY2;
           Z1minZ2 = Z1minZ2*Z1minZ2;

           double denomFinal = Math.pow(Math.sqrt(X1minX2 + Y1minY2 + Z1minZ2),3);
           double NumOnDenom = firstPart/denomFinal;

           Vector LocalFinal = new Vector(NumOnDenom*X1minX2, NumOnDenom*Y1minY2, NumOnDenom*Z1minZ2);
           finalForce = finalForce.add(LocalFinal);
           LocalFinal = new Vector(0, 0, 0);

        }
        this.finalForce = finalForce;
        return finalForce;


    }

    public void newVelocity(){
        int index = entities.get(planet); 
        double x = velocities[index].x;
        double y = velocities[index].y;
        double z = velocities[index].z;

        
        velocities[index].set(x+ ((finalForce.x)*3600)/masses[index], y+ ((finalForce.y)*3600)/masses[index], z+ ((finalForce.z)*3600)/masses[index]);

    }

    public void newCoordinate(){
        int index = entities.get(planet);
        double x = coordinates[index].x;
        double y = coordinates[index].y;
        double z = coordinates[index].z;

        coordinates[index].set((x+velocities[index].x)*3600, (coordinates[index].y+velocities[index].y)*3600, (coordinates[index].y+velocities[index].y)*3600);

        
    }
   
}

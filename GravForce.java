public class GravForce extends SystemProperties{

    final public double Gravcst = 6.67e-11;
    final public double ProbeMass = 50000;

    public static void main(String[] args) {
        GravForce force = new GravForce();
        force.GravForceSun();
        
    }



    public GravForce(){


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

    public void GravForceSun(){

        int index = entities.get("Sun");
        String []entiteStrings = {"Sun"};
        //String entites2 = "";
        //int index2 = entities.get(entites2);
        Vector finalForce = new Vector(0, 0, 0);

        for (int i = 0; i < entiteStrings.length; i++) {
           
           int index2 = entities.get(entiteStrings[i]);
           double firstPart =  Gravcst*masses[index]*masses[index2];

           Vector position1 = coordinates[index];
           
           double x1 = position1.getX();
           double y1 = position1.getY();
           double z1 = position1.getZ();
           

           Vector position2 = coordinates[index2];

           double x2 = position2.getX();
           double y2 = position2.getZ();
           double z2 = position2.getZ();

           double X1minX2 = x1-x2;
           double Y1minY2 = y1-y2;
           double Z1minZ2 = z1-z2;

           X1minX2 = X1minX2*X1minX2;
           Y1minY2 = Y1minY2*Y1minY2;
           Z1minZ2 = Z1minZ2*Z1minZ2;

           double denomFinal = Math.sqrt(X1minX2 + Y1minY2 + Z1minZ2);
           double NumOnDenom = firstPart/denomFinal;

           Vector LocalFinal = new Vector(NumOnDenom*X1minX2, NumOnDenom*Y1minY2, NumOnDenom*Z1minZ2);
           finalForce = finalForce.Add(finalForce, LocalFinal);

        }

        System.out.println(finalForce.toString());


    }
   
}

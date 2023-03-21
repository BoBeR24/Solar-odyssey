public class GravForce extends SystemProperties{

    final public double Gravcst = 6.67e-11;
    final public double ProbeMass = 50000;

    public static void main(String[] args) {
        GravForce force = new GravForce();
        force.GravForceSun();
        force.GravForceMercury();
    }



    public GravForce(){


    }


    public Vector GravForceSun(){
       int index = entities.get("Mercury");
        //first part of the formula
       double firstPart = Gravcst*ProbeMass*masses[index];
       Vector Xi = coordinates[index];
       Vector numerateur = new Vector(0, 0, 0);
       // in the add we need to put the coordinate vector of the probe, but we don't have it
       Vector sum = numerateur.Add(Xi, Xi/*need to have the coordinate of the probe and add -[probe cordinate] */);


       double X1 = Xi.getX();
       double Y1 = Xi.getY();
       double Z1 = Xi.getZ();
       /*then need the position of the probe gonna put some random value for now */
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











       
       //combine les 2 partie 
 
    }
    public void GravForceMercury(){
        int index = entities.get("Mercury");
     
        double firstPart = Gravcst*ProbeMass*masses[index];
        double secondPert;
        
 
    }
    public void GravForceVenus(){
        int index = entities.get("Venus");
     
        double firstPart = Gravcst*ProbeMass*masses[index];
        
 
    }


    
}

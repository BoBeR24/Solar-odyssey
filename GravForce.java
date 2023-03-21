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


    public void GravForceSun(){
       int index = entities.get("Sun");
        //first part of the formula
       double firstPart = Gravcst*ProbeMass*masses[index];
       Vector Xi = coordinates[index];
       Vector numerateur = new Vector(0, 0, 0);
       // in the add we need to put the coordinate vector of the probe, but we don't have it
       Vector sum = numerateur.Add(Xi, Xi/*need to have the coordinate of the probe */);


       Vector denominateur = new Vector(0, 0, 0);
       //faire en sorte d'avoir -Xj
       Vector sumdown = denominateur.Add(Xi, denominateur);
       double x = sumdown.getX();
       double y = sumdown.getY();
       double z = sumdown.getZ();
       Vector fin = sumdown.set(x*x*x, y*y*y, z*z*z);
        System.out.println(fin.toString());
       //pour la division use .getX,etcc, puis multiplier denom*(1/nume) puis set dans un vector 
       Vector test = new Vector(0, 0, 0);
       

       
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

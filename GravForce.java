public class GravForce extends SystemProperties{

    final public double Gravcst = 6.6743e10;
    final public double ProbeMass = 50000;
    public String planet;
    public Vector finalForce;

    public static void main(String[] args) {
       GravForce test = new GravForce("Earth");
       System.out.println(test.gravForceVector());
       test.newVelocity();
       test.newCoordinate();
       System.out.println(test.gravForceVector());
        
    }


    public GravForce(String planet){
        this.planet = planet;

    }


    public Vector gravForceVector(){
        int index = entities.get(planet);
        String []entiteStrings = {"Sun","Mercury", "Venus","Earth", "Moon", "Mars","Jupiter", "Saturn", "Titan"};
        
        Vector finalForce = new Vector(0, 0, 0);

        for (int i = 0; i < entiteStrings.length; i++) {
            //i get different value when i implement if statement to skip the entities that is taken as input
           if(!planet.equals(entiteStrings[i])){
            int index2 = entities.get(entiteStrings[i]);
           double firstPart =  Gravcst*masses[index]*masses[index2];

           Vector position1 = coordinates[index];
           
           double x1 = position1.x;
           double y1 = position1.y;
           double z1 = position1.z;
           

           Vector position2 = coordinates[index2];

           double x2 = position2.x;
           double y2 = position2.y;
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

           }
           else{
            i++;
           }
           
        }
        this.finalForce = finalForce;
        return finalForce;


    }

    public void newVelocity(){
        int index = entities.get(planet); 
        velocities[index].set(velocities[index].x+ ((finalForce.x)*3600)/masses[index], velocities[index].y+ ((finalForce.y)*3600)/masses[index], velocities[index].z+ ((finalForce.z)*3600)/masses[index]);

    }

    public void newCoordinate(){
        int index = entities.get(planet);
        coordinates[index].set((coordinates[index].x+velocities[index].x)*3600, (coordinates[index].y+velocities[index].y)*3600, (coordinates[index].z+velocities[index].z)*3600);

        
    }
   
}

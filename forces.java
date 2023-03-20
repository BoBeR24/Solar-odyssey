import java.util.Vector;

import com.mygdx.game.celestialBody;
import com.mygdx.game.vector;

public class forces{

    //Still have to change
    public final static double gravitionalconstant = 6.0;

    public static Vector3 actingforces(celestialBody Planet, celestialBody Spacecraft){

        double scalingfactor = gravitionalconstant*Planet.getMass()*Spacecraft.getMass()*(-1);

        Vector3 Planetvector = Planet.getLocation();
        Vector3 Spacecraftvector = Spacecraft.getLocation();

        

        

        //TODO (Spacecraftvector - Planetvector)

        //TODO (|Spacecraft - Planet|)³

        
    

    }

}
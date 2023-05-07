package MathematicalSolver;

import com.mygdx.game.Body;
import com.mygdx.game.Vector;

public interface MathematicalSolver {
    
    public final static double gravitationalConstant = 6.6743 * Math.pow(10, -20);
    public final static int STEPSIZE = 30;

    public void updateVelocities(Body body, Vector forcesSum);

    public void updatePositions(Body body);
}

package MathematicalSolver;

import com.mygdx.game.Body;
import com.mygdx.game.SystemProperties;
import com.mygdx.game.Vector;

public class Euler implements MathematicalSolver {

    @Override
    public void updateVelocities(Body body, Vector forcesSum) {
        // TODO Auto-generated method stub
        int index = body.getId();

        // if body is a probe update its properties immediately, if it is a planet write them to nextState array
        if (index != SystemProperties.PROBE) {

            SystemProperties.velocities_nextState[index].set(body.getVelocity(3).x + (forcesSum.x * STEPSIZE) / body.getMass(), body.getVelocity(3).y +
                    (forcesSum.y * STEPSIZE) / body.getMass(), body.getVelocity(3).z + (forcesSum.z * STEPSIZE) / body.getMass());

            return;
        }

        body.setVelocity(body.getVelocity(3).x + (forcesSum.x * STEPSIZE) / body.getMass(), body.getVelocity(3).y +
                (forcesSum.y * STEPSIZE) / body.getMass(), body.getVelocity(3).z + (forcesSum.z * STEPSIZE) / body.getMass(), 3);
    }

    @Override
    public void updatePositions(Body body) {
        // TODO Auto-generated method stub
        int index = body.getId();

        // if body is a probe update its properties immediately, if it is a planet write them to nextState array
        if (index != SystemProperties.PROBE) {

            SystemProperties.coordinates_nextState[index].set((body.getLocation(3).x + body.getVelocity(3).x * STEPSIZE), (body.getLocation(3).y +
                    body.getVelocity(3).y * STEPSIZE), (body.getLocation(3).z + body.getVelocity(3).z * STEPSIZE));

            return;
        }

        body.setLocation((body.getLocation(3).x + body.getVelocity(3).x * STEPSIZE), (body.getLocation(3).y +
               body.getVelocity(3).y * STEPSIZE), (body.getLocation(3).z + body.getVelocity(3).z * STEPSIZE), 3);
    }
    
}

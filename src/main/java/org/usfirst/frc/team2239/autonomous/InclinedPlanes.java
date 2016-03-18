package org.usfirst.frc.team2239.autonomous;

/**
 * What to do if in front of the Ramparts (the two inclined planes)
 *
 * @author Dean Bassett
 */
public class InclinedPlanes extends AutoFunction {

    /**
     * Each time autonomousPeriodic() gets called
     */
    @Override
    protected void onUpdate() {
        if(stage == 0) {
            drive.accelerateTo(.5, .5);
        }
    }

    /**
     * Default stage times
     *
     * @return times for the different stages
     */
    @Override
    protected double[] defaultTimes() {
        return new double[]{7};
    }
}

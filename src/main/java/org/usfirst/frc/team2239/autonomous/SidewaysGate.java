package org.usfirst.frc.team2239.autonomous;

/**
 * How to open the sideways openning gate
 *
 * @author Technocrats
 */
public class SidewaysGate extends AutoFunction {

    @Override
    public void onUpdate() {
        if(stage == 0) {
            drive.accelerateTo(1, 1);
        }
    }

    @Override
    protected double[] defaultTimes() {
        return new double[]{3};
    }
}

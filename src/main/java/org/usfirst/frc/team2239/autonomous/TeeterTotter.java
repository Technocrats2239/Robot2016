package org.usfirst.frc.team2239.autonomous;

/**
 * How to open the sideways openning gate
 *
 * @author Technocrats
 */
public class TeeterTotter extends AutoFunction {

    @Override
    public void onUpdate() {
        switch (stage) {
            case 0:
                drive.accelerateTo(.7, .7);
                break;
            case 1:
                drive.accelerateTo(0, 0);
                robot.arm.drop();
                break;
            case 2:
                drive.accelerateTo(.7, .7);
                break;
        }
    }

    @Override
    protected double[] defaultTimes() {
        return new double[]{.5, 1, 3};
    }
}

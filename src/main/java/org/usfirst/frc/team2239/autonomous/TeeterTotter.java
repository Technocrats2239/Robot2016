package org.usfirst.frc.team2239.autonomous;

/**
 * What to do if in front of the Cheval de Frise (the teeter totter lookin things)
 *
 * @author Dean Bassett
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

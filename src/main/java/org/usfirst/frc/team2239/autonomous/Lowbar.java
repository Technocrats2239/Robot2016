package org.usfirst.frc.team2239.autonomous;

/**
 * What to do if in front of the Low Bar
 *
 * @author Technocrats
 */
public class Lowbar extends AutoFunction {
    @Override
    public void onUpdate() {

        switch (stage) {
            case 0:
                drive.accelerateTo(.6, .6); //go forwards
                break;
            case 1:
                drive.accelerateTo(1, 0); //turn right
                break;
            case 2:
                drive.accelerateTo(.6, .6); //go forwards
                break;
            case 3:
                robot.collector.spit(); //spit out the ball
                break;
            default:
                //do nothing
        }
    }

    @Override
    protected double[] defaultTimes() {
        return new double[]{2.4, .4, 2.7, 2};
    }
}

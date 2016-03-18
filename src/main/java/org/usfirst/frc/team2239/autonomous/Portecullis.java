package org.usfirst.frc.team2239.autonomous;

import edu.wpi.first.wpilibj.Preferences;

/**
 * What to do if in front of the PorteCullis thingy (the vertical gate thing)
 *
 * @author Technocrats
 */
public class Portecullis extends AutoFunction {
    private double robotMoveSpeed;

    @Override
    public void onStart() {
        robotMoveSpeed = Preferences.getInstance().getDouble("PorteCullis_speed", .2);
    }

    @Override
    public void onUpdate() {
        switch (stage) {
            case 0:
                drive.accelerateTo(.6, .6); //go forwards
                break;
            case 1:
                robot.arm.lift();
                drive.accelerateTo(robotMoveSpeed, robotMoveSpeed);
                break;
            case 2:
                drive.accelerateTo(.6, .6); //go forwards
                break;
            default:
                //do nothing
        }
    }

    @Override
    protected double[] defaultTimes() {
        return new double[]{.4, 5, 2}; //{come in, under gate, rush off}
    }
}

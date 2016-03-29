package org.usfirst.frc.team2239.autonomous;

import edu.wpi.first.wpilibj.Preferences;

/**
 * What to do if in front of the Portcullis thingy (the vertical gate thing)
 *
 * @author Dean Bassett and London Lomanstone
 */
public class Portcullis extends AutoFunction {
    private double driveSpeed;

    @Override
    public void onStart() {
        if(!Preferences.getInstance().containsKey("Portcullis_speed")) {
            Preferences.getInstance().putDouble("Portcullis_speed", .2);
            driveSpeed = .2;
        } else {
            driveSpeed = Preferences.getInstance().getDouble("Portcullis_speed", .2);
        }
    }

    @Override
    public void onUpdate() {
        switch (stage) {
            case 0:
                drive.accelerateTo(.6, .6); //go forwards
                break;
            case 1:
                robot.arm.lift();
                drive.accelerateTo(driveSpeed, driveSpeed);
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

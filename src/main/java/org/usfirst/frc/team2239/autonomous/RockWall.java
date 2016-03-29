package org.usfirst.frc.team2239.autonomous;

import edu.wpi.first.wpilibj.Preferences;

/**
 * What to do if in front of the rock wall
 *
 * @author Dean Bassett
 */
public class RockWall extends AutoFunction {
    private double driveSpeed;

    @Override
    public void onStart() {
        if(!Preferences.getInstance().containsKey("Rockwall_speed")) {
            Preferences.getInstance().putDouble("Rockwall_speed", .7);
            driveSpeed = .2;
        } else {
            driveSpeed = Preferences.getInstance().getDouble("Portcullis_speed", .7);
        }
    }
    /**
     * Each time autonomousPeriodic() gets called
     */
    @Override
    protected void onUpdate() {
        if(stage == 0) {
            drive.accelerateTo(driveSpeed, driveSpeed);
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

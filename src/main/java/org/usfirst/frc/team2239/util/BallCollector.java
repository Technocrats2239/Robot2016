package org.usfirst.frc.team2239.util;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * Collects boulders
 *
 * @author Technocrats
 */
public class BallCollector {
    private VictorSP motor;

    public BallCollector(int deviceId) {
        motor = new VictorSP(deviceId);
        motor.set(0);
        motor.setInverted(true);
    }

    //to spit the boulder out
    public void spit() {
        motor.set(-1);
    }

    //to suck the boulder in
    public void swallow() {
        motor.set(.75);
    }

    public void stop() {
        motor.set(0);
    }
}

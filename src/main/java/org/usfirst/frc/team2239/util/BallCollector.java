package org.usfirst.frc.team2239.util;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

/**
 * Collects boulders
 *
 * @author Technocrats
 */
public class BallCollector {
    private Victor motor;
    private Timer timer;

    public BallCollector(int deviceId) {
        motor = new Victor(deviceId);
        motor.set(0);
        timer = new Timer();
    }

    public void start() {
        motor.set(.7);
        timer.start();
    }

    public void update() {
        if(timer.hasPeriodPassed(4)) {
            motor.set(0);
            timer.stop();
            timer.reset();
        }
    }
}

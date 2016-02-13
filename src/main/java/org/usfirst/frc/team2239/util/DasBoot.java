package org.usfirst.frc.team2239.util;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

/**
 * A motor attached to a metal thing that kicks the ball
 *
 * @author Technocrats
 */
public class DasBoot {
    private Victor motor;
    private Timer timer;
    private int stage;

    public DasBoot(int deviceId) {
        this.motor = new Victor(deviceId);
        stage = -1;
        timer = new Timer();
    }

    public void kick() {
        if(stage >= 0) return;

        stage = 0;
        timer.start();
    }

    public void update() {
        if(stage == 0) {
            motor.set(1);

            if(timer.hasPeriodPassed(.3)) stage++;
        }
        if(stage == 1) {
            motor.set(-.5);

            if(timer.hasPeriodPassed(.5)) {
                stage = -1;
                timer.stop();
                timer.reset();
            }
        }

    }
}

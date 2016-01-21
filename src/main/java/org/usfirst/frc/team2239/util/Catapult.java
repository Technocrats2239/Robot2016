package org.usfirst.frc.team2239.util;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 * Represents a catapult mechanism
 *
 * @author Dean Bassett
 */
public class Catapult {
    private Solenoid sol1;
    private Solenoid sol2;
    private Timer timer;
    private boolean launching;

    public Catapult(int chan1, int chan2) {
        this.sol1 = new Solenoid(chan1);
        this.sol2 = new Solenoid(chan2);
        sol1.set(false);
        sol2.set(false);
        this.timer = new Timer();
        launching = false;
    }

    public void update() {
        if(timer.hasPeriodPassed(2)) {
            timer.stop();
            timer.reset();

            sol1.set(false);
            sol2.set(false);

            launching = false;
        }
    }

    public void launch() {
        if(isLaunching()) return;
        timer.start();
        sol1.set(true);
        sol2.set(true);
        launching = true;
    }

    public boolean isLaunching() {
        return launching;
    }
}

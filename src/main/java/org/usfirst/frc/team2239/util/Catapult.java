package org.usfirst.frc.team2239.util;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 * Represents a catapult mechanism
 *
 * @author Dean Bassett
 */
public class Catapult {
    private DoubleSolenoid sol1;
    //private Solenoid sol2;
    private Timer timer;

    public Catapult(int chan1/*, int chan2*/) {
        this.sol1 = new DoubleSolenoid(chan1, 0, 1);
        //this.sol2 = new Solenoid(chan2);
        sol1.set(DoubleSolenoid.Value.kReverse);
        //sol2.set(false);
        this.timer = new Timer();
    }

    public void update() {
        if(timer.hasPeriodPassed(2)) {
            timer.stop();
            timer.reset();

            sol1.set(DoubleSolenoid.Value.kReverse);
            //sol2.set(false);
        }
    }

    public void launch() {
        timer.start();
        sol1.set(DoubleSolenoid.Value.kForward);
        //sol2.set(true);
    }
}

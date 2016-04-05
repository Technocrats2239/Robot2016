package org.usfirst.frc.team2239.util;

import edu.wpi.first.wpilibj.DoubleSolenoid;
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
	private int stage;

	public Catapult(int chan1/*, int chan2*/) {
		this.sol1 = new DoubleSolenoid(chan1, 0, 1);
		//this.sol2 = new Solenoid(chan2);
		sol1.set(DoubleSolenoid.Value.kOff);
		//sol2.set(false);
		this.timer = new Timer();
		this.stage = 0;
	}

	public void update() {
		if (stage == 0 && timer.hasPeriodPassed(2)) {
			sol1.set(DoubleSolenoid.Value.kReverse);
			//sol2.set(false);
			stage++;
		}
		if (stage == 1 && timer.hasPeriodPassed(1)) {
			timer.stop();
			timer.reset();
			stage = 0;
		}
	}

	public void launch() {
		timer.start();
		sol1.set(DoubleSolenoid.Value.kForward);
		//sol2.set(true);
	}
}

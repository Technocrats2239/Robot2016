package org.usfirst.frc.team2239.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;

/**
 * Represents a Logitech Controller (Without rumble because rumble is complicated)
 *
 * @author Dean Bassett
 */
public class Controller extends GenericHID {
	private final int port;
	private DriverStation station;

	public Controller(int port) {
		this.port = port;
		this.station = DriverStation.getInstance();
	}

	@Override
	public double getX(Hand hand) {
		return hand == Hand.kLeft ? getRawAxis(0) : getRawAxis(4);
	}

	@Override
	public double getY(Hand hand) {
		return hand == Hand.kLeft ? getRawAxis(1) : getRawAxis(5);
	}

	@Override
	public double getZ(Hand hand) {
		throw new UnsupportedOperationException("There is no Z axis");
	}

	@Override
	public double getTwist() {
		throw new UnsupportedOperationException("There is no twisting");
	}

	@Override
	public double getThrottle() {
		throw new UnsupportedOperationException("There is no throttle");
	}

	@Override
	public double getRawAxis(int axis) {
		return station.getStickAxis(port, axis);
	}

	@Override
	public boolean getTrigger(Hand hand) {
		return hand == Hand.kLeft ? getRawAxis(2) > .9 : getRawAxis(3) > .9;
	}

	@Override
	public boolean getTop(Hand hand) {
		throw new UnsupportedOperationException("There is no top");
	}

	@Override
	public boolean getBumper(Hand hand) {
		return hand == Hand.kLeft ? getRawButton(5) : getRawButton(6);
	}

	@Override
	public boolean getRawButton(int button) {
		return station.getStickButton(port, (byte) button);
	}

	@Override
	public int getPOV(int pov) {
		return station.getStickPOV(port, pov);
	}
}

package org.usfirst.frc.team2239;

import edu.wpi.first.wpilibj.*;

/**
 * Utility for driving the robot
 * Only put functions that operate instantaneously in this class
 *
 * @author Dean Bassett &amp; London Lowmanstone
 */
public class TechnoDrive extends RobotDrive {
    public static final double MAIN_SPEED = 1;

    public TechnoDrive(int leftMotorChannel, int rightMotorChannel) {
        super(setupCANTalon(leftMotorChannel), setupCANTalon(rightMotorChannel));
    }

    //todo test this (setupCANTalon has not been tested yet. It worked without it before.)
    public TechnoDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
        super(setupCANTalon(frontLeftMotor), setupCANTalon(rearLeftMotor),
                setupCANTalon(frontRightMotor), setupCANTalon(rearRightMotor)); //calls the RobotDrive constructor
    }

    //sets the voltage ramp rate so that the motors can't go as fast as possible right away, they must ramp up.
    //needs to be static so that we can call it inside the super call.
    private static CANTalon setupCANTalon(int channel) {
        CANTalon theMotor = new CANTalon(channel);
        //theMotor.setVoltageRampRate(1.5); //deleted to see if this will run better
        return theMotor;
    }

    private void setupMotorSafety() {
        m_safetyHelper = new MotorSafetyHelper(this);
        m_safetyHelper.setExpiration(kDefaultExpirationTime);
        m_safetyHelper.setSafetyEnabled(true);
    }

    public double[] getMotorValues() {
        return new double[] {super.m_rearLeftMotor.get(), -super.m_rearRightMotor.get()};
    }

    public void accelerateTo(double left, double right) {
        double[] speed = getMotorValues();
        tankDrive(accelerateHelper(speed[0], left), accelerateHelper(speed[1], right));
    }

    private double accelerateHelper(double from, double to) {
        if(from < to) {
            return Math.max(to, from - .005);
        } else {
            return Math.min(to, from + .005);
        }
    }

    @Override
    public void tankDrive(GenericHID leftStick, GenericHID rightStick) {
        if (leftStick == null || rightStick == null) {
            throw new NullPointerException("Null HID provided");
        }

        double left = -leftStick.getY();
        double right = -rightStick.getY();

        //rightStick is straight, controller is full speed
        //if the controller is triggered then it will run at full speed

        //straight
        if (rightStick.getTrigger()) {
            double speed = (left+right)/2.0;
            if (leftStick.getTrigger()) {
                tankDrive(speed, speed);
            } else {
                tankDrive(speed*MAIN_SPEED, speed*MAIN_SPEED);
            }
        } else {
            if (leftStick.getTrigger()) {
                tankDrive(left, right);
            } else {
                tankDrive(left*MAIN_SPEED, right*MAIN_SPEED);
            }
        }
    }

    @Override
    public void tankDrive(double left, double right) {
        tankDrive(left, right, true);
    }

    @Override
    public void tankDrive(double left, double right, boolean squared) {
        super.tankDrive(left, right, squared);
    }
}

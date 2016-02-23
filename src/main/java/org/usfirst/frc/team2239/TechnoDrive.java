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
        super(new CANTalon(leftMotorChannel), new CANTalon(rightMotorChannel));
    }

    public TechnoDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
        super(new CANTalon(frontLeftMotor), new CANTalon(rearLeftMotor),
                new CANTalon(frontRightMotor), new CANTalon(rearRightMotor)); //calls the RobotDrive constructor
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

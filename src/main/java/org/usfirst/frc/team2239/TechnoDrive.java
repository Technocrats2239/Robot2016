package org.usfirst.frc.team2239;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Utility for driving the robot
 *
 * @author Dean Bassett & London Lomanstone
 */
public class TechnoDrive extends RobotDrive {
    public static final double MAIN_SPEED = .8;

    public TechnoDrive(int leftMotorChannel, int rightMotorChannel) {
        super(leftMotorChannel, rightMotorChannel);
    }

    public TechnoDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
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

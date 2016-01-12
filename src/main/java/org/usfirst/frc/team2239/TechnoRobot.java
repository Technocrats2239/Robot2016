package org.usfirst.frc.team2239;

import edu.wpi.first.wpilibj.*;

/**
 * Our robot
 */
public class TechnoRobot extends IterativeRobot {
    RobotDrive myRobot;  // class that handles basic drive operations

    Joystick controller;  // set to ID 3 in DriverStation
    Victor liftMotor;
    Compressor compressor;
    Solenoid solenoid;
    //true is tilted forwards
    Timer timer;

    @Override
    /**
     *  Method robotInit declares all devices needed, and turns on the compressor.
     */
    public void robotInit(){
        myRobot = new TechnoDrive(0, 1);
        myRobot.setExpiration(0.1);
        myRobot.setSafetyEnabled(true);
        controller = new Joystick(3);
        liftMotor = new Victor(4);
        liftMotor.enableDeadbandElimination(true);
        compressor = new Compressor();
        solenoid = new Solenoid(0);
        timer = new Timer();
    }

    @Override
    /**
     *  Starts timer to work as a clock for autonomous.
     */
    public void autonomousInit(){
        solenoid.set(true);
        compressor.start();
        timer.start();
    }

    @Override
    public void autonomousPeriodic(){
    }

    @Override
    /**
     * Called when teleOp period starts
     *  When myRobot.setSafetyEnabled is true it will send you errors that the robot is not
     * receiving commands.
     */
    public void teleopInit() {
        timer.stop();
        timer.reset(); //stops and resets the timer
        myRobot.setSafetyEnabled(true);
        myRobot.tankDrive(0, 0); //resets the speed to 0
    }

    @Override
    /**
     * Manual control over the robot during the competition
     */
    public void teleopPeriodic(){
        double left = controller.getRawAxis(1);
        double right = controller.getRawAxis(5);
        myRobot.tankDrive(left, right);
    }
}
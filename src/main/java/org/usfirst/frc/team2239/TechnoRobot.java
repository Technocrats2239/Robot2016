package org.usfirst.frc.team2239;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team2239.util.*;

import java.util.Arrays;

/**
 * Technocrat's game handler
 *
 * @author Technocrats
 */
public class TechnoRobot extends IterativeRobot {
    public static TechnoRobot instance;

    public TechnoDrive drive;  // class that handles basic drive operations
    Controller controller;  // set to ID 3 in DriverStation
    Compressor compressor;
    DasBoot boot;
    BallCollector collector;
    AccelerationUtil accelerator;
    Timer timer;
    NetworkTable lines;


    @Override
    /**
     *  Method robotInit declares all devices needed, and turns on the compressor.
     */
    public void robotInit(){
        instance = this;
        drive = new TechnoDrive(0, 1, 2, 3);
        drive.setExpiration(0.1);
        drive.setSafetyEnabled(true);
        controller = new Controller(3);
        compressor = new Compressor();
        collector = new BallCollector(4);
        timer = new Timer();
        accelerator = new AccelerationUtil(drive);
        lines = NetworkTable.getTable("GRIP/lines");
        boot = new DasBoot(5);
        compressor.stop();
    }

    @Override
    public void autonomousInit() {
        drive.tankDrive(0,0);
        timer.reset();
        timer.start();
    }

    @Override
    public void autonomousPeriodic() {
        accelerator.update();
    }

    @Override
    /**
     *  Starts timer to work as a clock for autonomous.
     */
    public void testInit(){
        compressor.stop();
        timer.start();
    }

    @Override
    public void testPeriodic(){
        final double[] DEFAULT_VALUE = new double[0];

        double[] x1 = (double[]) lines.getValue("x1", DEFAULT_VALUE);
        double[] x2 = (double[]) lines.getValue("x2", DEFAULT_VALUE);
        double[] y1 = (double[]) lines.getValue("y1", DEFAULT_VALUE);
        double[] y2 = (double[]) lines.getValue("y2", DEFAULT_VALUE);
        double[] lengths = (double[]) lines.getValue("length", DEFAULT_VALUE);
        double[] angles = (double[]) lines.getValue("angle", DEFAULT_VALUE);

        System.out.println(Arrays.toString(x1));
        System.out.println(Arrays.toString(x2));
        System.out.println(Arrays.toString(y1));
        System.out.println(Arrays.toString(y2));
        System.out.println(Arrays.toString(lengths));
        System.out.println(Arrays.toString(angles));
    }

    @Override
    /**
     * Called when teleOp period starts
     *  When drive.setSafetyEnabled is true it will send you errors that the robot is not
     * receiving commands.
     */
    public void teleopInit() {
        timer.stop();
        timer.reset(); //stops and resets the timer
        compressor.start();
        drive.setSafetyEnabled(true);
        drive.tankDrive(0, 0); //resets the speed to 0
    }

    @Override
    /**
     * Manual control over the robot during the competition
     */
    public void teleopPeriodic(){
        boot.update();
        collector.update();
        accelerator.update();

        drive.tankDrive(-controller.getY(GenericHID.Hand.kLeft), -controller.getY(GenericHID.Hand.kRight));

        if(controller.getBumper(GenericHID.Hand.kLeft)) {
            boot.kick();
        }

        if(controller.getBumper(GenericHID.Hand.kRight)) {
            collector.start();
        }
    }

    public static AccelerationUtil getAccelerator() {
        return instance.accelerator;
    }
}
package org.usfirst.frc.team2239;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team2239.util.*;

import java.io.IOException;
import java.util.Arrays;

/**
 * Technocrat's game handler
 *
 * @author Technocrats
 */
public class TechnoRobot extends IterativeRobot {
    RobotDrive myRobot;  // class that handles basic drive operations

    Controller controller;  // set to ID 3 in DriverStation
    Compressor compressor;
    Catapult catapult;
    //true is tilted forwards
    Timer timer;
    PowerDistributionPanel pdp;
    NetworkTable lines;


    @Override
    /**
     *  Method robotInit declares all devices needed, and turns on the compressor.
     */
    public void robotInit(){
        myRobot = new TechnoDrive(1, 0);
        myRobot.setExpiration(0.1);
        myRobot.setSafetyEnabled(true);
        controller = new Controller(3);
        compressor = new Compressor();
        catapult = new Catapult(0, 1);
        timer = new Timer();
        pdp = new PowerDistributionPanel();
        pdp.clearStickyFaults();
        try {
            NetworkTable.initialize();
        } catch (IOException e) {
            DriverStation.reportError("NetworkTable failed to initialize!", true);
            e.printStackTrace();
        }
        NetworkTable.setTeam(2239);
        NetworkTable.setServerMode();
        lines = NetworkTable.getTable("GRIP/lines");
    }

    private int stage;

    @Override
    /**
     *  Starts timer to work as a clock for autonomous.
     */
    public void testInit(){
        compressor.stop();
        timer.start();
        stage = 0;
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
        catapult.update();
        myRobot.tankDrive(controller.getY(GenericHID.Hand.kLeft), controller.getY(GenericHID.Hand.kRight));

        if (controller.getBumper(GenericHID.Hand.kLeft)) {
            catapult.launch();
        }

        if (controller.getBumper(GenericHID.Hand.kRight)) {
            compressor.setClosedLoopControl(!compressor.getClosedLoopControl());

            while (controller.getBumper(GenericHID.Hand.kRight));
        }
    }
}
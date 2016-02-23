package org.usfirst.frc.team2239;

import org.usfirst.frc.team2239.autonomous.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import org.usfirst.frc.team2239.util.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Technocrat's game handler
 *
 * @author Technocrats
 */
public class TechnoRobot extends IterativeRobot {
    //Almost all values range from -1.0 to 1.0 (like setting motor values or getting joystick values)
    //Also, when getting values from the joysticks, they are negated to reverse the direction before being passed to any other function.

    public static TechnoRobot instance;
    //from http://wpilib.screenstepslive.com/s/4485/m/26401/l/255419-choosing-an-autonomous-program-from-smartdashboard
    private SendableChooser autoChooser;

    public TechnoDrive drive;  // class that handles basic drive operations
    public Controller controller;  // set to ID 3 in DriverStation
    public LiftingArm arm;
    public BallCollector collector;
    public int direction = 1; //ONLY FOR TELEOP REMOTE DRIVING: 1 means the ball collector is the front, -1 means the arm is in the front
    private int switchDirection = 0; //0 when waiting for the button to be pressed. Once pressed, it switches the direction and then switchDirection is 1 until the button is released

    private Timer timer;
    private AutoFunction auto;


    @Override
    /**
     *  Method robotInit declares all devices needed, and turns on the compressor.
     */
    public void robotInit() {
        instance = this;
        drive = new TechnoDrive(2, 3, 0, 1);
        drive.setExpiration(0.1);
        drive.setSafetyEnabled(true);
        controller = new Controller(3);
        collector = new BallCollector(0);
        timer = new Timer();
        arm = new LiftingArm(1);
        //setup the USB camera for the SmartDashboard
        //from http://www.chiefdelphi.com/forums/showpost.php?p=1425235&postcount=6
        CameraServer server = CameraServer.getInstance();
        server.setQuality(50);
        server.startAutomaticCapture("cam1"); //"cam1" (or something like that) is the name of the camera and can be found from the roborio online dashboard at http://roborio-2239-frc.local/#Home

        //set up the autochooser
        //from http://wpilib.screenstepslive.com/s/4485/m/26401/l/255419-choosing-an-autonomous-program-from-smartdashboard
        autoChooser = new SendableChooser();
        autoChooser.addDefault("Easy Lowbar (default)", new EasyLowbar());
        autoChooser.addObject("Harder Lowbar", new HarderLowbar());
        SmartDashboard.putData("Autonomous mode chooser", autoChooser);
    }

    @Override
    public void autonomousInit() {
        //get the chosen auto program to run and run it.
        //from http://wpilib.screenstepslive.com/s/4485/m/26401/l/255419-choosing-an-autonomous-program-from-smartdashboard
        auto = (AutoFunction) autoChooser.getSelected();
        auto.onStart(instance);
    }

    @Override
    public void autonomousPeriodic() {
        auto.onUpdate();
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
        drive.setSafetyEnabled(true);
        this.stopAll(); //stop everything
    }

    @Override
    /**
     * Manual control over the robot during the competition
     */
    public void teleopPeriodic() {
        double leftVal = -controller.getY(GenericHID.Hand.kLeft);
        double rightVal = -controller.getY(GenericHID.Hand.kRight);
        double fightThreshold = .7;
        if (((getSign(leftVal)== -1 && getSign(rightVal)== 1) ||
            (getSign(leftVal)== 1 && getSign(rightVal)== -1 )) && (Math.abs(leftVal)>fightThreshold || Math.abs(rightVal)>fightThreshold)){

            if (Math.abs(leftVal)> Math.abs(rightVal)) {
                rightVal= 0;
            }
            if (Math.abs(rightVal)> Math.abs(leftVal)) {
                leftVal = 0;
            }
            if (Math.abs(rightVal) == Math.abs(leftVal)) {
                leftVal = 0;
                rightVal = 0;
            }
        }

        //sets the direction and switchDirection
        if (switchDirection == 0) { //waiting for it to be pressed
            if (controller.getPOV() == 0) {
                direction = -direction;
                switchDirection = 1; //waiting for it to be released
            }
        }
        if (switchDirection == 1) { //waiting for it to be released
            if (controller.getPOV(0)!=0) {
                switchDirection = 0; //waiting for it to be pressed
            }
        }

        if (direction==-1) {
            //swap the sides
            double tempVal = leftVal;
            leftVal = -rightVal;
            rightVal = -tempVal;
        }
        drive.tankDrive(leftVal, rightVal);

        /*
        if (controller.getPOV() == 0) {
            arm.lift();
        } else if (controller.getPOV() == 180) {
            arm.drop();
        } else {
            arm.stop();
        }
        */

        if (controller.getBumper(GenericHID.Hand.kLeft)) {
            arm.lift();
        } else if (controller.getBumper(GenericHID.Hand.kRight)) {
            arm.drop();
        } else {
            arm.stop();
        }

        if (controller.getTrigger(GenericHID.Hand.kLeft)) {
            collector.swallow();
        } else if (controller.getTrigger(GenericHID.Hand.kRight)) {
            collector.spit();
        } else {
            collector.stop();
        }
    }

    private int getSign(double val) {
        if (val == 0) return 0;
        if (val < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public void stopAll() {
        drive.tankDrive(0, 0);
        arm.stop();
        collector.stop();
    }
}
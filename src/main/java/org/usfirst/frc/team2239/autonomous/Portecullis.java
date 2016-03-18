package org.usfirst.frc.team2239.autonomous;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team2239.TechnoRobot;

import java.util.Arrays;

/**
 * What to do if in front of the Low Bar
 *
 * @author Technocrats
 */
public class Portecullis extends AutoFunction {
    //timer keeps track of time from the start of the current stage in seconds
    private Timer timer;
    //the current stage of movement
    private int stage;
    //the robot we will be using.
    private TechnoRobot theRobot;
    private double armLiftSpeed, robotMoveSpeed;

    @Override
    public void onStart(TechnoRobot robot) {
        stage = 0;
        timer = new Timer();
        timer.start();
        theRobot = robot;
        robotMoveSpeed = stageTimes[0];
        stageTimes = Arrays.copyOfRange(stageTimes, 1, stageTimes.length);
    }

    @Override
    public void onUpdate() {
        if (stage >= stageTimes.length) return; //if we've passed the last stage, just don't do anything

        if (timer.hasPeriodPassed(stageTimes[stage])) {
            stage++;
            theRobot.stopAll();
        }

        switch (stage) {
            case 0:
                theRobot.drive.accelerateTo(.6, .6); //go forwards
                break;
            case 1:
                theRobot.arm.lift();
                theRobot.drive.accelerateTo(robotMoveSpeed, robotMoveSpeed);
                break;
            case 2:
                theRobot.drive.accelerateTo(.6, .6); //go forwards
                break;
            default:
                //do nothing
        }
    }

    @Override
    protected double[] defaultTimes() {
        return new double[]{.2, .4, 5, 2}; //{robot move speed under gate, come in time, under gate time, rush off time}
    }
}

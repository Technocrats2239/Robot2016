package org.usfirst.frc.team2239.autonomous;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team2239.TechnoRobot;

/**
 * What to do if in front of the Low Bar
 *
 * @author Technocrats
 */
public class Lowbar extends AutoFunction {
    //timer keeps track of time from the start of the current stage in seconds
    private Timer timer;
    //the current stage of movement
    private int stage;
    //the robot we will be using.
    private TechnoRobot theRobot;

    @Override
    public void onStart(TechnoRobot robot) {
        stage = 0;
        timer = new Timer();
        timer.start();
        theRobot = robot;
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
                theRobot.drive.accelerateTo(1, 0); //turn right
                break;
            case 2:
                theRobot.drive.accelerateTo(.6, .6); //go forwards
                break;
            case 3:
                theRobot.collector.spit(); //spit out the ball
                break;
            default:
                //do nothing
        }
    }
}

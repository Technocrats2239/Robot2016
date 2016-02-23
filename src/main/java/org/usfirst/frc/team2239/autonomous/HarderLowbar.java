package org.usfirst.frc.team2239.autonomous;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team2239.TechnoRobot;

/**
 * What to do if in front of the Low Bar
 *
 * @author Technocrats
 */
public class HarderLowbar extends AutoFunction {
    //timer keeps track of time from the start of the init in seconds
    private Timer timer;
    private int stage;

    @Override
    public void onStart(TechnoRobot robot) {
        timer = new Timer();
        timer.start();
        stage = 0;
    }

    @Override
    public void onUpdate() {

    }
}

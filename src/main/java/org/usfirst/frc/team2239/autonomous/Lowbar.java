package org.usfirst.frc.team2239.autonomous;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team2239.TechnoRobot;

/**
 * (description)
 *
 * @author (name)
 */
public class Lowbar extends AutonomousFunction {
    private Timer timer;
    private int stage;
    public Lowbar() {
        super("lowbar");
    }

    @Override
    protected void onStart() {
        timer = new Timer();
        timer.start();
        stage = 0;
    }

    @Override
    protected void onUpdate() {
        TechnoRobot.instance.drive.turnRight();
    }
}

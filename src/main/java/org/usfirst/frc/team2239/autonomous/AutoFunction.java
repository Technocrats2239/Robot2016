package org.usfirst.frc.team2239.autonomous;

import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team2239.TechnoRobot;

/**
 * Each AutoFunction needs to have an initiate function that will take a timer in order to keep track of time
 * It will also have an iterate function that will use the timer initiated in init() to keep track of time.
 *
 *
 * @author London Lowmanstone IV
 */
public abstract class AutoFunction {

    public abstract void onStart(TechnoRobot theRobot);
    public abstract void onUpdate();

}

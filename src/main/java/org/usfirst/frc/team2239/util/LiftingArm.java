package org.usfirst.frc.team2239.util;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * Represents a piece of 8020 that lifts stuff
 *
 * @author Technocrats
 */
public class LiftingArm {
    private VictorSP motor;
    private Timer timer;
    private double speed;

    public LiftingArm(int channel) {
        this.motor = new VictorSP(channel);
        this.timer = new Timer();
        refresh();
    }

    public void refresh() {
        speed = Preferences.getInstance().getDouble("ArmSpeed", .5);
        System.out.println("New arm speed: " + speed);
    }

    public void lift() {
        timer.start();
        motor.set(speed);
    }

    public void drop() {
        timer.start();
        motor.set(-speed);
    }

    public void stop() {
        motor.set(0);
    }
}

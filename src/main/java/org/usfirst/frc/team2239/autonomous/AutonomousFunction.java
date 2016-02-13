package org.usfirst.frc.team2239.autonomous;

import edu.wpi.first.wpilibj.Timer;

import java.util.HashMap;
import java.util.Map;

/**
 * base function to make autonomous functions
 *
 * @author Dean B
 */
public abstract class AutonomousFunction {
    private static Map<String, AutonomousFunction> functions = new HashMap<>();

    public AutonomousFunction(String id) {
        functions.put(id, this);
    }

    protected abstract void onStart();
    protected abstract void onUpdate();
}

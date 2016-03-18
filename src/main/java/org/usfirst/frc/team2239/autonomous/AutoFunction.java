package org.usfirst.frc.team2239.autonomous;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2239.TechnoRobot;

import java.util.Arrays;

/**
 * Each AutoFunction needs to have an initiate function that will take a timer in order to keep track of time
 * It will also have an iterate function that will use the timer initiated in init() to keep track of time.
 *
 *
 * @author Technocrats
 */
public abstract class AutoFunction {
    private static SendableChooser chooser;
    protected double[] stageTimes;
    //create a unique id for the class based on the class' name
    private String id = getClass().getSimpleName();

    protected AutoFunction() {
        //create the SendableChooser object if not already created
        if(chooser == null) {
            chooser = new SendableChooser();
            chooser.addDefault(id, this);
        } else {
            chooser.addObject(id, this);
        }

        stageTimes = defaultTimes();

        Preferences.getInstance().putString(id+"_times", Arrays.toString(stageTimes));
        refresh();
    }

    public static AutoFunction getChosen() {
        return (AutoFunction) chooser.getSelected();
    }

    /**
     * Parses a string into a double array
     *
     * @param stringyArray  the array represented as a string
     * @return              the string represented as an array
     */
    private double[] parse(String stringyArray) {
        return Arrays.stream(stringyArray.substring(1, stringyArray.length()-1).split(",(\\w*)"))
                .filter(s -> !s.isEmpty())          //remove empty strings
                .mapToDouble(Double::parseDouble)   //map string to double
                .toArray();                         //make it an array again
    }

    public void refresh() {
        stageTimes = parse(Preferences.getInstance().getString(id+"_times", "[]"));
    }

    public abstract void onStart(TechnoRobot theRobot);
    public abstract void onUpdate();
    protected abstract double[] defaultTimes();

    public static void register() {
        new DoNothing();
        new Lowbar();
        new Portecullis();
        SmartDashboard.putData("Autonomous mode chooser", chooser);
    }
}

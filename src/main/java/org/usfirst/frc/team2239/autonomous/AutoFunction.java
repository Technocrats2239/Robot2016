package org.usfirst.frc.team2239.autonomous;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2239.TechnoDrive;
import org.usfirst.frc.team2239.TechnoRobot;

import java.util.Arrays;

/**
 * Each AutoFunction needs to have an initiate function that will take a timer in order to keep track of time
 * It will also have an iterate function that will use the timer initiated in init() to keep track of time.
 *
 *
 * @author Dean Bassett and London Lomanstone
 */
public abstract class AutoFunction {
    private static SendableChooser chooser;
    protected double[] stageTimes;
    //create a unique id for the class based on the class' name
    private String id = getClass().getSimpleName();
    protected int stage;
    protected Timer timer;
    protected TechnoRobot robot;
    protected TechnoDrive drive;

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

    public void start(TechnoRobot robot) {
        this.stage = 0;
        this.timer = new Timer();
        this.timer.start();
        this.robot = robot;
        this.drive = robot.drive;
        onStart();
    }

    public void update() {
        if (stage >= stageTimes.length) return; //if we've passed the last stage, just don't do anything

        if (timer.hasPeriodPassed(stageTimes[stage])) {
            stage++;
        }

        onUpdate();
    }

    /**
     * Optional override
     */
    protected void onStart(){}

    /**
     * Each time autonomousPeriodic() gets called
     */
    protected abstract void onUpdate();

    /**
     * Default stage times
     * @return times for the different stages
     */
    protected abstract double[] defaultTimes();

    public static void register() {
        new DoNothing();
        new Lowbar();
        new Portcullis();
        new TeeterTotter();
        new InclinedPlanes();
        new RockWall();
        SmartDashboard.putData("Autonomous mode chooser", chooser);
    }
}

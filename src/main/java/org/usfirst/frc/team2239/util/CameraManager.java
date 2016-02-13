package org.usfirst.frc.team2239.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.util.Arrays;

/**
 * Does stuff based on camera input
 *
 * @author Dean B
 */
public class CameraManager {
    private final NetworkTable table;
    private final Controller controller;

    public CameraManager(NetworkTable table, Controller controller) {
        this.table = table;
        this.controller = controller;
    }

    /**
     * @return whether or not to block other operations
     */
    public boolean update() {
        double[] x1 = table.getNumberArray("x1", (double[]) null);
        //double[] x2 = table.getNumberArray("x2", (double[]) null);
        double[] y1 = table.getNumberArray("y1", (double[]) null);
        double[] y2 = table.getNumberArray("y2", (double[]) null);
        double[] distance = table.getNumberArray("length", (double[]) null);
        Line[] lines = new Line[x1.length];

        for(int i = 0; i < x1.length; i++) {
            lines[i] = new Line(new Point(x1[i], Math.min(y1[i], y2[i])), distance[i], true);
        }

        Arrays.sort(lines);

        lines = new Line[]{lines[0], lines[lines.length - 1]};

        return false;
    }

    class Point {
        private double x;
        private double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    class Line implements Comparable<Line>{
        private final Point p1;
        private final double distance;

        Line(Point p1, double distance, boolean vertical) {
            this.p1 = p1.getY() < p2.getY() ? p1 : p2;
            this.distance = distance;
        }


        public Point getPoint1() {
            return p1;
        }

        public Point getPoint2() {
            return p2;
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public int compareTo(Line o) {
            return Double.compare(this.getPoint1().getX(), o.getPoint1().getX());
        }
    }
}

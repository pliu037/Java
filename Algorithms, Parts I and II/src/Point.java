import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 * Created by pengl on 2015-11-18.
 */
public class Point implements Comparable<Point> {

    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point other) {
        StdDraw.line(x, y, other.x, other.y);
    }

    public int compareTo(Point other) {
        return 0;
    }

    public double slopeTo(Point other) {
        return 0;
    }

    public Comparator<Point> slopeOrder() {
        return null;
    }

}

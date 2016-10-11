import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.TreeSet;

public class PointSet {

    private TreeSet<Point2D> points;

    public PointSet() {
        points = new TreeSet<>();
    }

    public boolean isEmpty(){
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        points.add(p);
    }

    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> included = new ArrayList<>();
        Point2D lowerInc = points.floor(new Point2D(rect.xmin(), rect.ymin()));
        Point2D upperInc = points.ceiling(new Point2D(rect.xmax(), rect.ymax()));
        if (lowerInc == null || upperInc == null) {
            return included;
        }
        NavigableSet<Point2D> candidates = points.subSet(lowerInc, true, upperInc, true);
        for (Point2D check : candidates) {
            if (rect.contains(check)) {
                included.add(check);
            }
        }
        return included;
    }

    public Point2D nearest(Point2D p) {
        if (points.isEmpty()) {
            return null;
        }
        Point2D closest = points.first();
        double minDist = closest.distanceTo(p);
        for (Point2D check : points) {
            if (check.distanceTo(p) < minDist) {
                minDist = check.distanceTo(p);
                closest = check;
            }
        }
        return closest;
    }

    public static void main (String[] args) {
        PointSet test = new PointSet();
        RectHV rect = new RectHV(1, 1, 5, 5);
        test.insert(new Point2D(1, 1));
        test.insert(new Point2D(1, 0));
        test.insert(new Point2D(6, 2));
        test.insert(new Point2D(3, 3));
        test.insert(new Point2D(4, 4));
        test.insert(new Point2D(5, 5));
        test.insert(new Point2D(6, 5));
        System.out.println(test.size());
        test.insert(new Point2D(6, 5));
        System.out.println(test.size());
        System.out.println(test.contains(new Point2D(6, 5)));
        System.out.println(test.contains(new Point2D(6, 4)));
        Iterable<Point2D> results = test.range(rect);
        System.out.println(results);
        System.out.println(test.nearest(new Point2D(0, 0.75)));
    }

}

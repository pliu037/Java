import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class FastCollinearPoints {

    private Point[] points;
    private ArrayList<Point> order;

    public FastCollinearPoints(Point[] points) {
        this.points = points;
        order = new ArrayList<>();
        for (Point p : points) {
            order.add(p);
        }
        Collections.sort(order);
    }

    public ArrayList<ArrayList<Point>> findLines(int num_consec) {
        ArrayList<ArrayList<Point>> lines = new ArrayList<>();
        for (Point check : order) {
            findLine(check, num_consec, lines);
        }
        return lines;
    }

    private void findLine(Point base, int num_consec, ArrayList<ArrayList<Point>> lines) {
        Comparator<Point> cmp = base.slopeOrder();
        Arrays.sort(points, cmp);
        double last_slope = base.slopeTo(points[0]);
        ArrayList<Point> line = new ArrayList<>();
        for (Point p : points) {
            if (base.slopeTo(p) == last_slope) {
                line.add(p);
            }
            else {
                if (line.size() >= num_consec - 1) {
                    line.add(base);
                    Collections.sort(line);
                    if (line.get(0) == base) {
                        lines.add(line);
                    }
                }
                last_slope = base.slopeTo(p);
                line = new ArrayList<>();
                line.add(p);
            }
        }
        if (line.size() >= num_consec) {
            Collections.sort(line);
            if (line.get(0) == base) {
                lines.add(line);
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(".\\Algorithms, Parts I and II\\collinear\\test1.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i ++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        FastCollinearPoints test = new FastCollinearPoints(points);
        ArrayList<ArrayList<Point>> result = test.findLines(3);
        for (ArrayList<Point> al : result) {
            System.out.println(al);
        }
        System.out.println(result.size());
    }

}

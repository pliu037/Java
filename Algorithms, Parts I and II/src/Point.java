import java.util.Comparator;

/**
 * Created by pengl on 2015-11-18.
 */
public class Point implements Comparable<Point> {

    static class PointComparator implements Comparator<Point> {

        private Point ref;

        public PointComparator(Point ref) {
            this.ref = ref;
        }

        public int compare(Point a, Point b) {
            double check = ref.slopeTo(a) - ref.slopeTo(b);
            if (check < 0) {
                return - 1;
            }
            else if (check > 0) {
                return 1;
            }
            else {
                return 0;
            }
        }

    }

    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int compareTo(Point other) {
        if (y < other.y) {
            return -1;
        }
        else if (y > other.y) {
            return 1;
        }
        else if (x < other.x) {
            return -1;
        }
        else if (x > other.x) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public double slopeTo(Point other) {
        double diff_x = other.x - x, diff_y = other.y - y;
        if (diff_x == 0 && diff_y == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        else if (diff_y == 0) {
            return 0;
        }
        else if (diff_x == 0) {
            return Double.POSITIVE_INFINITY;
        }
        else {
            return diff_y/diff_x;
        }
    }

    public Comparator<Point> slopeOrder() {
        return new PointComparator(this);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        Point a = new Point(0, 0), b = new Point(1,1), c = new Point(3, 4), d = new Point(0, 5);
        System.out.println(a.slopeTo(b));
        System.out.println(a.slopeTo(c));
        System.out.println(a.slopeTo(d));
        System.out.println(a.slopeTo(a));
    }

}

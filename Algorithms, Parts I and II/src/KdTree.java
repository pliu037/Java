import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class KdTree {

    private class KdNode<E> {

        private final double value;
        private KdNode<E> left, right;
        private final E data;

        private KdNode(double value, E data) {
            this.value = value;
            this.data = data;
        }
    }

    private final int dimensions;
    private KdNode<Point2D> root;
    private int size = 0;

    public KdTree(int dimensions) {
        this.dimensions = dimensions;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        double[] vector = new double[]{p.x(), p.y()};
        int level = 0;
        if (root == null) {
            root = new KdNode<>(vector[level], p);
            size ++;
            return;
        }
        KdNode<Point2D> current = root;
        while (current != null) {
            if (p.equals(current.data)) {
                return;
            }
            if (vector[level % dimensions] < current.value) {
                if (current.left == null) {
                    current.left = new KdNode<>(vector[(level + 1) % dimensions], p);
                    size ++;
                    return;
                }
                current = current.left;
            }
            else {
                if (current.right == null) {
                    current.right = new KdNode<>(vector[(level + 1) % dimensions], p);
                    size ++;
                    return;
                }
                current = current.right;
            }
            level ++;
        }
    }

    public boolean contains(Point2D p) {
        if (root == null) {
            return false;
        }
        int level = 0;
        double[] vector = new double[]{p.x(), p.y()};
        KdNode<Point2D> current = root;
        while (current != null) {
            if (p.equals(current.data)) {
                return true;
            }
            if (vector[level % dimensions] < current.value) {
                current = current.left;
            }
            else {
                current = current.right;
            }
            level ++;
        }
        return false;
    }

    public void printTree() {
        KdNode<Point2D> emptyNode = null;
        Deque<KdNode<Point2D>> currentLevel = new Deque<>(), nextLevel;
        if (root != null) {
            currentLevel.addLast(root);
        }
        while (!currentLevel.isEmpty()) {
            nextLevel = new Deque<>();
            while (!currentLevel.isEmpty()) {
                KdNode<Point2D> current = currentLevel.removeFirst();
                if (current != emptyNode) {
                    System.out.print(current.data + " ");
                    if (current.left != null) {
                        nextLevel.addLast(current.left);
                    }
                    else {
                        nextLevel.addLast(emptyNode);
                    }
                    if (current.right != null) {
                        nextLevel.addLast(current.right);
                    }
                    else {
                        nextLevel.addLast(emptyNode);
                    }
                }
                else {
                    System.out.print("* ");
                }
            }
            System.out.println();
            currentLevel = nextLevel;
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> included = new ArrayList<>();
        int dim = 0;
        double[] minVector = new double[]{rect.xmin(), rect.ymin()};
        double[] maxVector = new double[]{rect.xmax(), rect.ymax()};
        Deque<KdNode<Point2D>> currentLevel = new Deque<>(), nextLevel;
        if (root != null) {
            currentLevel.addLast(root);
        }
        while (!currentLevel.isEmpty()) {
            nextLevel = new Deque<>();
            while (!currentLevel.isEmpty()) {
                KdNode<Point2D> current = currentLevel.removeFirst();
                if (current.value < minVector[dim]) {
                    if (current.right != null) {
                        nextLevel.addLast(current.right);
                    }
                }
                else if (current.value > maxVector[dim]) {
                    if (current.left != null) {
                        nextLevel.addLast(current.left);
                    }
                }
                else {
                    if (rect.contains(current.data)) {
                        included.add(current.data);
                    }
                    if (current.right != null) {
                        nextLevel.addLast(current.right);
                    }
                    if (current.left != null) {
                        nextLevel.addLast(current.left);
                    }
                }
            }
            currentLevel = nextLevel;
            dim = (dim + 1)%dimensions;
        }
        return included;
    }

    public Point2D nearest(Point2D p) {
        double[] vector = new double[]{p.x(), p.y()};
        return closestSubtree(p, vector, root, null, 0);
    }

    private Point2D closestSubtree(Point2D p, double[] vector, KdNode<Point2D> root, Point2D tentativeClosest, int level) {
        if (root == null) {
            return tentativeClosest;
        }
        Deque<KdNode<Point2D>> path = new Deque<>();
        KdNode<Point2D> current = root;
        int dim = level % dimensions;
        while (current != null) {
            path.addLast(current);
            if (vector[dim] < current.value) {
                current = current.left;
            }
            else {
                current = current.right;
            }
            dim = (dim + 1)%dimensions;
        }

        double closestSqDistance = tentativeClosest == null ? Double.POSITIVE_INFINITY : tentativeClosest.distanceSquaredTo(p);
        while (!path.isEmpty()) {
            KdNode<Point2D> check = path.removeLast();
            if (Math.pow(vector[(path.size() + level)%dimensions] - check.value, 2) < closestSqDistance) {
                if (check.data.distanceSquaredTo(p) < closestSqDistance) {
                    tentativeClosest = check.data;
                    closestSqDistance = check.data.distanceSquaredTo(p);
                }
                if (vector[(path.size() + level)%dimensions] < check.value) {
                    check = check.right;
                }
                else {
                    check = check.left;
                }
                tentativeClosest = closestSubtree(p, vector, check, tentativeClosest, path.size() + level + 1);
            }
        }
        return tentativeClosest;
    }

    public static void main (String[] args) {
        KdTree test = new KdTree(2);
        PointSet test2 = new PointSet();
        /*RectHV rect = new RectHV(1, 1, 5, 5);
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
        test.printTree();
        Iterable<Point2D> results = test.range(rect);
        System.out.println(results);
        System.out.println(test.nearest(new Point2D(3.5, 3.4)));*/
        In in = new In(".\\Algorithms, Parts I and II\\input1M.txt");
        for (int i = 0; i < 1000000; i ++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D cur = new Point2D(x, y);
            test.insert(cur);
            test2.insert(cur);
        }
        System.out.println("Data Loaded: " + test.size() + " points");
        System.out.println(test.nearest(new Point2D(0.21, 0.75)));
        System.out.println(test.nearest(new Point2D(0.65, 0.23)));
        System.out.println(test.nearest(new Point2D(0.89, 0.46)));
        System.out.println(test2.nearest(new Point2D(0.21, 0.75)));
        System.out.println(test2.nearest(new Point2D(0.65, 0.23)));
        System.out.println(test2.nearest(new Point2D(0.89, 0.46)));
    }

}

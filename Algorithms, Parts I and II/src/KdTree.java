import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

/**
 * Created by pengl on 1/21/2016.
 */
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
        if (isEmpty()) {
            return null;
        }
        int dim = 0;
        double[] vector = new double[]{p.x(), p.y()};
        Deque<KdNode<Point2D>> path = new Deque<>();
        KdNode<Point2D> current = root;
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
        Point2D closestPoint = path.peekLast().data;
        double closestSqDistance = closestPoint.distanceSquaredTo(p);
        while (!path.isEmpty()) {
            KdNode<Point2D> check = path.removeLast();
            //Whether to check a given subtree (make sure only the non-traversed one is checked)
            if (Math.pow(vector[path.size()%dimensions] - check.value, 2) < closestSqDistance) {
                if (vector[path.size()%dimensions] < check.value) {
                    check = check.right;
                }
                else {
                    check = check.left;
                }
                Point2D newClosest = closestSubtree(path.size() + 1, check, p, closestSqDistance);
                if (newClosest != null) {
                    closestPoint = newClosest;
                    closestSqDistance = closestPoint.distanceSquaredTo(p);
                }
            }
        }
        return closestPoint;
    }

    private Point2D closestSubtree(int dim, KdNode<Point2D> subtreeRoot, Point2D p, double closestSqDistance) {
        double[] vector = new double[]{p.x(), p.y()};
        Point2D newClosest = null;
        Deque<KdNode<Point2D>> currentLevel = new Deque<>(), nextLevel;
        currentLevel.addLast(subtreeRoot);
        while (!currentLevel.isEmpty()) {
            nextLevel = new Deque<>();
            while (!currentLevel.isEmpty()) {
                KdNode<Point2D> check = currentLevel.removeFirst();
            }
            currentLevel = nextLevel;
            dim ++;
        }
        return newClosest;
    }

    public static void main (String[] args) {
        KdTree test = new KdTree(2);
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
        //test.printTree();
        Iterable<Point2D> results = test.range(rect);
        System.out.println(results);
        System.out.println(test.nearest(new Point2D(0, 0.75)));
    }

}

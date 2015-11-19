import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.Math;

/**
 * Created by pengl on 2015-11-11.
 */
public class SeamCarver {

    private Picture pic;

    public SeamCarver(Picture picture) {               // create a seam carver object based on the given picture
        this.pic = new Picture(picture);
    }

    public Picture picture() {                         // current picture
        return pic;
    }

    public int width() {                               // width of current picture
        return pic.width();
    }

    public int height() {                              // height of current picture
        return pic.height();
    }

    public double energy(int x, int y) {               // energy of pixel at column x and row y
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 1000;
        }
        else {
            Color top = pic.get(x, y - 1);
            Color bottom = pic.get(x, y + 1);
            Color left = pic.get(x - 1, y);
            Color right = pic.get(x + 1, y);
            double sum = 0;
            sum += Math.pow(top.getBlue() - bottom.getBlue(), 2);
            sum += Math.pow(top.getGreen() - bottom.getGreen(), 2);
            sum += Math.pow(top.getRed() - bottom.getRed(), 2);
            sum += Math.pow(left.getBlue() - right.getBlue(), 2);
            sum += Math.pow(left.getGreen() - right.getGreen(), 2);
            sum += Math.pow(left.getRed() - right.getRed(), 2);
            return Math.sqrt(sum);
        }
    }

    public int[] findHorizontalSeam() {                // sequence of indices for horizontal seam
        double[][] matrix = new double[width()][height()];
        for (int x = 0; x < width(); x ++) {
            for (int y = 0; y < height(); y ++) {
                matrix[x][y] = energy(x, y);
            }
        }
        return findShortest(matrix);
    }

    public int[] findVerticalSeam() {                  // sequence of indices for vertical seam
        double[][] matrix = new double[height()][width()];
        for (int x = 0; x < width(); x ++) {
            for (int y = 0; y < height(); y ++) {
                matrix[y][x] = energy(x, y);
            }
        }
        return findShortest(matrix);
    }

    private int[] findShortest(double[][] matrix) {
        for (int x = matrix.length - 2; x >= 0; x --) {
            for (int y = 0; y < matrix[x].length; y++) {
                matrix[x][y] = matrix[x][y] + findMin(matrix, x, y);
            }
        }
        int[] seam = new int[matrix.length];
        Double current_value = Double.MAX_VALUE;
        int current_pos = -1;
        for (int y = 0; y < matrix[0].length; y ++) {
            if (matrix[0][y] < current_value) {
                current_value = matrix[0][y];
                current_pos = y;
            }
        }
        seam[0] = current_pos;
        for (int x = 1; x < matrix.length; x ++) {
            current_value = Double.MAX_VALUE;
            int y = current_pos;
            if (y > 0 && matrix[x][y - 1] < current_value) {
                current_value = matrix[x][y - 1];
                current_pos = y - 1;
            }
            if (matrix[x][y] < current_value) {
                current_value = matrix[x][y];
                current_pos = y;
            }
            if (y + 1 < matrix[x].length && matrix[x][y + 1] < current_value) {
                current_pos = y + 1;
            }
            seam[x] = current_pos;
        }
        return seam;
    }

    private double findMin(double[][] matrix, int x, int y) {
        Double min = Double.MAX_VALUE;
        if (y > 0 && matrix[x + 1][y - 1] < min) {
            min = matrix[x + 1][y - 1];
        }
        if (matrix[x + 1][y] < min) {
            min = matrix[x + 1][y];
        }
        if (y + 1 < matrix[x + 1].length && matrix[x + 1][y + 1] < min) {
            min = matrix[x + 1][y + 1];
        }
        return min;
    }

    public void removeHorizontalSeam(int[] seam) {     // remove horizontal seam from current picture
        Picture new_pic = new Picture(width(), height() - 1);
        for (int x = 0; x < width(); x ++) {
            for(int y = 0; y < height(); y ++) {
                if (y != seam[x]) {
                    if (y < seam[x]) {
                        new_pic.set(x, y, pic.get(x, y));
                    }
                    else {
                        new_pic.set(x, y - 1, pic.get(x, y));
                    }
                }
            }
        }
        pic = new_pic;
    }

    public void removeVerticalSeam(int[] seam) {       // remove vertical seam from current picture
        Picture new_pic = new Picture(width() - 1, height());
        for (int y = 0; y < height(); y ++) {
            for(int x = 0; x < width(); x ++) {
                if (x != seam[y]) {
                    if (x < seam[y]) {
                        new_pic.set(x, y, pic.get(x, y));
                    }
                    else {
                        new_pic.set(x - 1, y, pic.get(x, y));
                    }
                }
            }
        }
        pic = new_pic;
    }

    public void save() {
        pic.save("D:\\Work\\Programming\\Java\\Algorithms, Parts I and II\\seamCarving\\out.jpg");
    }

    public static void main(String[] args) {
        SeamCarver test = new SeamCarver(new Picture ("D:\\Work\\Programming\\Java\\Algorithms, Parts I and II\\seamCarving\\ZhGEqAP.jpg"));
        for (int num = 0; num < 1000; num ++) {
            int[] seam = test.findVerticalSeam();
            for (int i = 0; i < seam.length; i++) {
                System.out.print(seam[i] + " ");
            }
            System.out.println();
            test.removeVerticalSeam(seam);
        }
        test.save();
    }

}

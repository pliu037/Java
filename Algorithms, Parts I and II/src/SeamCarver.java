import edu.princeton.cs.algs4.Picture;

import java.awt.*;
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
        return null;
    }

    public int[] findVerticalSeam() {                  // sequence of indices for vertical seam
        return null;
    }

    private int[] findShortest(int[][] matrix) {

        return null;
    }

    public void removeHorizontalSeam(int[] seam) {     // remove horizontal seam from current picture
        for (int x = 0; x < pic.width(); x ++) {

        }
    }

    public void removeVerticalSeam(int[] seam) {       // remove vertical seam from current picture

    }

}

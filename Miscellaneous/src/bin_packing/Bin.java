package bin_packing;

import java.util.ArrayList;

import static bin_packing.BinPacking.PRECISION;

/*
Each Bin keeps track of how much remaining space it has
 */
public class Bin {

    private double remaining;
    private ArrayList<Double> objects = new ArrayList<>();

    public Bin(double capacity) {
        remaining = capacity;
    }

    public void putObject(double size) {
        remaining -= size;
        if (Math.abs(remaining) < PRECISION) {
            remaining = 0;
        }
        objects.add(size);
    }

    public double getRemaining() {
        return remaining;
    }

    public boolean fits(double size) {
        return remaining - size >= -PRECISION;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(remaining);
        sb.append(": {");
        for (Double d : objects) {
            sb.append(d);
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }
}

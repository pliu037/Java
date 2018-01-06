package bin_packing;

import java.util.ArrayList;

import static bin_packing.BinPacking.PRECISION;

/*
A BinSet holds Bins with the same remaining space
 */
public class BinSet implements Comparable<BinSet> {
    private double key;
    private ArrayList<Bin> bins = new ArrayList<>();

    public BinSet(double key) {
        this.key = key;
    }

    public void addBin(Bin b) {
        bins.add(b);
    }

    public Bin popBin() {
        return bins.remove(bins.size() - 1);
    }

    public boolean isEmpty() {
        return bins.isEmpty();
    }

    public String toString() {
        return key + ": " + bins.size();
    }

    @Override
    public int compareTo(BinSet binSet) {
        if (this.key - binSet.key > PRECISION) {
            return 1;
        } else if (this.key - binSet.key < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}

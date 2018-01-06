package bin_packing;

/*
Each Bin keeps track of how much remaining space it has
 */
public class Bin {

    public double remaining;

    public Bin(double capacity) {
        remaining = capacity;
    }
}

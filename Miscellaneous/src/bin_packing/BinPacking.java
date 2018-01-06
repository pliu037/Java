package bin_packing;

public class BinPacking {

    // The tolerance for double arithmetic
    public static final double PRECISION = 0.00000000000001;

    private final BinPool bins;

    private double totalWeight = 0;

    /**
     * @param bins A concrete BinPool instance (used to pass in different Bin management behaviour)
     */
    public BinPacking(BinPool bins) {
        this.bins = bins;
    }

    /**
     * Simulates bin packing given an array of objects
     * @param sizes An array of doubles, representing the sizes of the objects to be packed
     */
    public void pack(double[] sizes) {
        for (double size : sizes) {
            try {
                bins.put(size);
                totalWeight += size;
            } catch (Error e) {
                System.out.println(e.getMessage());
            }
            bins.printBins();
            System.out.println("-----");
        }
        System.out.println("Total weight packed: " + totalWeight + " Number of bins: " + bins.getNumBins());
    }

    public static void main(String[] args) {
        // Change the instantiated BinPool class here
        BinPacking bp = new BinPacking(new ListBinPoolImpl(1));

        // Modify the order of object sizes to be packed here
        bp.pack(new double[]{0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.6, 1, 1.0001, 0.5, 0.3, 0.2});

    }
}

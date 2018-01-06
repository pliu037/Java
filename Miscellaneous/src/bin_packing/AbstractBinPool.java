package bin_packing;

/*
The abstract class responsible for managing Bins (e.g.: adding new objects into
the pool of Bins)
 */
public abstract class AbstractBinPool {

    protected final double binCapacity;
    protected int numBins = 0;

    protected AbstractBinPool(double binCapacity) {
        this.binCapacity = binCapacity;
    }

    /**
     * Puts an object into the pool of Bins
     * @param size The size of the object to add
     */
    public abstract void put(double size);

    /**
     * Prints the remaining space in each Bin
     */
    public abstract void printBins();

    /**
     * @return The number of Bins
     */
    public int getNumBins() {
        return numBins;
    }
}

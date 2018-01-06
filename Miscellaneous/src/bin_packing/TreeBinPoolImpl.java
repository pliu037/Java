package bin_packing;

import java.util.TreeSet;

/*
A tree-based implementation of BinPool.

The Bins are stored in BinSets, with each BinSet holding all Bins with the same
remaining space. The BinSets are stored in a balanced search tree, keyed by
remaining space. Whenever an object is added, the BinSet with the least remaining
space that is greater than or equal to object's size is retrieved from the tree.
A Bin is removed from this BinSet (if there is no BinSet with remaining space
greater than or equal to the object's size, a new Bin is created), the size of the
object is deducted from the Bin's remaining space, and the Bin is then placed into
the BinSet representing its new remaining space.

Finding a Bin that has enough space to fit the object and inserting a new Bin in
this implementation both take O(log n) time (where n is the number of Bins).
Therefore, adding an object takes O(log n) time.
 */
public class TreeBinPoolImpl extends BinPool {
    private TreeSet<BinSet> binTree = new TreeSet<>();

    public TreeBinPoolImpl(double binCapacity) {
        super(binCapacity);
    }

    @Override
    public void put(double size) {
        if (size > binCapacity) {
            throw new Error("Object size larger than bin capacity!");
        }

        Bin b = getBin(size);
        b.putObject(size);
        putBin(b);
    }

    private Bin getBin(double size) {
        BinSet bs = binTree.ceiling(new BinSet(size));
        Bin b;

        if (bs == null) {
            b = new Bin(binCapacity);
            numBins++;
        } else {
            b = bs.popBin();
            if (bs.isEmpty()) {
                binTree.remove(bs);
            }
        }

        return b;
    }

    private void putBin(Bin b) {
        BinSet query = new BinSet(b.getRemaining());
        BinSet bs;

        if (binTree.contains(query)) {
            bs = binTree.ceiling(query);
        } else {
            bs = new BinSet(b.getRemaining());
        }

        bs.addBin(b);
        binTree.add(bs);
    }

    @Override
    public void printBins() {
        for (BinSet bs : binTree) {
            System.out.println(bs);
        }
    }
}

package bin_packing;

/*
A list-based implementation of BinPool.

The Bins are stored in a singly-linked list in no particular order. Whenever an
object is added, the list is traversed, with the object being put in the first
Bin that has enough room for it. If no such Bin exists, a new Bin is created and
appended to the start of the list.

Finding a Bin that has enough space to fit the object takes O(n) time in this
implementation whereas inserting a new Bin in takes O(1) time (where n is the
number of Bins). Therefore, adding an object takes O(n) time.
 */
public class ListBinPoolImpl extends BinPool {
    private static class Node {
        private Node next = null;
        private Bin bin;

        private Node(Bin b) {
            bin = b;
        }
    }

    private Node head = null;

    public ListBinPoolImpl(double binCapacity) {
        super(binCapacity);
    }

    @Override
    public void put(double size) {
        if (size > binCapacity) {
            throw new Error("Object size larger than bin capacity!");
        }

        Node pointer = head;
        while (pointer != null && !pointer.bin.fits(size)) {
            pointer = pointer.next;
        }

        Bin b;
        if (pointer == null) {
            b = new Bin(binCapacity);
            numBins++;
            Node n = new Node(b);
            n.next = head;
            head = n;
        } else {
            b = pointer.bin;
        }

        b.putObject(size);
    }

    @Override
    public void printBins() {
        Node pointer = head;
        while (pointer != null) {
            System.out.println(pointer.bin);
            pointer = pointer.next;
        }
    }
}

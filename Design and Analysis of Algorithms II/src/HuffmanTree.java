import java.util.concurrent.LinkedBlockingQueue;
import java.util.Collections;
import java.util.ArrayList;
import DataStructs.HashTable;

public final class HuffmanTree<E> extends EncodingTree<E> {
	
	private static final String NEG_FREQ = "Frequency must be non-negative!";
	private static final String UNMATCHED_SYMBOL_FREQ = "Each symbol must be accompanied by a frequency!";
	
	private HuffmanTree (int encoding_size, ArrayList<E> list, ArrayList<Double> freqs) {
		makeTree(ComparableAdaptor.makeList(freqs, StaticNode.makeNodes(list)), encoding_size);
	}
	
	public static <T> HuffmanTree<T> getHuffmanTree (ArrayList<T> list, ArrayList<Double> freqs) {
		if (checkError(list, freqs)) {
			return null;
		}
		return new HuffmanTree<T>(SMALLEST_ENCODING_SIZE, list, freqs);
	}
	
	public static <T> HuffmanTree<T> getHuffmanTree (int encoding_size, ArrayList<T> list, ArrayList<Double> freqs) {
		if (encoding_size < SMALLEST_ENCODING_SIZE || checkError(list, freqs)) {
			if (encoding_size < SMALLEST_ENCODING_SIZE) {
				System.out.println(ENCODING_SIZE_TOO_SMALL);
			}
			return null;
		}
		return new HuffmanTree<T>(encoding_size, list, freqs);
	}
	
	public static <T> boolean checkError (ArrayList<T> list, ArrayList<Double> freqs) {
		if (list == null || freqs == null || list.size() != freqs.size() || list.size() < 1) {
			if (list == null || freqs == null) {
				System.out.println(NULL_INPUT);
			}
			else {
				if (list.size() != freqs.size()) {
					System.out.println(UNMATCHED_SYMBOL_FREQ);
				}
				if (list.size() < 1) {
					System.out.println(ALPHABET_TOO_SMALL);
				}
			}
			return true;
		}
		HashTable<T, Boolean> ht = new HashTable<>();
		boolean flag = false;
		for (int i = 0; i < list.size(); i ++) {
			if (list.get(i) == null) {
				System.out.println(NULL_INPUT);
				flag = true;
			}
			else if (!ht.add(list.get(i), true)) {
				System.out.println(EncodingTree.DUPLICATE_SYMBOL(list.get(i)));
				flag = true;
			}
			if (freqs.get(i) == null) {
				System.out.println(NULL_INPUT);
				flag = true;
			}
			else if (freqs.get(i) < 0) {
				System.out.println(NEG_FREQ);
				flag = true;
			}
		}
		return flag;
	}
	
	private ComparableAdaptor<Double, StaticNode<E>> mergeNodes (int to_merge, LinkedBlockingQueue<ComparableAdaptor<Double, StaticNode<E>>> singletons, LinkedBlockingQueue<ComparableAdaptor<Double, StaticNode<E>>> mergeds) {
		ArrayList<StaticNode<E>> children = new ArrayList<>();
		double total_freq = 0;
		for (int i = 0; i < to_merge; i ++) {
			ComparableAdaptor<Double, StaticNode<E>> check_singletons = singletons.peek(), check_merged = mergeds.peek();
			if (check_merged == null || check_merged.compareTo(check_singletons) > 0) {
				children.add(check_singletons.getValue());
				total_freq += singletons.poll().getKey();
			}
			else {
				children.add(check_merged.getValue());
				total_freq += mergeds.poll().getKey();
			}
		}
		ComparableAdaptor<Double, StaticNode<E>> parent = new ComparableAdaptor<>(total_freq, new StaticNode<>(null, children));
		for (int i = 0; i < children.size(); i ++) {
			children.get(i).setParent(parent.getValue(), i);
		}
		mergeds.add(parent);
		return parent;
	}
	
	private void makeTree (ArrayList<ComparableAdaptor<Double, StaticNode<E>>> nodes, int encoding_size) {
		Collections.sort(nodes);
		LinkedBlockingQueue<ComparableAdaptor<Double, StaticNode<E>>> singletons = new LinkedBlockingQueue<>(nodes), mergeds = new LinkedBlockingQueue<>();
		ArrayList<StaticNode<E>> children = new ArrayList<>();
		children.add(singletons.peek().getValue());
		ComparableAdaptor<Double, StaticNode<E>> parent = new ComparableAdaptor<>(0.0, new StaticNode<>(null, children));
		if (singletons.size() == 1) {
			children.get(0).setParent(parent.getValue(), 0);
		}
		else {
			int over = (singletons.size()-1)%(encoding_size-1);
			if (over > 0) {
				parent = mergeNodes (over+1, singletons, mergeds);
			}
			while (singletons.size() + mergeds.size() > 1) {
				parent = mergeNodes (encoding_size, singletons, mergeds);
			}
		}
		setRoot(parent.getValue());
	}
}

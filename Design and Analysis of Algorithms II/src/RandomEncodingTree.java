import java.util.ArrayList;
import java.util.Random;
import DataStructs.HashTable;

public final class RandomEncodingTree<E> extends EncodingTree<E>{
	
	private RandomEncodingTree (int encoding_size, ArrayList<E> symbols) {
		makeTree(StaticNode.makeNodes(symbols), encoding_size);
	}
	
	public static <T> RandomEncodingTree<T> getRandomEncodingTree (ArrayList<T> symbols) {
		if (checkError(symbols)) {
			return null;
		}
		return new RandomEncodingTree<T>(SMALLEST_ENCODING_SIZE, symbols);
	}
	
	public static <T> RandomEncodingTree<T> getRandomEncodingTree (int encoding_size, ArrayList<T> symbols) {
		if (encoding_size < SMALLEST_ENCODING_SIZE || checkError(symbols)) {
			if (encoding_size < SMALLEST_ENCODING_SIZE) {
				System.out.println(ENCODING_SIZE_TOO_SMALL);
			}
			return null;
		}
		return new RandomEncodingTree<T>(encoding_size, symbols);
	}
	
	public static <T> boolean checkError (ArrayList<T> stream) {
		if (stream == null || stream.size() < 1) {
			if (stream == null) {
				System.out.println(NULL_INPUT);
			}
			else if (stream.size() < 1) {
				System.out.println(ALPHABET_TOO_SMALL);
			}
			return true;
		}
		HashTable<T, Boolean> ht = new HashTable<>();
		boolean flag = false;
		for (int i = 0; i < stream.size(); i ++) {
			if (stream.get(i) == null) {
				System.out.println(NULL_INPUT);
				flag = true;
			}
			else if (!ht.add(stream.get(i), true)) {
				System.out.println(EncodingTree.DUPLICATE_SYMBOL(stream.get(i)));
				flag = true;
			}
		}
		return flag;
	}
	
	private StaticNode<E> mergeNodes (int to_merge, ArrayList<StaticNode<E>> nodes) {
		Random gen = new Random();
		ArrayList<StaticNode<E>> children = new ArrayList<>();
		for (int i = 0; i < to_merge; i ++) {
			children.add(Utilities.removeElement(nodes, gen.nextInt(nodes.size())));
		}
		StaticNode<E> parent = new StaticNode<>(null, children);
		for (int i = 0; i < children.size(); i ++) {
			children.get(i).setParent(parent, i);
		}
		nodes.add(parent);
		return parent;
	}
	
	private void makeTree (ArrayList<StaticNode<E>> nodes, int encoding_size) {
		ArrayList<StaticNode<E>> children = new ArrayList<>();
		children.add(nodes.get(0));
		StaticNode<E> parent = new StaticNode<>(null, children);
		if (nodes.size() == 1) {
			children.get(0).setParent(parent, 0);
		}
		else {
			int over = (nodes.size()-1)%(encoding_size-1);
			if (over > 0) {
				parent = mergeNodes (over+1, nodes);
			}
			while (nodes.size() > 1) {
				parent = mergeNodes (encoding_size, nodes);
			}
		}
		setRoot(parent);
	}
}

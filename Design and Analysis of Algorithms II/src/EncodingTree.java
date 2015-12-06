import java.util.ArrayList;
import java.util.Collections;
import DataStructs.HashTable;

public class EncodingTree<E> extends StaticTree<E> implements Encoder<E>{
	
	private final HashTable<E, StaticNode<E>> alphabet;
    private final HashTable<E, ArrayList<Integer>> cache = new HashTable<>();

	private static final String ROOT_WITH_SYMBOL = "Root node cannot contain a symbol!";
	private static final String LEAVES_WITH_CHILDREN = "Leaves cannot have children!";
	protected static final String ALPHABET_TOO_SMALL = "Alphabet size must be at least 1!";
	protected static final String ENCODING_SIZE_TOO_SMALL = "Encoding size must be at least 2!";
	protected static final int SMALLEST_ENCODING_SIZE = 2;
	
	private static final <T> String SYMBOL_NOT_FOUND (T symbol) {
		return (symbol + " was not found in the alphabet!");
	}
	
	private static final String SYMBOL_NOT_FOUND (ArrayList<Integer> code) {
		return ("The encoded message contains the symbol fragment " + code);
	}
	
	protected static final <T> String DUPLICATE_SYMBOL (T symbol) {
		return ("Duplicate symbol, " + symbol + ", detected!");
	}
	
	protected EncodingTree () {
		alphabet = new HashTable<>();
	}
	
	private EncodingTree (StaticNode<E> root, HashTable<E, StaticNode<E>> alphabet) {
		this.alphabet = alphabet;
		super.setRoot(root);
	}
	
	public static <T> EncodingTree<T> getEncodingTree (StaticNode<T> root) {
		HashTable<T, StaticNode<T>> alphabet = new HashTable<>();
		if (!isValid(root, alphabet)) {
			return null;
		}
		return new EncodingTree<T>(root, alphabet);
	}
	
	public static <T> boolean isValid (StaticNode<T> root, HashTable<T, StaticNode<T>> alphabet) {
		if (root == null) {
			System.out.println(NO_ROOT);
			return false;
		}
		if (alphabet == null || alphabet.getSize() > 0) {
			alphabet = new HashTable<>();
		}
		HashTable<StaticNode<T>, Boolean> ht = new HashTable<>();
		boolean flag = true;
		if (root.getData() != null) {
			System.out.println(ROOT_WITH_SYMBOL);
			flag = false;
		}
		ArrayList<StaticNode<T>> list = new ArrayList<>();
		list.add(root);
		while (!list.isEmpty()) {
			StaticNode<T> current = list.remove(list.size()-1);
			if (current == null) {
				System.out.println(NULL_INPUT);
				flag = false;
			}
			else {
				if(!ht.add(current, true)) {
					System.out.println(HAS_CYCLES);
					return false;
				}
				ArrayList<StaticNode<T>> children = current.getChildren();
				T data = current.getData();
				if (data != null) {
					if (children.size() > 0) {
						System.out.println(LEAVES_WITH_CHILDREN);
						flag = false;
					}
					if (!alphabet.add(data, current)) {
						System.out.println(DUPLICATE_SYMBOL(data));
						flag = false;
					}
				}
				else {
					for (int i = 0; i < children.size(); i ++) {
						StaticNode<T> child = children.get(i);
						if (child != StaticNode.POISON_NODE) {
							if (child.getParent() != current) {
								System.out.println(PARENT_CHILD_MISMATCH);
								flag = false;
							}
							list.add(child);
						}
					}
				}
			}
		}
		if (alphabet.getSize() < 1) {
			System.out.println(ALPHABET_TOO_SMALL);
			flag = false;
		}
		return flag;
	}
	
	protected boolean setRoot (StaticNode<E> root) {
		if (isValid(root, alphabet)) {
			return super.setRoot(root);
		}
		return false;
	}
	
	private final ArrayList<Integer> encodeSymbol (StaticNode<E> base) {
		ArrayList<Integer> output = new ArrayList<>();
		while (base != getRoot()) {
			output.add(base.getParentLink());
			base = base.getParent();
		}
		Collections.reverse(output);
		return output;
	}
	
	public final ArrayList<Integer> encode (ArrayList<E> stream) {
		if (getRoot() == null || stream == null || stream.contains(null)) {
			if (getRoot() == null) {
				System.out.println(NO_ROOT);
			}
			if (stream == null || stream.contains(null)) {
				System.out.println(NULL_INPUT);
			}
			return null;
		}
		ArrayList<Integer> output = new ArrayList<>();
		for (int i = 0; i < stream.size(); i ++) {
            if (cache.peek(stream.get(i)) == null) {
                StaticNode<E> base = alphabet.peek(stream.get(i));
                if (base == null) {
                    System.out.println(SYMBOL_NOT_FOUND(stream.get(i)));
                    return null;
                }
                cache.add(stream.get(i), encodeSymbol(base));
            }
			output.addAll(cache.peek(stream.get(i)));
		}
		return output;
	}
	
	public final ArrayList<E> decode (ArrayList<Integer> stream) {
		if (getRoot() == null || stream == null || stream.contains(null)) {
			if (getRoot() == null) {
				System.out.println(NO_ROOT);
			}
			if (stream == null || stream.contains(null)) {
				System.out.println(NULL_INPUT);
			}
			return null;
		}
		ArrayList<E> output = new ArrayList<>();
		StaticNode<E> current = getRoot();
		for (int i = 0; i < stream.size(); i ++) {
			if (stream.get(i) >= current.getChildren().size()) {
				ArrayList<Integer> error = encodeSymbol(current);
				error.add(stream.get(i));
				System.out.println(SYMBOL_NOT_FOUND(error));
				return null;
			}
			current = current.getChildren().get(stream.get(i));
			if (current.getData() != null) {
				output.add(current.getData());
				current = getRoot();
			}
		}
		if (current != getRoot()) {
			System.out.println(SYMBOL_NOT_FOUND(encodeSymbol(current)));
		}
		return output;
	}
	
	public final ArrayList<E> getAlphabet () {
		return alphabet.getNames();
	}
	
	public static void main(String[] args) {
		ArrayList<Double> list = new ArrayList<>();
		list.add(0.08167);
		list.add(0.01492);
		list.add(0.02782);
		list.add(0.04253);
		list.add(0.12702);
		list.add(0.02228);
		list.add(0.02015);
		list.add(0.06094);
		list.add(0.06966);
		list.add(0.00153);
		list.add(0.00772);
		list.add(0.04025);
		list.add(0.02406);
		list.add(0.06749);
		list.add(0.07507);
		list.add(0.01929);
		list.add(0.00095);
		list.add(0.05987);
		list.add(0.06327);
		list.add(0.09056);
		list.add(0.02758);
		list.add(0.00978);
		list.add(0.02360);
		list.add(0.00150);
		list.add(0.01974);
		list.add(0.00074);
		list.add(0.15);
		ArrayList<Character> list2 = new ArrayList<>();
		list2.add('a');
		list2.add('b');
		list2.add('c');
		list2.add('d');
		list2.add('e');
		list2.add('f');
		list2.add('g');
		list2.add('h');
		list2.add('i');
		list2.add('j');
		list2.add('k');
		list2.add('l');
		list2.add('m');
		list2.add('n');
		list2.add('o');
		list2.add('p');
		list2.add('q');
		list2.add('r');
		list2.add('s');
		list2.add('t');
		list2.add('u');
		list2.add('v');
		list2.add('w');
		list2.add('x');
		list2.add('y');
		list2.add('z');
		list2.add(' ');
		
		EncodingTree<Character> test = HuffmanTree.getHuffmanTree(4, list2, list);
		test.printTree();
		String str = "lets try this out";
		ArrayList<Integer> out = test.encode(Utilities.toArrayList(str.toCharArray()));
		out.add(3);
		out.add(1);
		out.add(0);
		System.out.println(out);
		System.out.println(test.decode(out));
		
		EncodingTree<Character> test3 = RandomEncodingTree.getRandomEncodingTree(4, list2);
		test3.printTree();
		out = test3.encode(Utilities.toArrayList(str.toCharArray()));
		System.out.println(out);
		System.out.println(test3.decode(out));
	}
}

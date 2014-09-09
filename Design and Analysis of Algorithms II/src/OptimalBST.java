import java.util.ArrayList;
import java.util.Collections;

public final class OptimalBST<E extends Comparable<E>> extends StaticTree<E> {
	
	private OptimalBST (ArrayList<E> list, ArrayList<Double> freqs) {
		makeTree(ComparableAdaptor.makeList(list, freqs));
	}
	
	public static <T extends Comparable<T>> OptimalBST<T> getOptimalBST (ArrayList<T> list, ArrayList<Double> freqs) {
		if (HuffmanTree.checkError(list, freqs)) {
			return null;
		}
		return new OptimalBST<T>(list, freqs);
	}
	
	private ArrayList<ArrayList<Integer>> findRoots (ArrayList<ComparableAdaptor<E, Double>> list) {
		Collections.sort(list);
		ArrayList<ArrayList<Double>> values = new ArrayList<>();
		ArrayList<ArrayList<Integer>> roots = new ArrayList<>();
		for (int length = 0; length < list.size(); length ++) {
			values.add (new ArrayList<Double>());
			roots.add (new ArrayList<Integer>());
			for (int start = 0; start < list.size()-length; start ++) {
				Double base = 0.0;
				Double min = Double.MAX_VALUE;
				Integer root = start;
				for (int check_root = start; check_root <= start + length; check_root ++) {
					base += list.get(check_root).getValue();
					Double check = 0.0;
					if (start <= check_root-1) {
						check += values.get(check_root-start-1).get(start);
					}
					if (check_root+1 <= start+length) {
						check += values.get(start+length-check_root-1).get(check_root+1);
					}
					if (check < min) {
						min = check;
						root = check_root;
					}
				}
				values.get(length).add(base + min);
				roots.get(length).add(root);
			}
		}
		System.out.println(values.get(list.size()-1).get(0));
		return roots;
	}
	
	private StaticNode<E> makeTreeHelper (int start, int end, ArrayList<ArrayList<Integer>> roots, ArrayList<ComparableAdaptor<E, Double>> list) {
		if (start > end) {
			return StaticNode.POISON_NODE;
		}
		if (start == end) {
			return new StaticNode<E>(list.get(start).getKey(), null);
		}
		ArrayList<StaticNode<E>> children = new ArrayList<>();
		children.add(makeTreeHelper(start, roots.get(end-start).get(start)-1, roots, list));
		children.add(makeTreeHelper(roots.get(end-start).get(start)+1, end, roots, list));
		StaticNode<E> node = new StaticNode<>(list.get(roots.get(end-start).get(start)).getKey(), children);
		for (int i = 0; i < children.size(); i ++) {
			children.get(i).setParent(node, i);
		}
		return node;
	}
	
	private void makeTree (ArrayList<ComparableAdaptor<E, Double>> list) {
		setRoot(makeTreeHelper(0, list.size()-1, findRoots(list), list));
	}
	
	private E findHelper (E query, StaticNode<E> current) {
		if (current == StaticNode.POISON_NODE) {
			return null;
		}
		if (query.compareTo(current.getData()) == 0) {
			return current.getData();
		}
		if (current.getChildren().size() < 1) {
			return null;
		}
		if (query.compareTo(current.getData()) > 0) {
			return findHelper(query, current.getChildren().get(1));
		}
		return findHelper(query, current.getChildren().get(0));
	}
	
	public E find (E query) {
		if (query == null) {
			System.out.println(NULL_INPUT);
			return null;
		}
		return findHelper(query, getRoot());
	}
	
	private void getAllHelper (ArrayList<E> out, StaticNode<E> current) {
		ArrayList<StaticNode<E>> children = current.getChildren();
		if (children.size() > 0 && children.get(0) != StaticNode.POISON_NODE) {
			getAllHelper(out, children.get(0));
		}
		out.add(current.getData());
		if (children.size() > 0 && children.get(1) != StaticNode.POISON_NODE) {
			getAllHelper(out, children.get(1));
		}
	}
	
	public ArrayList<E> getAll () {
		ArrayList<E> out = new ArrayList<>();
		getAllHelper(out, getRoot());
		return out;
	}
	
	public static void main (String[] args) {
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
		
		OptimalBST<Character> test = OptimalBST.getOptimalBST(list2, list);
		test.printTree();
		System.out.println(test.getAll());
	}
}

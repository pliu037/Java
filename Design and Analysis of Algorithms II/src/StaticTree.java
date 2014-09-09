import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import DataStructs.HashTable;

public class StaticTree<E> {
	
	private StaticNode<E> root = null;
	
	protected static final String HAS_CYCLES = "The tree must not contain cycles!";
	protected static final String NULL_INPUT = "Null input!";
	protected static final String NO_ROOT = "This tree's root has not been initialized!";
	protected static final String PARENT_CHILD_MISMATCH = "There is a mismatch between the parent pointer of a child and the child pointer of the parent!";
	
	protected StaticTree () {
		
	}
	
	private StaticTree (StaticNode<E> root) {
		this.root = root;
	}
	
	public static <T> StaticTree<T> getTree (StaticNode<T> root) {
		if (!isValid(root)) {
			return null;
		}
		return new StaticTree<T>(root);
	}
	
	public static <T> boolean isValid (StaticNode<T> root) {
		if (root == null) {
			System.out.println(NO_ROOT);
			return false;
		}
		HashTable<StaticNode<T>, Boolean> ht = new HashTable<>();
		ArrayList<StaticNode<T>> list = new ArrayList<>();
		list.add(root);
		boolean flag = true;
		while (!list.isEmpty()) {
			StaticNode<T> current = list.remove(list.size()-1);
			if (current == null) {
				System.out.println(NULL_INPUT);
				flag = false;
			}
			else {
				if (!ht.add(current, true)) {
					System.out.println(HAS_CYCLES);
					return false;
				}
				ArrayList<StaticNode<T>> children = current.getChildren();
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
		return flag;
	}
	
	protected boolean setRoot (StaticNode<E> root) {
		if (this.root == null && isValid(root)) {
			this.root = root;
			return true;
		}
		return false;
	}
	
	public final StaticNode<E> getRoot() {
		return root;
	}
	
	public final void printTree () {
		if (root == null) {
			System.out.println(NO_ROOT);
			return;
		}
		LinkedBlockingQueue<StaticNode<E>> list1 = new LinkedBlockingQueue<>(), list2 = new LinkedBlockingQueue<>();
		list1.add(root);
		int level = 0;
		while (!list1.isEmpty() || !list2.isEmpty()) {
			if (level%2 == 0) {
				while (!list1.isEmpty()) {
					StaticNode<E> current = list1.poll();
					if (current == StaticNode.POISON_NODE) {
						System.out.print("* ");
					}
					else {
						System.out.print(current.getData() + " ");
						ArrayList<StaticNode<E>> children = current.getChildren();
						if (children != null) {
							for (int i = 0; i < children.size(); i ++) {
								list2.add(children.get(i));
							}
						}
					}
				}
			}
			else {
				while (!list2.isEmpty()) {
					StaticNode<E> current = list2.poll();
					if (current == StaticNode.POISON_NODE) {
						System.out.print("* ");
					}
					else {
						System.out.print(current.getData() + " ");
						ArrayList<StaticNode<E>> children = current.getChildren();
						if (children != null) {
							for (int i = 0; i < children.size(); i ++) {
								list1.add(children.get(i));
							}
						}
					}
				}
			}
			System.out.println();
			level ++;
		}
	}
}

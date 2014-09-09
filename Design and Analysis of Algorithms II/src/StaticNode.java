import java.util.ArrayList;

public final class StaticNode<E> {
	
	public static final StaticNode POISON_NODE = new StaticNode();

	private final E data;
	private final ArrayList<StaticNode<E>> children;
	private StaticNode<E> parent = null;
	private int parent_link_value = -1;
	
	private static final String ALREADY_ASSIGNED = "This node's parent has already been assigned!";
	private static final String INDEX_DOES_NOT_MATCH = "The index of the child does not match the child!";
	
	private StaticNode () {
		data = null;
		children = null;
	}
	
	public StaticNode (E data, ArrayList<StaticNode<E>> children) {
		this.data = data;
		this.children = children;
	}
	
	public boolean setParent (StaticNode<E> parent, int parent_link_value) {
		if (this == POISON_NODE || this.parent_link_value != -1) {
			if (this.parent_link_value != -1) {
				System.out.println(ALREADY_ASSIGNED);
			}
			return false;
		}
		this.parent = parent;
		if (parent == null) {
			this.parent_link_value = -2;
		}
		else if (parent_link_value < 0 || parent_link_value >= parent.getChildren().size() || parent.getChildren().get(parent_link_value) != this) {
			System.out.println(INDEX_DOES_NOT_MATCH);
			return false;
		}	
		this.parent_link_value = parent_link_value;
		return true;
	}
	
	public E getData () {
		return data;
	}
	
	public StaticNode<E> getParent () {
		return parent;
	}
	
	public ArrayList<StaticNode<E>> getChildren () {
		if (children == null) {
			return new ArrayList<StaticNode<E>>();
		}
		return new ArrayList<StaticNode<E>>(children);
	}
	
	public int getParentLink () {
		return parent_link_value;
	}
	
	public static <T> ArrayList<StaticNode<T>> makeNodes (ArrayList<T> list) {
		ArrayList<StaticNode<T>> nodes = new ArrayList<>();
		if (list != null) {
			for (int i = 0; i < list.size(); i ++) {
				nodes.add(new StaticNode<>(list.get(i), null));
			}
		}
		return nodes;
	}
}
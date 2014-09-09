package DataStructs;
import java.util.ArrayList;

public final class WQUPC {
	
	private int size = 0;
	private int groups = 0;
	private final ArrayList<Integer> array = new ArrayList<>(), ranks = new ArrayList<>();
	
	private static final String TOO_SMALL = "Initial size must be at least 0!";
	
	private final String OUT_OF_BOUNDS () {
		if (size < 1) {
			return "WQUPC is empty!";
		}
		return ("Stay within the bounds (1 - " + size + ")!");
	}
	
	public WQUPC () {
		array.add(0);
		ranks.add(0);
	}
	
	private WQUPC (int size) {
		this.size = size;
		groups = size;
		for (int i = 0; i <= size; i ++) {
			array.add(i);
			ranks.add(0);
		}
	}
	
	public static WQUPC getWQUPC (int size) {
		if (size < 0) {
			System.out.println(TOO_SMALL);
			return null;
		}
		return new WQUPC (size);
	}
	
	private int find_helper (int query) {
		int check = array.get(query);
		if (check == query) {
			return query;
		}
		int root = find_helper (check);
		array.set(query, root);
		return root;
	}
	
	public int find (int query) {
		if (query <= 0 || query > size) {
			System.out.println(OUT_OF_BOUNDS());
			return 0;
		}
		return find_helper (query);
	}
	
	public boolean union (int target1, int target2) {
		if (target1 <= 0 || target1 > size || target2 <= 0 || target2 > size) {
			System.out.println(OUT_OF_BOUNDS());
			return false;
		}
		int a = find_helper(target1);
		int b = find_helper(target2);
		if (a != b) {
			if (ranks.get(a) > ranks.get(b)) {
				array.set(b, a);
			}
			else {
				array.set(a, b);
				if (ranks.get(a) == ranks.get(b)) {
					ranks.set(b, ranks.get(b)+1);
				}
			}
			groups --;
		}
		return true;
	}
	
	public void addElement () {
		size ++;
		groups ++;
		array.add(size);
		ranks.add(0);
	}
	
	public int getGroups () {
		return groups;
	}
	
	public int getSize () {
		return size;
	}
	
	public ArrayList<Integer> getRaw () {
		return new ArrayList<Integer>(array.subList(1, array.size()));
	}

	public static void main(String[] args) {
		WQUPC test = WQUPC.getWQUPC (10);
		test.union(1, 2);
		test.union(2, 3);
		test.union(3, 4);

		test.union(5, 6);
		test.union(6, 7);
		test.union(7, 8);
		
		test.union(9, 10);
		System.out.println(test.getRaw());
		
		test.union(7, 10);
		System.out.println(test.getRaw());
		
		test.union(9, 1);
		System.out.println(test.getRaw());
		
		test.find(1);
		test.find(7);
		System.out.println(test.getRaw());
	}

}

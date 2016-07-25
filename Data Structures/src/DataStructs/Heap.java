package DataStructs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;


public final class Heap <E extends Comparable<E>> {
	private final int is_max;
	private final ArrayList <E> al = new ArrayList<>();
	private final Comparator<E> cmp;
	private final HashTable<E, HashTable<Integer, Boolean>> ht = new HashTable<>();
	private int size = 0; 
	
	private static final String NULL_INPUT = "Null input!";

	private final String OUT_OF_BOUNDS () {
		if (size < 1) {
			return "Heap is empty!";
		}
		return ("Stay within the bounds (1 - " + size + ")!");
	}
	
	public Heap (boolean max) {
		if (!max) {
			is_max = -1;
		}
		else {
			is_max = 1;
		}
		al.add(null);
		cmp = null;
	}
	
	private Heap (boolean max, Comparator<E> cmp, Collection<E> group) {
		if (!max) {
			is_max = -1;
		}
		else {
			is_max = 1;
		}
		al.add(null);
		this.cmp = cmp;
		if (group != null) {
			al.addAll(group);
			size = group.size();
			for (int i = 1; i <= size; i ++) {
				hashTableManagement(al.get(i), i);
			}
			for (int i = size/2; i >= 1; i --) {
				bubbleDown (i);
			}
		}
	}
	
	public static <T extends Comparable<T>> Heap<T> getHeap (boolean max, Comparator<T> cmp) {
		if (cmp == null) {
			System.out.println(NULL_INPUT);
			return null;
		}
		return new Heap<T>(max, cmp, null);
	}
	
	public static <T extends Comparable<T>> Heap<T> getHeap (boolean max, Collection<T> group) {
		if (group == null || group.contains(null)) {
			System.out.println(NULL_INPUT);
			return null;
		}
		return new Heap<T>(max, null, group);
	}
	
	public static <T extends Comparable<T>> Heap<T> getHeap (boolean max, Comparator<T> cmp, Collection<T> group) {
		if (cmp == null || group == null || group.contains(null)) {
			System.out.println(NULL_INPUT);
			return null;
		}
		return new Heap<T>(max, cmp, group);
	}
	
	private void hashTableManagement (E item, int pos) {
		HashTable<Integer, Boolean> temp = ht.get(item);
		if (temp == null) {
			temp = new HashTable<>();
			temp.add(pos, true);
			ht.add(item, temp);
		}
		else {
			if (temp.get(pos) != null) {
				temp.remove(pos);
				if (temp.isEmpty()) {
					ht.remove(item);
				}
			}
			else {
				temp.add(pos, true);
			}
		}
	}
	
	private void swap (int pos1, int pos2) {
		E temp = al.get(pos2);
		E temp2 = al.get(pos1);
		al.set(pos1, temp);
		hashTableManagement(temp,pos1);
		hashTableManagement(temp,pos2);
		al.set(pos2, temp2);
		hashTableManagement(temp2,pos1);
		hashTableManagement(temp2,pos2);
	}
	
	private int compare (E check1, E check2) {
		if (cmp == null) {
			return is_max*check1.compareTo(check2);
		}
		else {
			return is_max*cmp.compare(check1, check2);
		}
	}
	
	private int heapify (int pos, E check) {
		int target = pos;
		if (2*pos + 1 > size) {
			if (compare(check, al.get(2*pos)) < 0) {
				target = 2*pos;
			}
		}
		else {
			E check1 = al.get(2*pos);
			E check2 = al.get(2*pos + 1);
			if (compare(check1, check2) > 0) {
				if (compare(check, check1) < 0) {
					target = 2*pos;
				}
			}
			else if (compare(check, check2) < 0) {
				target = 2*pos + 1;
			}
		}
		swap (pos, target);
		return target;
	}
	
	private int bubbleDown (int pos) {
		return bubbleDownHelper (pos, al.get(pos));
	}
	
	private int bubbleDownHelper (int pos, E check) {
		if (2*pos > size) {
			return pos;
		}
		int target = heapify (pos, check);
		if (target != pos) {
			return bubbleDownHelper (target, check);
		}
		return pos;
	}
	
	private int bubbleUp (int pos) {
		return bubbleUpHelper (pos, al.get(pos));
	}
	
	private int bubbleUpHelper (int pos, E check) {
		if (pos <= 1) {
			return 1;
		}
		if (compare(check, al.get(pos/2)) > 0) {
			swap (pos, pos/2);
			return bubbleUpHelper (pos/2, check);
		}
		return pos;
	}
	
	public boolean isEmpty() {
		return (size < 1);
	}
	
	public int getSize () {
		return size;
	}
	
	public Comparator<E> getComparator () {
		return cmp;
	}
	
	public boolean isMax () {
		if (is_max == 1) {
			return true;
		}
		return false;
	}
	
	public boolean add (E added) {
		if (added == null) {
			System.out.println(NULL_INPUT);
			return false;
		}
		al.add(added);
		size ++;
		hashTableManagement(added,size);
		bubbleUp(size);
		return true;
	}
	
	public boolean change (int pos, E added) {
		if (pos < 1 || pos > size || added == null) {
			if (pos < 1 || pos > size) {
				System.out.println(OUT_OF_BOUNDS());
			}
			if (added == null) {
				System.out.println(NULL_INPUT);
			}
			return false;
		}
		hashTableManagement(al.get(pos),pos);
		al.set(pos, added);
		hashTableManagement(added,pos);
		bubbleDown(bubbleUp(pos));
		return true;
	}
	
	public E peek () {
		return peek(1);
	}
	
	public E peek (int pos) {
		if (pos < 1 || pos > size) {
			System.out.println(OUT_OF_BOUNDS());
			return null;
		}
		return al.get(pos);
	}
	
	public ArrayList<Integer> getPositions (E target) {
		if (target == null) {
			System.out.println(NULL_INPUT);
			return null;
		}
		HashTable<Integer, Boolean> check = ht.get(target);
		if (check == null) {
			return new ArrayList<Integer>();
		}
		return ht.get(target).getNames();
	}
	
	public E remove () {
		return remove(1);
	}
	
	public E remove (int pos) {
		if (pos < 1 || pos > size) {
			System.out.println(OUT_OF_BOUNDS());
			return null;
		}
		E temp = al.get(pos);
		E temp2 = al.remove(size);
		hashTableManagement(temp,pos);
		size --;
		if (size >= pos) {
			al.set(pos, temp2);
			hashTableManagement(temp2,pos);
			hashTableManagement(temp2,size+1);
			bubbleDown(bubbleUp(pos));
		}
		return temp;
	}
	
	public boolean removeAll (E target) {
		HashTable<Integer, Boolean> targets = ht.get(target);
		if (targets == null) {
			return false;
		}
		while (!targets.isEmpty()) {
			remove(targets.getName());
		}
		return true;
	}
	
	public ArrayList<E> getHeap () {
		return new ArrayList<E>(al.subList(1, al.size()));
	}
	
	public static void main (String[] args) {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < 50000; i ++) {
			list.add(i);
		}
		Heap <Integer> test = Heap.getHeap(true,list);
		test.add(3);
		test.add(3);
		test.add(3);
		test.add(3);
        System.out.println(test.getPositions(3));
		test.removeAll(3);
		System.out.println(test.getPositions(3));
		System.out.println(test.getSize() + " " + test.getHeap());

	}
}

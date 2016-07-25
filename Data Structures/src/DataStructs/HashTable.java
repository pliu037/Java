package DataStructs;
import java.util.ArrayList;

public final class HashTable <S,T> {

	private static class Container <S,T> {
		
		private final S name;
		private final T value;
		
		public Container (S name, T value) {
			this.name = name;
			this.value = value;
		}
	}
	
	private int size = 0;
	private int num_bins = MIN_NUM_BINS;
	private final ArrayList<ArrayList<Container<S,T>>> bins = new ArrayList<>();
	
	private static final double RATIO = 0.75;
	private static final double FACTOR = 2;
	private static final int MIN_NUM_BINS = 2;
	private static final String NULL_INPUT = "Null input!";
	private static final String IS_EMPTY = "HashTable is empty!";
	
	private static final String LOW_NUM_BINS () {
		return("Number of bins too low and has been set to " + MIN_NUM_BINS + "!");
	}
	
	public HashTable () {
		for (int i = 0; i < num_bins; i ++) {
			bins.add(new ArrayList<Container<S,T>>());
		}
	}
	
	private HashTable (int num_bins) {
		this.num_bins = num_bins;
		for (int i = 0; i < num_bins; i ++) {
			bins.add(new ArrayList<Container<S,T>>());
		}
	}
	
	public static <K,V> HashTable<K,V> getHashTable (int num_bins) {
		if (num_bins < MIN_NUM_BINS) {
			System.out.println(LOW_NUM_BINS());
			num_bins = MIN_NUM_BINS;
		}
		return new HashTable<K,V>(num_bins);
	}
	
	private ArrayList<Container<S,T>> getAllContainers () {
		ArrayList<Container<S,T>> al = new ArrayList<>();
		for (int i = 0; i < num_bins; i ++) {
			ArrayList<Container<S,T>> current = bins.get(i);
			for (int j = current.size() - 1; j >= 0; j --) {
				al.add(current.remove(j));
			}
		}
		return al;
	}
	
	private void newHashTable (int new_num_bins) {
		if (size >= num_bins*RATIO || (size <= (1 - RATIO)*num_bins && new_num_bins >= MIN_NUM_BINS)) {
			ArrayList<Container<S,T>> al = getAllContainers ();
			if (size >= num_bins*RATIO) {
				while (num_bins < new_num_bins) {
					bins.add(new ArrayList<Container<S,T>>());
					num_bins ++;
				}
			}
			else if (size <= (1 - RATIO)*num_bins && new_num_bins >= MIN_NUM_BINS) {
				while (num_bins > new_num_bins) {
					bins.remove(bins.size()-1);
					num_bins --;
				}
			}
			for (int i = al.size() - 1; i >= 0; i --) {
				bins.get(getKey(al.get(i).name)).add(al.remove(i));
			}
		}
	}
	
	private int getKey (S name) {
		int key = name.hashCode()%num_bins;
		if (key < 0) {
			key += num_bins;
		}
		return key;
	}
	
	private int inHashTable (S name) {
		ArrayList<Container<S,T>> bin = bins.get(getKey(name));
		for (int i = 0; i < bin.size(); i ++) {
			if (bin.get(i).name.equals(name)) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean add (S name, T value) {
		if (name == null || value == null) {
			System.out.println(NULL_INPUT);
			return false;
		}
		if (inHashTable(name) == -1) {
			Container<S,T> cntr = new Container<>(name, value);
			size ++;
			newHashTable((int)(num_bins*FACTOR));
			bins.get(getKey(name)).add(cntr);
			return true;
		}
		return false;
	}
	
	public boolean replace (S name, T value) {
		if (name == null || value == null) {
			System.out.println(NULL_INPUT);
			return false;
		}
		int check = inHashTable(name);
		ArrayList<Container<S,T>> bin = bins.get(getKey(name));
		Container<S,T> cntr = new Container<>(name, value);
		if (check == -1) {
			size ++;
			newHashTable((int)(num_bins*FACTOR));
			bin.add(cntr);
		}
		else {
			bin.set(check, cntr);
		}
		return true;
	}
	
	public T get(S name) {
		if (name == null) {
			System.out.println(NULL_INPUT);
			return null;
		}
		int check = inHashTable(name);
		if (check == -1) {
			return null;
		}
		return bins.get(getKey(name)).get(check).value;
	}
	
	public T remove (S name) {
		if (name == null) {
			System.out.println(NULL_INPUT);
			return null;
		}
		int check = inHashTable(name);
		if (check == -1) {
			return null;
		}
		T value = bins.get(getKey(name)).remove(check).value;
		size --;
		newHashTable((int)(num_bins/FACTOR));
		return value;
	}
	
	public S getName () {
		if (size < 1) {
			System.out.println(IS_EMPTY);
			return null;
		}
		for (int i = 0; i < num_bins; i ++) {
			ArrayList<Container<S,T>> current = bins.get(i);
			if (current.size() > 0) {
				return current.get(0).name;
			}
		}
		return null;
	}
	
	public int getSize () {
		return size;
	}
	
	public boolean isEmpty () {
		return (size < 1);
	}
	
	public int getNumBins () {
		return num_bins;
	}
	
	public ArrayList<Integer> getBinSizes () {
		ArrayList<Integer> out = new ArrayList<>();
		for (int i = 0; i < num_bins; i ++) {
			out.add(bins.get(i).size());
		}
		return out;
	}
	
	public ArrayList<S> getNames () {
		ArrayList<S> al = new ArrayList<>();
		for (int i = 0; i < num_bins; i ++) {
			ArrayList<Container<S,T>> current = bins.get(i);
			for (int j = 0; j < current.size(); j ++) {
				al.add(current.get(j).name);
			}
		}
		return al;
	}
	
	public ArrayList<T> getValues () {
		ArrayList<T> al = new ArrayList<>();
		for (int i = 0; i < num_bins; i ++) {
			ArrayList<Container<S,T>> current = bins.get(i);
			for (int j = 0; j < current.size(); j ++) {
				al.add(current.get(j).value);
			}
		}
		return al;
	}
	
	public static void main(String[] args) {
		//Reference copied into and returned from Container (values mutable by default)
		//Integer is immutable and compares addresses (autoboxer is weird)
		/*Integer a = 1;
		Integer b = 10;
		Integer c = a;
		System.out.println(a.hashCode() + " " + b.hashCode() + " " + c.hashCode());
		Container one = new Container (a, true);
		Container two = new Container (b, true);
		Container three = new Container (c, true);
		//System.out.println((one.getName()==b) + " " + (one.getName()==three.getName()));
		//System.out.println(a + " " + one.getName() + " " + b + " " + two.getName() + " " + c + " " + three.getName());
		//c = (Integer)two.getName();
		//System.out.println(a + " " + one.getName() + " " + b + " " + two.getName() + " " + c + " " + three.getName());
		//c = 3;
		//System.out.println(a + " " + one.getName() + " " + b + " " + two.getName() + " " + c + " " + three.getName());
		*/
		
		HashTable <Integer, Boolean> ht = HashTable.getHashTable(1);
		ht.add (1, true);
		System.out.println(ht.getNumBins() + " " + ht.getSize());
		ht.add (2, true);
		System.out.println(ht.getNumBins() + " " + ht.getSize());
		ht.replace (3, false);
		System.out.println(ht.getNumBins() + " " + ht.getSize());
		ht.add (4, true);
		ht.replace (3, true);
		System.out.println(ht.getNumBins() + " " + ht.getSize() + " " + ht.get(3));
		ht.remove(4);
		ht.remove(3);
		System.out.println(ht.getNumBins() + " " + ht.getSize() + " " + ht.get(3));
	}

}

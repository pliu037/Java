import java.util.ArrayList;

public final class ComparableAdaptor<S extends Comparable<S>, T> implements Comparable<ComparableAdaptor<S,T>>{
	
	private final S key;
	private final T value;
	
	public ComparableAdaptor (S key, T value) {
		this.key = key;
		this.value = value;
	}
	
	public S getKey () {
		return key;
	}
	
	public T getValue () {
		return value;
	}
	
	public int compareTo (ComparableAdaptor<S,T> other) {
		if (other == null) {
			return -1;
		}
		return key.compareTo(other.key);
	}
	
	public boolean equals (Object other) {
		if (other == null || !(other.getClass() == ComparableAdaptor.class)) {
			return false;
		}
		ComparableAdaptor<S,T> check = (ComparableAdaptor<S,T>) other;
		return value.equals(check.value);
	}
	
	public int hashCode () {
		return value.hashCode();
	}
	
	public static <S extends Comparable<S>, T> ArrayList<ComparableAdaptor<S, T>> makeList (ArrayList<S> list1, ArrayList<T> list2) {
		ArrayList<ComparableAdaptor<S, T>> list = new ArrayList<>();
		if (list1 != null && list2 != null && list1.size() == list2.size()) {
			for (int i = 0; i < list1.size(); i ++) {
				list.add(new ComparableAdaptor<>(list1.get(i), list2.get(i)));
			}
		}
		return list;
	}
}

import java.util.ArrayList;

public final class Utilities {

	public static <T> T removeElement (ArrayList<T> list, int pos) {
		if (list != null && pos >= 0 && pos < list.size()) {
			if (list.size()-1 > pos) {
				T temp = list.get(pos);
				list.set(pos, list.remove(list.size()-1));
				return temp;
			}
			else {
				return list.remove(pos);
			}
		}
		return null;
	}
	
	public static ArrayList<Character> toArrayList (char[] stream) {
		ArrayList<Character> out = new ArrayList<>();
		if (stream != null) {
			for (int i = 0; i < stream.length; i ++) {
				out.add(stream[i]);
			}
		}
		return out;
	}
}

import java.util.ArrayList;

public interface Encoder<E> {

	public ArrayList<Integer> encode (ArrayList<E> stream);
	
	public ArrayList<E> decode (ArrayList<Integer> stream);
}

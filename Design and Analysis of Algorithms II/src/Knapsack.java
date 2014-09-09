import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Knapsack {
	
	static class Item {
		int value;
		int size;
		
		public Item (int value, int space) {
			this.value = value;
			this.size = space;
		}
	}
	
	public static int gcd (int a, int b) {
		if (b == 0) {
			return a;
		}
		if (a >= b) {
			return gcd (b, a-b*(a/b));
		}
		else {
			return gcd (a, b-a*(b/a));
		}
	}
	
	public static int solveKnapsack (ArrayList<Item> items, int capacity) {
		int gcd = capacity;
		for (int i = 0; i < items.size(); i ++) {
			gcd = gcd(gcd, items.get(i).size);
		}
		if (gcd > 1) {
			capacity = capacity/gcd;
			for (int i = 0; i < items.size(); i ++) {
				items.get(i).size = items.get(i).size/gcd;
			}
		}
		ArrayList<Integer> value = new ArrayList<>();
		for (int i = 0; i <= capacity; i ++) {
			value.add(0);
		}
		for (int i = 0; i < items.size(); i ++) {
			Item current = items.get(i);
			for (int j = capacity; j >= current.size; j --) {
				int	check = value.get(j-current.size) + current.value;
				if (value.get(j) < check) {
					value.set(j, check);
				}
			}
		}
		return value.get(capacity);
	}
	
	public static void main(String[] args) {
		try {
			String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms II/knapsack_big.txt";
			//Get data from the file
			BufferedReader input = new BufferedReader (new FileReader (path));
			String line = input.readLine();
			StringTokenizer st = new StringTokenizer (line, " \t");
			int capacity = Integer.parseInt(st.nextToken());
			ArrayList<Item> items = new ArrayList<>();
			while ((line=input.readLine()) != null) {
				st = new StringTokenizer (line, " \t");
				items.add(new Item (Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
			}
			input.close ();
			System.out.println(solveKnapsack(items, capacity));
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
	}

}

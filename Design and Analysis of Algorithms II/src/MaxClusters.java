import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import DataStructs.HashTable;
import DataStructs.WQUPC;
import static java.lang.Math.pow;

public class MaxClusters {
	
	static class Vertex {
		private int[] id;
		private int int_rep;
		
		public Vertex (int[] id, int int_rep) {
			this.id = id;
			this.int_rep = int_rep;
		}
	}
	
	private static int[] precomppow2 = null;
	
	private static void precompPow2 (int x) {
		precomppow2 = new int[x];
		for (int i = 0; i < x; i ++) {
			precomppow2[i] = (int)pow(2, i);
		}
	}
	
	private static int intRepresentation (int[] check) {
		if (precomppow2 == null || precomppow2.length < check.length) {
			precompPow2 (check.length);
		}
		int total = 0;
		for (int i = 0; i < check.length; i ++) {
			if (check[check.length - i - 1] == 1) {
				total += precomppow2[i];
			}
		}
		return total;
	}
	
	private static int flipBit (int[] array, int pos) {
		if (array[pos] == 1) {
			return -1*precomppow2[array.length - pos - 1];
		}
		else {
			return precomppow2[array.length - pos - 1];
		}
	}
	
	private static void checkDifferences (Vertex base, int pos, int max_depth, int cur_depth, WQUPC groups, HashTable<Integer, Integer> ht, int cur_vertex) {
		for (int i = pos; i < base.id.length; i ++) {
			base.int_rep += flipBit(base.id, i);
			if (cur_depth+1 < max_depth && i+1 < base.id.length) {
				checkDifferences(base, i+1, max_depth, cur_depth+1, groups, ht, cur_vertex);
			}
			Integer check = ht.peek(base.int_rep);
			if (check != null) {
				groups.union(cur_vertex+1, check+1);
			}
			base.int_rep -= flipBit(base.id, i);
		}
	}
	
	public static WQUPC findMinDistances (ArrayList<Vertex> vertices, HashTable<Integer, Integer> ht, int min_distance) {
		WQUPC groups = WQUPC.getWQUPC (ht.getSize());
		if (min_distance - 1 >= 1) {
			for (int i = 0; i < vertices.size(); i ++) {
				checkDifferences (vertices.get(i), 0, min_distance - 1, 0, groups, ht, i);
				if (groups.getGroups() == 1) {
					return groups;
				}
			}
		}
		return groups;
	}

	public static void main(String[] args) {
		
		try {
			String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms II/clustering_big.txt";
			//Get data from the file
			BufferedReader input = new BufferedReader (new FileReader (path));
			String line = input.readLine();
			StringTokenizer st = new StringTokenizer (line, " \t");
			st.nextToken();
			int bits = Integer.parseInt(st.nextToken());
			ArrayList<Vertex> vertices = new ArrayList<>();
			HashTable<Integer, Integer> ht = new HashTable<> ();
			int count = 0;
			while ((line=input.readLine()) != null) {
				st = new StringTokenizer (line, " \t");
				int[] temp = new int [bits];
				for (int i = 0; i < bits; i ++) {
					temp[i] = Integer.parseInt(st.nextToken());
				}
				int check = intRepresentation(temp);
				if (ht.add(check, count)) {
					vertices.add(new Vertex(temp, check));
					count ++;
				}
			}
			input.close ();
			System.out.println(findMinDistances(vertices, ht, 3).getGroups());
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
	}

}

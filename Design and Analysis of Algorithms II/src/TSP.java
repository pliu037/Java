import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.BitSet;
import DataStructs.HashTable;

public class TSP {
	
	static class Vertex {
		private double x, y;
		
		public Vertex (double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		public double getDistance (Vertex other) {
			return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2));
		}
	}
	
	public static double solveTSP (Vertex[] vertices) {
		
		//Base case: When there are 0 edges; only vertices[0] is visited
		HashTable<BitSet, Double>[] shortest_paths = new HashTable[vertices.length];
		for (int i = 0; i < vertices.length; i ++) {
			shortest_paths[i] = new HashTable<>();
		}
		shortest_paths[0].add(new BitSet (vertices.length), 0.0);
		
		//Inductive step: Induction on the # of vertices (# of edges) visited along a given path
		for (int path_length = 1; path_length < vertices.length; path_length ++) {
			HashTable<BitSet, Double>[] new_paths = new HashTable[vertices.length];
			for (int i = 0; i < vertices.length; i ++) {
				new_paths[i] = new HashTable<>();
			}
			
			int count = 0;
			
			//Iterate through paths discovered in the previous iteration (path_length - 1 vertices
			//visited)
			for (int end = 0; end < vertices.length; end ++) {
				while (!shortest_paths[end].isEmpty()) {
					BitSet current = shortest_paths[end].getName();
					Double current_distance = shortest_paths[end].remove(current);
					
					//Iterate through next steps of the path, checking to make sure it is not in the current
					//path, to extend paths
					for (int destination = 1; destination < vertices.length; destination ++) {
						if (!current.get(destination)) {
							
							//
							BitSet new_bs = (BitSet) current.clone(); //REQUIRES deep copy
							new_bs.set(destination);
							if (new_paths[destination].peek(new_bs) == null || new_paths[destination].peek(new_bs) > current_distance + vertices[end].getDistance(vertices[destination])) {
								new_paths[destination].replace(new_bs, current_distance + vertices[end].getDistance(vertices[destination]));
							}
						}
					}
				}
				
				count += new_paths[end].getSize();
				
			}
			
			System.out.println(count);
			
			//
			shortest_paths = new_paths;
		}
		
		//Find the shortest path from the existing shortest paths that visit all vertices only once back
		//to vertices[0]
		return 0;
	}

	public static void main(String[] args) {
		try {
			String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms II/tsp.txt";
			//Get data from the file
			BufferedReader input = new BufferedReader (new FileReader (path));
			String line = input.readLine();
			StringTokenizer st = new StringTokenizer (line, " \t");
			int num = Integer.parseInt(st.nextToken());
			Vertex[] vertices = new Vertex [num];
			num = 0;
			while ((line=input.readLine()) != null) {
				st = new StringTokenizer (line, " \t");
				vertices[num] = new Vertex (Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
				num ++;
			}
			input.close ();
			System.out.println(solveTSP(vertices));
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
	}
}

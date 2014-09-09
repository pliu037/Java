import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import DataStructs.Heap;

public class PrimMST {

	static class Edge implements Comparable <Edge> {
		
		public int length;
		public int dest;
		
		public Edge (int dest, int length) {
			this.dest = dest;
			this.length = length;
		}
		
		public int compareTo (Edge edge) {
			if (length < edge.length) {
				return -1;
			}
			else if (length > edge.length) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}
	
	static class Vertex {
		
		public ArrayList <Edge> outgoing = new ArrayList <Edge> ();
		public boolean visited = false;
		public int name;
		
		public Vertex (int name) {
			this.name = name;
		}
	}
	
	public static long primMST (Vertex[] vertices) {
		long total = 0;
		Heap <Edge> pq = new Heap <Edge> (false);
		vertices[0].visited = true;
		for (int i = 0, size = vertices[0].outgoing.size(); i < size; i ++) {
			Edge current_edge = vertices[0].outgoing.get(i);
			pq.add (current_edge);
		}
		while (!pq.isEmpty()) {
			Edge check_edge = pq.remove();
			int vertex = check_edge.dest;
			if (!vertices[vertex].visited) {
				vertices[vertex].visited = true;
				total += check_edge.length;
				for (int i = 0, size = vertices[vertex].outgoing.size(); i < size; i ++) {
					Edge current_edge = vertices[vertex].outgoing.get(i);
					if (!vertices[current_edge.dest].visited) {
						pq.add (current_edge);
					}
				}
			}
		}
		return total;
	}
	
	public static void main(String[] args) {
		try {
			String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms II/edges.txt";
			//Get data from the file
			BufferedReader input = new BufferedReader (new FileReader (path));
			String line = input.readLine();
			StringTokenizer st = new StringTokenizer (line, " \t");
			Vertex[] vertices = new Vertex [Integer.parseInt(st.nextToken())];
			for (int i = 0; i < vertices.length; i ++) {
				vertices[i] = new Vertex (i);
			}
			while ((line=input.readLine())!=null) {
				st = new StringTokenizer (line, " \t");
				int a = Integer.parseInt(st.nextToken()) - 1;
				int b = Integer.parseInt(st.nextToken()) - 1;
				int length = Integer.parseInt(st.nextToken());
				vertices[a].outgoing.add(new Edge (b, length));
				vertices[b].outgoing.add(new Edge (a, length));
			}
			input.close ();
			//Procedure
			System.out.println(primMST(vertices));
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
	}
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import DataStructs.HashTable;
import DataStructs.Heap;

public class ShortestPaths {
	
	static class Vertex implements Comparable<Vertex>{
		public static int counter = 0;
		
		public ArrayList<Edge> incoming = new ArrayList<>(), outgoing = new ArrayList<>();
		int id, path_length = 0;
		
		public Vertex () {
			id = counter;
			counter ++;
		}
		
		public int compareTo (Vertex other) {
			if (path_length < other.path_length) {
				return -1;
			}
			if (path_length > other.path_length) {
				return 1;
			}
			return 0;
		}
		
		public boolean equals (Object other) {
			Vertex cmp = (Vertex) other;
			if (id == cmp.id) {
				return true;
			}
			return false;
		}
		
		public int hashCode () {
			return id;
		}
	}
	
	static class Edge {
		public Vertex tail, head;
		public int length;
		
		public Edge (Vertex tail, Vertex head, int length) {
			this.tail = tail;
			this.head = head;
			this.length = length;
		}
	}
	
	public static Integer floydWarshall (ArrayList<Vertex> vertices) {
		int num_vertices = vertices.size();
		ArrayList<ArrayList<Integer>> opt_values = new ArrayList<>();
		for (int i = 0; i < num_vertices; i ++) {
			ArrayList<Integer> current = new ArrayList<>();
			for (int j = 0; j < num_vertices; j ++) {
				if (i != j) {
					current.add(null);
				}
				else {
					current.add(0);
				}
			}
			for (int j = 0; j < vertices.get(i).outgoing.size(); j ++) {
				current.set(vertices.get(i).outgoing.get(j).head.id, vertices.get(i).outgoing.get(j).length);
			}
			opt_values.add(current);
		}
		
		for (int check = 0; check < num_vertices; check ++) {
			ArrayList<ArrayList<Integer>> current = new ArrayList<>();
			for (int start = 0; start < num_vertices; start ++) {
				ArrayList<Integer> current2 = new ArrayList<>();
				for (int end = 0; end < num_vertices; end ++) {
					Integer p = opt_values.get(start).get(end),
							p1 = opt_values.get(start).get(check),
							p2 = opt_values.get(check).get(end);
					if (p1 == null || p2 == null || (p != null && p1 + p2 >= p)) {
						current2.add(p);
					}
					else {
						current2.add(p1 + p2);
					}
				}
				if (current2.get(start) < 0) {
					return null;
				}
				current.add(current2);
			}
			opt_values = current;
		}
		
		Integer min = Integer.MAX_VALUE;
		for (int i = 0; i < num_vertices; i ++) {
			for (int j = 0; j < num_vertices; j ++) {
				Integer check = opt_values.get(i).get(j);
				if (check != null && check < min) {
					min = check;
				}
			}
		}
		return min;
	}
	
	public static ArrayList<Integer> bellmanFord (Vertex source, ArrayList<Vertex> vertices) {
		int num_vertices = vertices.size();
		ArrayList<Integer> opt_values = new ArrayList<>();
		for (int i = 0; i < num_vertices; i ++) {
			opt_values.add(null);
		}
		opt_values.set(source.id, 0);
		
		for (int i = 0; i < num_vertices - 1; i ++) {
			ArrayList<Integer> current = new ArrayList<>();
			boolean finished = true;
			for (int j = 0; j < num_vertices; j ++) {
				Integer min = opt_values.get(j) != null ? opt_values.get(j) : Integer.MAX_VALUE;
				for (int k = 0; k < vertices.get(j).incoming.size(); k ++) {
					Edge check = vertices.get(j).incoming.get(k);
					Integer value = opt_values.get(check.tail.id) != null ? opt_values.get(check.tail.id) + check.length : Integer.MAX_VALUE;
					if (value < min) {
						min = value;
					}
				}
				if (opt_values.get(j) != min) {
					finished = false;
				}
				current.add(min);
			}
			if (finished) {
				return opt_values;
			}
			opt_values = current;
		}
		
		for (int j = 0; j < num_vertices; j ++) {
			Integer min = opt_values.get(j) != null ? opt_values.get(j) : Integer.MAX_VALUE;
			for (int k = 0; k < vertices.get(j).incoming.size(); k ++) {
				Edge check = vertices.get(j).incoming.get(k);
				Integer value = opt_values.get(check.tail.id) != null ? opt_values.get(check.tail.id) + check.length : Integer.MAX_VALUE;
				if (value < min) {
					return null;
				}
			}
		}
		return opt_values;
	}
	
	public static ArrayList<Integer> dijkstra (Vertex source, ArrayList<Vertex> vertices) {
		ArrayList<Integer> opt_values = new ArrayList<>();
		for (int i = 0; i < vertices.size(); i ++) {
			opt_values.add(null);
		}
		HashTable<Vertex, Boolean> visited = new HashTable<>();
		Heap<Vertex> heap = new Heap<>(false);
		heap.add(source);
		while (!heap.isEmpty()) {
			Vertex current = heap.remove();
			if (visited.peek(current) == null) {
				visited.add(current, true);
				opt_values.set(current.id, current.path_length);
				for (int i = 0; i < current.outgoing.size(); i ++) {
					Vertex head = current.outgoing.get(i).head;
					if (visited.peek(head) == null) {
						Integer new_length = current.path_length + current.outgoing.get(i).length;
						ArrayList<Integer> check = heap.getPositions(head);
						if (check.isEmpty()) {
							head.path_length = new_length;
							heap.add(head);
						}
						else if (heap.peek(check.get(0)).path_length > new_length) {
							head.path_length = new_length;
							heap.change(check.get(0), head);
						}
					}
				}
				current.path_length = 0;
			}
		}
		return opt_values;
	}
	
	public static Integer johnson (ArrayList<Vertex> vertices, ArrayList<Edge> edges) {
		int num_vertices = vertices.size();
		Vertex ghost = new Vertex();
		vertices.add(ghost);
		for (int i = 0; i < num_vertices; i ++) {
			Edge current = new Edge (ghost, vertices.get(i), 0);
			ghost.outgoing.add(current);
			vertices.get(i).incoming.add(current);
		}
		ArrayList<Integer> vertex_weights = bellmanFord(ghost, vertices);
		if (vertex_weights == null) {
			return null;
		}
		vertices.remove(num_vertices);
		for (int i = 0; i < edges.size(); i ++) {
			Edge current = edges.get(i);
			current.length -= vertex_weights.get(current.head.id) - vertex_weights.get(current.tail.id);
		}
		
		ArrayList<ArrayList<Integer>> opt_values = new ArrayList<>();
		for (int i = 0; i < num_vertices; i ++) {
			opt_values.add(dijkstra (vertices.get(i), vertices));
		}
		
		Integer min = Integer.MAX_VALUE;
		for (int i = 0; i < num_vertices; i ++) {
			for (int j = 0; j < num_vertices; j ++) {
				Integer check = opt_values.get(i).get(j);
				if (check != null) {
					check += vertex_weights.get(j) - vertex_weights.get(i);
					opt_values.get(i).set(j, check);
					if (check < min) {
						min = check;
					}
				}
			}
		}
		return min;
	}

	public static void main(String[] args) {
		try {
			String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms II/g3.txt";
			//Get data from the file
			BufferedReader input = new BufferedReader (new FileReader (path));
			String line = input.readLine();
			StringTokenizer st = new StringTokenizer (line, " \t");
			int num_vertices = Integer.parseInt(st.nextToken());
			ArrayList<Vertex> vertices = new ArrayList<>();
			ArrayList<Edge> edges = new ArrayList<>();
			for (int i = 0; i < num_vertices; i ++) {
				vertices.add(new Vertex ());
			}
			while ((line=input.readLine()) != null) {
				st = new StringTokenizer (line, " \t");
				Vertex tail = vertices.get(Integer.parseInt(st.nextToken())-1), head = vertices.get(Integer.parseInt(st.nextToken())-1);
				int length = Integer.parseInt(st.nextToken());
				Edge current = new Edge (tail, head, length);
				edges.add(current);
				tail.outgoing.add(current);		
				head.incoming.add(current);
			}
			input.close ();
			System.out.println(johnson(vertices, edges));
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
	}

}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import DataStructs.WQUPC;

public class MaxSpacing {
	
	static class Edge implements Comparable <Edge> {
		
		public int a;
		public int b;
		public int length;
		
		public Edge (int head, int tail, int length) {
			this.a = head;
			this.b = tail;
			this.length = length;
		}
		
		public int compareTo (Edge ext) {
			if (this.length < ext.length) {
				return -1;
			}
			else if (this.length > ext.length) {
				return 1;
			}
			return 0;
		}
	}
	
	public static WQUPC maxSpacingClustering (ArrayList<Edge> edges, int vertices, int clusters) {
		Collections.sort (edges);
		WQUPC uf = WQUPC.getWQUPC (vertices);
		for (int i = 0; i < edges.size(); i ++) {
			uf.union(edges.get(i).a, edges.get(i).b);
			if (uf.getGroups() == clusters) {
				i ++;
				while (uf.find(edges.get(i).a) == uf.find(edges.get(i).b)) {
					i ++;
				}
				System.out.println(edges.get(i).length);
				return uf;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		
		try {
			String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms II/clustering1.txt";
			//Get data from the file
			BufferedReader input = new BufferedReader (new FileReader (path));
			String line = input.readLine();
			StringTokenizer st = new StringTokenizer (line, " \t");
			int n = Integer.parseInt(st.nextToken());
			ArrayList<Edge> edges = new ArrayList<>();
			while ((line=input.readLine()) != null) {
				st = new StringTokenizer (line, " \t");
				edges.add(new Edge (Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
			}
			input.close ();
			//Procedure
			System.out.println(maxSpacingClustering(edges, n, 4).getRaw());
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
	}

}

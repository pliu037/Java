import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class ConnectedComponents {
	
	static class Node {
		
		private int rank;
		private int cluster;
		private boolean visited = false;
		private ArrayList <Integer> out_paths = new ArrayList <Integer>();
		private ArrayList <Integer> in_paths = new ArrayList <Integer>();
		
		public Node () {
		}
		
		public ArrayList <Integer> getOutPaths () {
			return out_paths;
		}
		
		public void addEdge (Integer destination, boolean out) {
			if (out == true) {
				out_paths.add(destination);
			}
			else {
				in_paths.add(destination);
			}
		}
		
		public void reverseEdges () {
			ArrayList <Integer> temp = out_paths;
			out_paths = in_paths;
			in_paths = temp;
		}
		
		public void setRank (int erank) {
			rank = erank;
		}
		
		public int getRank () {
			return rank;
		}
		
		public void setVisited (boolean evisited) {
			visited = evisited;
		}
		
		public boolean isVisited () {
			return visited;
		}
		
		public void setCluster (int ecluster) {
			cluster = ecluster;
		}
		
		public int getCluster () {
			return cluster;
		}
	}
	
	public static Node[] rearrangeGraphByRank (Node[] graph) {
		int size = graph.length;
		Node[] temp = new Node [size];
		for (int i = 0; i < size; i ++) {
			temp[graph[i].getRank()] = graph[i];
		}
		return temp;
	}
	
	public static void addPathsToStack (int node, Node[] graph, ArrayList <Integer> stack) {
		ArrayList <Integer> paths = graph[node].getOutPaths();
		for (int i = 0; i < paths.size(); i ++) {
			if (!graph[paths.get(i)].isVisited()) {
				stack.add(paths.get(i));
			}
		}
	}
	
	public static void DepthFirstSearch (Node[] graph) {
		int counter = graph.length - 1;
		ArrayList <Integer> stack = new ArrayList <Integer>();
		for (int i = 0; i < graph.length; i ++) {
			if (!graph[i].isVisited()) {
				addPathsToStack (i, graph, stack);
				graph[i].setVisited(true);
				graph[i].setRank(counter);
				graph[i].setCluster(i);
				counter --;
				while (!stack.isEmpty()) {
					int check = stack.remove(stack.size() - 1);
					if (!graph[check].isVisited()) {
						addPathsToStack (check, graph, stack);
						graph[check].setVisited(true);
						graph[check].setRank(counter);
						graph[check].setCluster(i);
						counter --;
					}
				}
			}
		}
	}
	
	public static void FindSCC (Node[] graph) {
		DepthFirstSearch(graph);
		for (int i = 0; i < graph.length; i ++) {
			graph[i].reverseEdges();
			graph[i].setVisited(false);
		}
		graph = rearrangeGraphByRank (graph);
		DepthFirstSearch(graph);
		
		double[] counts = new double [graph.length];
		for (int i = 0; i < graph.length; i ++) {
			counts[i] = 0;
		}
		for (int i = 0; i < graph.length; i ++) {
			counts[graph[i].getCluster()] ++;
		}
		CountInverses.CountInversions_1DMergeSort(counts, 0, counts.length);
		System.out.println(counts[graph.length - 1]);
		System.out.println(counts[graph.length - 2]);
		System.out.println(counts[graph.length - 3]);
		System.out.println(counts[graph.length - 4]);
		System.out.println(counts[graph.length - 5]);
	}
	
	public static void main(String[] args) {
		//Find the maximum node in the file
		String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms I/SCC.txt";
		String line;
		int n = 0;
		try {
			BufferedReader input = new BufferedReader (new FileReader (path));
			while ((line=input.readLine())!=null) {
				StringTokenizer st = new StringTokenizer (line, " \t");
				while (st.hasMoreTokens()) {
					int check = Integer.parseInt(st.nextToken());
					if (check > n) {
						n = check;
					}
				}
			}
			input.close ();
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
		//Get data from the file
		Node[] graph = new Node [n];
		for (int i = 0; i < n; i ++) {
			graph[i] = new Node ();
		}
		try {
			BufferedReader input = new BufferedReader (new FileReader (path));
			while ((line=input.readLine())!=null) {
				StringTokenizer st = new StringTokenizer (line, " \t");
				Integer origin = Integer.valueOf(st.nextToken())-1;
				Integer dest = Integer.valueOf(st.nextToken())-1;
				graph[origin].addEdge(dest, true);
				graph[dest].addEdge(origin, false);
			}
			input.close ();
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
		//Procedure
		FindSCC(graph);
	}
}

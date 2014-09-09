import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.ArrayList;
import static java.lang.Math.pow;

public class MinCut {
	
	public static ArrayList<ArrayList<int[]>> CopyListofLists (ArrayList<ArrayList<int[]>> list){
		//Create a copy of the list of lists
		ArrayList<ArrayList<int[]>> copy = new ArrayList<ArrayList<int[]>>();
		copy.ensureCapacity(list.size());
		for (int i = 0; i < list.size(); i++){
			copy.add(new ArrayList<int[]>(list.get(i)));
		}
		return copy;
	}
	
	public static boolean isInside (ArrayList<int[]> nodes, int node) {
		//Determine whether a node is in a group (of nodes)
		for (int i = 0; i < nodes.size(); i ++) {
			if (nodes.get(i)[0] == node) {
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList <int[]> RemoveEdges (ArrayList <int[]> nodes, ArrayList <int[]> edges) {
		//Remove redundant (self-to-self) edges pointing to a given group (of nodes)
		ArrayList <int[]> copy = new ArrayList <int[]>();
		copy.ensureCapacity(edges.size());
		for (int i = 0; i < edges.size(); i ++) {
			int[] check = edges.get(i);
			if (!(isInside(nodes, check[0]) && isInside(nodes, check[1]))) {
				copy.add(check);
			}
		}
		return copy;
	}
	
	public static ArrayList <int[]> MergeLists (ArrayList <int[]> to, ArrayList <int[]> from) {
		//Merge target groups
		while (!from.isEmpty()) {
			to.add(from.remove(from.size() - 1));
		}
		return to;
	}
	
	public static ArrayList <int[]> FindCut (ArrayList <ArrayList<int[]>> groups, ArrayList <int[]> edges) {
		//Find a cut of the graph (into two parts) and its corresponding number of edges
		Random gen = new Random ();
		while (groups.size() > 2) {
			//Choose an edge randomly and merge the groups on either side of that edge before removing
			//any resulting redundant edges
			int count = 0;
			int i = 0;
			int [] targets = new int [2];
			int [] merge_nodes = edges.get(gen.nextInt(edges.size()));
			while (count < 2) {
				if (isInside(groups.get(i), merge_nodes[0]) || isInside(groups.get(i), merge_nodes[1])) {
					targets[count] = i;
					count ++;
				}
				i ++;
			}
			for (i = 0; i < 2; i ++) {
				ArrayList<int[]> temp = groups.get(targets[i]);
				groups.set(targets[i], groups.get(groups.size()-1-i));
				groups.set(groups.size()-1-i, temp);
			}
			groups.add (MergeLists (groups.remove(groups.size()-1), groups.remove(groups.size()-1)));
			edges = RemoveEdges (groups.get(groups.size() - 1), edges);
		}
		return edges;
	}

	public static void main(String[] args) {
		//Get data from the file
		String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms I/kargerAdj.txt";
		String line;
		ArrayList <ArrayList<int[]>> groups = new ArrayList <ArrayList<int[]>>();
		ArrayList <int[]> edges = new ArrayList <int[]>();
		try {
			BufferedReader input = new BufferedReader (new FileReader (path));
			while ((line=input.readLine())!=null) {
				StringTokenizer st = new StringTokenizer (line, " \t");
				int node_id = Integer.parseInt(st.nextToken());
				int[] node = {node_id};
				ArrayList <int[]> nodes = new ArrayList <int[]>();
				nodes.add(node);
				groups.add(nodes);
				while (st.hasMoreTokens()) {
					int test = Integer.parseInt(st.nextToken());
					if (test > node_id) {
						int[] edge = {node_id, test};
						edges.add (edge);
					}
				}
			}
			input.close ();
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
		//Procedure: Repeat FindCut n^2 times, each time with a copy of groups and edges, keeping track of
		//			 the lowest cut achieved
		int min = -1;
		ArrayList <ArrayList<int[]>> groups_min = new ArrayList <ArrayList<int[]>>();
		ArrayList <int[]> edges_min = new ArrayList <int[]>();
		for (int i = 0, max = (int)pow(groups.size(),2); i < max; i ++) {
			ArrayList <ArrayList<int[]>> groups_copy = CopyListofLists(groups);
			ArrayList <int[]> edges_copy = FindCut (groups_copy, edges);
			if (min == -1 || edges_copy.size() < min) {
				min = edges_copy.size();
				groups_min = CopyListofLists(groups_copy);
				edges_min = new ArrayList<int[]>(edges_copy);
			}
		}
		System.out.println(min);
		
		//For shits
		System.out.println("The cut resulted in the following two groups:");
		for (int i = 0; i < groups_min.size(); i ++) {
			ArrayList<int[]> temp = groups_min.get(i);
			for (int j = 0; j < temp.size(); j ++) {
				System.out.print(temp.get(j)[0] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("The following edges remain:");
		for (int i = 0; i < edges_min.size(); i ++) {
			int[] temp = edges_min.get(i);
			for (int j = 0; j < temp.length; j ++) {
				System.out.print(temp[j] + " ");
			}
			System.out.println();
		}
	}
}

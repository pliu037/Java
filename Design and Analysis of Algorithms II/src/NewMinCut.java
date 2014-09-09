import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Collections;
import DataStructs.WQUPC;
import static java.lang.Math.pow;

public class NewMinCut {
	
	public static WQUPC minCut (ArrayList<MaxSpacing.Edge> edges, int vertices) {
		WQUPC min_cut = new WQUPC ();
		int min = edges.size(); 
		for (int j = 0, max = (int)pow(vertices, 2); j < max; j ++) {
			Collections.shuffle(edges);
			WQUPC candidate = WQUPC.getWQUPC (vertices);
			for (int i = 0; i < edges.size(); i ++) {
				candidate.union(edges.get(i).a, edges.get(i).b);
				if (candidate.getGroups() == 2) {
					int count = 0;
					i ++;
					while (i < edges.size()) {
						if (candidate.find(edges.get(i).a) != candidate.find(edges.get(i).b)) {
							count ++;
						}
						i ++;
					}
					if (count < min) {
						min = count;
						min_cut = candidate;
					}
				}
			}
		}
		System.out.println(min);
		return min_cut;
	}
	
	public static void main(String[] args) {
		//Get data
		try {
			String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms I/kargerAdj.txt";
			String line;
			ArrayList<MaxSpacing.Edge> edges = new ArrayList<>();
			BufferedReader input = new BufferedReader (new FileReader (path));
			int max = 0;
			while ((line=input.readLine())!=null) {
				StringTokenizer st = new StringTokenizer (line, " \t");
				int head = Integer.parseInt(st.nextToken());
				if (head > max) {
					max = head;
				}
				while (st.hasMoreTokens()) {
					int tail = Integer.parseInt(st.nextToken());
					if (tail > max) {
						max = tail;
					}
					if (tail > head) {
						edges.add(new MaxSpacing.Edge(head, tail, 0));
					}
				}
			}
			input.close ();
			//Procedure
			System.out.println(minCut(edges, max).getRaw());
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
	}
}

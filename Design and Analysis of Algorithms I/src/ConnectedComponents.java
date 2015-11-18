import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class ConnectedComponents {

	static class Digraph {

        private ArrayList<ArrayList<Integer>> forwardEdges = new ArrayList<>(), reverseEdges = null;
        private int numNodes;

        public Digraph (int size) {
            for (int i = 0; i < size; i ++) {
                forwardEdges.add(new ArrayList<>());
            }
            numNodes = size;
        }

        public void addEdge(int from, int to) {
            forwardEdges.get(from).add(to);
        }

        public void reverse() {
            if (reverseEdges == null) {
                reverseEdges = new ArrayList<>();
                for (int i = 0; i < numNodes; i ++) {
                    reverseEdges.add(new ArrayList<>());
                }
                for (int from = 0; from < numNodes; from ++) {
                    ArrayList<Integer> current = forwardEdges.get(from);
                    for (int i = 0; i < current.size(); i ++) {
                        int to = current.get(i);
                        reverseEdges.get(to).add(from);
                    }
                }
            }
            ArrayList<ArrayList<Integer>> temp = forwardEdges;
            forwardEdges = reverseEdges;
            reverseEdges = temp;
        }

        public void print() {
            for (int i = 0; i < forwardEdges.size(); i ++) {
                System.out.print(i+1 + ": ");
                ArrayList<Integer> current = forwardEdges.get(i);
                for (int j = 0; j < current.size(); j ++) {
                    System.out.print(current.get(j)+1 + " ");
                }
                System.out.println();
            }
        }

        public ArrayList<Integer> adj(int from) {
            return forwardEdges.get(from);
        }

        public int getNodes() {
            return numNodes;
        }

    }

    static class Xrange implements Iterator<Integer> {

        private int end;
        private int current;

        public Xrange (int begin, int end) {
            this.end = end;
            this.current = begin;
        }

        public boolean hasNext() {
            if (current < end) {
                return true;
            }
            return false;
        }

        public Integer next() {
            if (hasNext()) {
                return current ++;
            }
            return null;
        }
    }

    static class Topological {

        private Digraph graph;
        private ArrayDeque<Integer> order;
        private boolean[] visited;
        private int[] last_checked;
        private ArrayList<Integer> group_sizes;
        private Iterator<Integer> iter = null;

        public Topological (Digraph graph) {
            this(graph, null);
        }

        public Topological (Digraph graph, Iterable<Integer> iter) {
            this.graph = graph;
            this.order = new ArrayDeque<>();
            this.visited = new boolean[graph.getNodes()];
            this.last_checked = new int[graph.getNodes()];
            this.group_sizes = new ArrayList<>();
            if (iter == null) {
                this.iter = new Xrange(0, graph.getNodes());
            }
            else {
                this.iter = iter.iterator();
            }
            findTopological();
        }

        public void findTopological() {
            while (iter.hasNext()) {
                int i = iter.next();
                if (!visited[i]) {
                    int count = 0;
                    Stack<Integer> visiting = new Stack<>();
                    visiting.push(i);
                    while (!visiting.isEmpty()) {
                        int current = visiting.peek();
                        visited[current] = true;
                        int next = childrenVisited(current);
                        if (next == -1) {
                            order.push(visiting.pop());
                            count ++;
                        }
                        else {
                            visiting.push(next);
                        }
                    }
                    group_sizes.add(count);
                }
            }
        }

        private int childrenVisited (int check) {
            while (last_checked[check] < graph.adj(check).size()) {
                int next = graph.adj(check).get(last_checked[check]);
                last_checked[check] ++;
                if (!visited[next]) {
                    return next;
                }
            }
            return -1;
        }

        public ArrayList<Integer> getGroupSizes() {
            return group_sizes;
        }

        public ArrayDeque<Integer> getTopological () {
            return order;
        }
    }

    public static void FindSCC(Digraph graph) {
        graph.reverse();
        Topological top = new Topological(graph);
        ArrayDeque<Integer> order = top.getTopological();

        graph.reverse();
        top = new Topological(graph, order);
        ArrayList<Integer> group_sizes = top.getGroupSizes();

        Collections.sort(group_sizes);
        Collections.reverse(group_sizes);
        for (int i = 0; i < 5; i ++) {
            System.out.println(group_sizes.get(i));
        }
    }
	
	public static void main(String[] args) {
		//Find the maximum node in the file
		String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms I/SCC.txt";
		String line;
		int max = 0;
		try {
			BufferedReader input = new BufferedReader (new FileReader (path));
			while ((line=input.readLine())!=null) {
				StringTokenizer st = new StringTokenizer (line, " \t");
				while (st.hasMoreTokens()) {
					int check = Integer.parseInt(st.nextToken());
					if (check > max) {
						max = check;
					}
				}
			}
			input.close ();
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
		//Get data from the file
		Digraph graph = new Digraph(max);
		try {
			BufferedReader input = new BufferedReader (new FileReader (path));
			while ((line=input.readLine())!=null) {
				StringTokenizer st = new StringTokenizer (line, " \t");
				Integer to = Integer.valueOf(st.nextToken())-1;
				Integer from = Integer.valueOf(st.nextToken())-1;
				graph.addEdge(to, from);
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

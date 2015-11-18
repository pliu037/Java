import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by pengl on 2015-11-10.
 */
public class SAP {

    private Digraph wordNet;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.wordNet = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        ArrayList<Integer> _v = new ArrayList<>(), _w = new ArrayList<>();
        _v.add(v);
        _w.add(w);
        return length(_v, _w);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        ArrayList<Integer> _v = new ArrayList<>(), _w = new ArrayList<>();
        _v.add(v);
        _w.add(w);
        return ancestor(_v, _w);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return findAncestor(v, w)[1];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return findAncestor(v, w)[0];
    }

    private int[] findAncestor(Iterable<Integer> v, Iterable<Integer> w) {
        HashMap<Integer, Integer> left_seen = new HashMap<>(), right_seen = new HashMap<>();
        LinkedList<Integer> left_queue = new LinkedList<>(), right_queue = new LinkedList<>();
        for (Integer i : v) {
            left_seen.put(i, 0);
            left_queue.add(i);
        }
        for (Integer i : w) {
            right_seen.put(i, 0);
            right_queue.add(i);
        }
        left_queue.add(-1);
        right_queue.add(-1);
        while (true) {
            if (left_queue.size() == 1 && right_queue.size() == 1) {
                return new int[] {-1, -1};
            }
            while (left_queue.peek() != -1) {
                int current = left_queue.remove();
                int current_length = left_seen.get(current);
                Iterable<Integer> paths = wordNet.adj(current);
                for (Integer i : paths) {
                    if (right_seen.containsKey(i)) {
                        return new int[] {i, current_length + 1 + right_seen.get(i)};
                    }
                    left_seen.put(i, current_length + 1);
                    left_queue.add(i);
                }
            }
            left_queue.remove();
            left_queue.add(-1);
            while (right_queue.peek() != -1) {
                int current = right_queue.remove();
                int current_length = right_seen.get(current);
                Iterable<Integer> paths = wordNet.adj(current);
                for (Integer i : paths) {
                    if (left_seen.containsKey(i)) {
                        return new int[] {i, current_length + 1 + left_seen.get(i)};
                    }
                    right_seen.put(i, current_length + 1);
                    right_queue.add(i);
                }
            }
            right_queue.remove();
            right_queue.add(-1);
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }

}

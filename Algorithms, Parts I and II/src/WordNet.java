import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class WordNet {

    private HashMap<String, Integer> words;
    private HashMap<Integer, String> intMap;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        words = new HashMap<>();
        intMap = new HashMap<>();
        In in = new In (synsets);
        int count = 0;
        while (!in.isEmpty()) {
            String[] tokens = in.readLine().split(",");
            words.put(tokens[1], Integer.parseInt(tokens[0]));
            intMap.put(Integer.parseInt(tokens[0]), tokens[1]);
            count ++;
        }
        in.close();
        Digraph wordNet = new Digraph(count);
        in = new In (hypernyms);
        while (!in.isEmpty()) {
            String[] tokens = in.readLine().split(",");
            for (int i = 1; i < tokens.length; i ++) {
                wordNet.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
            }
        }
        in.close();
        sap = new SAP(wordNet);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return words.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (words.containsKey(word)) {
            return true;
        }
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return sap.length(words.get(nounA), words.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        Integer synset = sap.ancestor(words.get(nounA), words.get(nounB));
        return intMap.get(synset);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet test = new WordNet(".\\Algorithms, Parts I and II\\wordNet\\synsets.txt", ".\\Algorithms, Parts I and II\\wordNet\\hypernyms.txt");
        System.out.println(test.distance("hoot", "honey"));
        System.out.println(test.sap("hoot", "honey"));
    }

}

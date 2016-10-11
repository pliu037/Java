import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private WordNet wordNet;

    public Outcast(WordNet wordnet) {        // constructor takes a WordNet object
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {  // given an array of WordNet nouns, return an outcast
        int[][] scores = new int[nouns.length][nouns.length];
        for (int i = 0; i < nouns.length; i ++) {
            for (int j = i; j < nouns.length; j ++) {
                scores[i][j] = wordNet.distance(nouns[i], nouns[j]);
                scores[j][i] = scores[i][j];
            }
        }
        int max_sum = 0, max_position = -1, sum;
        for (int i = 0; i < nouns.length; i ++) {
            sum = 0;
            for (int j = 0; j < nouns.length; j ++) {
                sum += scores[i][j];
            }
            if (sum > max_sum) {
                max_sum = sum;
                max_position = i;
            }
        }
        return nouns[max_position];
    }

    public static void main(String[] args) { // see test client below
        WordNet wordnet = new WordNet(".\\Algorithms, Parts I and II\\wordNet\\synsets.txt", ".\\Algorithms, Parts I and II\\wordNet\\hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        String[] nouns = new String[]{"zebra", "cat", "bear", "table"};
        StdOut.println("Outcast: " + outcast.outcast(nouns));
    }

}

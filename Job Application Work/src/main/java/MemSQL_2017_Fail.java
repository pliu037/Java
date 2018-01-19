import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

// Given a board of letters and a set of valid words, find all valid words that can be formed as a path on the board
// (diagonal movement is allowed but the same "tile" cannot be traversed twice in the same path)

/**
 * Build a trie from the set of valid words. Then perform a trie-guided DFS from every starting location on the board.
 * Failed to implement trie-guided DFS
 * Analysis:
 * - started coding before fully flushing out what needed to be kept track of, leading to confusing "hot fixes" while
 *   coding
 * - need to understand the input state of the recursive call prior to coding it
 * Optimizations:
 * - instead of a flag to indicate a "word node" within the trie, we can store the actual word that is completed at that
 *   node, simplifying the DFS implementation as we no longer need to keep track of a path's prefix or backtrack to the
 *   root once a "word node" is found
 * - since we don't care about how many times a given word occurs, we can prune the trie upon finding a word, thereby
 *   further constraining subsequent paths (would not have had time to implement this)
 * - can preprocess valid words so that valid words that are substrings, both in the forward and reverse direction, of
 *   other valid words are merged into their longest such valid words (THIS IS INCORRECT AS <.*><TARGET STRING> !=
 *   <SPECIFIC PREFIX><TARGET STRING>)
 */

public class MemSQL_2017_Fail {
    private static class Node {
        HashMap<Character, Node> map;
        String word;

        Node() {
            map = new HashMap<>();
            word = null;
        }
    }

    private static final int[] DELTA = {-1, 0, 1};

    private final Node root;
    private final char[][] board;
    private final int max_x;
    private final int max_y;

    public MemSQL_2017_Fail(Set<String> validWords, char[][] board) {
        root = convertWordSetToTrie(validWords);
        this.board = board;

        // Assumption: Board is rectangular
        max_x = board.length;
        max_y = board[0].length;
    }

    public Object[] solve() {
        HashSet<String> foundWords = new HashSet<>();
        for (int x = 0; x < max_x; x ++) {
            for (int y = 0; y < max_y; y ++) {
                boolean[][] visited = new boolean[max_x][max_y];
                HashSet<String> dfsWords = new HashSet<>();
                dfs(x, y, root, visited, dfsWords);
                foundWords.addAll(dfsWords);
            }
        }
        return foundWords.toArray();
    }

    private void dfs(int x, int y, Node currentNode, boolean[][] visited, HashSet<String> foundWords) {
        if (!currentNode.map.containsKey(board[x][y])) {
            return;
        }

        visited[x][y] = true;
        currentNode = currentNode.map.get(board[x][y]);

        if (currentNode.word != null) {
            foundWords.add(currentNode.word);
        }

        for (int aDELTA1 : DELTA) {
            int new_x = x + aDELTA1;
            if (new_x >= 0 && new_x < max_x) {
                for (int aDELTA2 : DELTA) {
                    int new_y = y + aDELTA2;
                    if (new_y >= 0 && new_y < max_y && !visited[new_x][new_y]) {
                        dfs(new_x, new_y, currentNode, visited, foundWords);
                    }
                }
            }
        }

        visited[x][y] = false;
    }

    private static Node convertWordSetToTrie(Set<String> validWords) {
        Node root = new Node();
        for (String word : validWords) {
            Node current = root;
            for (Character c : word.toCharArray()) {
                if (!current.map.containsKey(c)) {
                    current.map.put(c, new Node());
                }
                current = current.map.get(c);
            }
            current.word = word;
        }
        return root;
    }

    public static void main(String[] args) {
        String[] validWords = {"word", "war"};
        Set<String> validWordsSet = new HashSet<>(Arrays.asList(validWords));
        char[][] board = {{'w', 'o', 'r', 'd'}, {'a', 'g', 'e', 't'}, {'r', 'g', 't', 'u'}, {'z', 'z', 'z', 'z'}};
        MemSQL_2017_Fail obj = new MemSQL_2017_Fail(validWordsSet, board);
        Object[] words = obj.solve();
        for (Object word : words) {
            System.out.println(word);
        }
    }
}

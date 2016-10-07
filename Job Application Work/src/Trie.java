public class Trie {

    Trie[] children = new Trie[256];
    boolean endOfWord = false;

    boolean contains(String str) {
        if (str.length() == 0) {
            if (endOfWord) {
                return true;
            } else {
                return false;
            }
        }
        int index = str.charAt(0);
        if (children[index] == null) {
            return false;
        }
        return children[index].contains(str.substring(1));
    }

    void insert(String str) {
        if (str.length() == 0) {
            endOfWord = true;
        } else {
            int index = str.charAt(0);
            if (children[index] == null) {
                children[index] = new Trie();
            }
            children[index].insert(str.substring(1));
        }
    }

    public static void main(String[] args) {
        Trie root = new Trie();
        root.insert("cat");
        root.insert("caterpillar");
        System.out.println(root.contains("ca"));
        System.out.println(root.contains("caterpi"));
        System.out.println(root.contains("cat"));
        System.out.println(root.contains("caterpillar"));
    }
}

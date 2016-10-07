class Trie {

    Trie[] children = new Trie[256];
    boolean endOfWord = false;

    boolean contains(String str) {
        if (str.length() == 0 && endOfWord) {
            return true;
        } else {
            return false;
        }

        str.get
        return children[1].contains(str.substring(1));
    }

    void insert(String str) {

    }

    public static void main(String[] args) {
        Trie root = new Trie();
    }
}

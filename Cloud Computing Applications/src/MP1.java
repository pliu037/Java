import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    private class Tuple implements Comparable<Tuple> {

        private final String str;
        private final Integer count;

        private Tuple (String str, Integer count) {
            this.str = str;
            this.count = count;
        }

        public int compareTo (Tuple other) {
            if (count < other.count) {
                return - 1;
            }
            else if (count > other.count) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    public String[] process() throws Exception {
        String[] ret = new String[20];
       
        //TODO
        HashSet<String> notIncluded = new HashSet<>(Arrays.asList(stopWordsArray));
        HashMap<Integer, Integer> indices = new HashMap<>();
        HashMap<String, Integer> counts = new HashMap<>();

        for (Integer index : getIndexes()) {
            if (indices.containsKey(index)) {
                indices.put(index, indices.get(index) + 1);
            }
            else {
                indices.put(index, 1);
            }
        }

        try(BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            int index = 0;
            for(String line; (line = br.readLine()) != null; ) {
                if (indices.containsKey(index)) {
                    StringTokenizer tok = new StringTokenizer(line, delimiters);
                    while (tok.hasMoreTokens()) {
                        String check = tok.nextToken().toLowerCase().trim();
                        if (!notIncluded.contains(check)) {
                            if (counts.containsKey(check)) {
                                counts.put(check, counts.get(check) + indices.get(index));
                            } else {
                                counts.put(check, indices.get(index));
                            }
                        }
                    }
                }
                index ++;
            }
        }

        Tuple[] tuples = new Tuple[counts.size()];
        int i = 0;
        for (String key : counts.keySet()) {
            tuples[i] = new Tuple(key, counts.get(key));
            i ++;
        }
        Arrays.sort(tuples, Collections.reverseOrder());

        for (i = 0; i < 20; i ++) {
            ret[i] = tuples[i].str;
        }

        return ret;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "D:/Work/Programming/Java/Cloud Computing Applications/input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
}

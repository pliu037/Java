import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringBuild {

    static void recursiveParse(String str, List<String> words, List<String> stack) {
        if (str.length() == 0) {
            return;
        }

        for (String word : words) {
            if (str.startsWith(word)) {
                stack.add(word);
                recursiveParse(str.substring(word.length(), str.length()), words, stack);
                if (stack.get(stack.size() - 1) == null) {
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                } else {
                    return;
                }
            }
        }

        stack.add(null);
    }

    static String buildString(List<String> words, String separator) {
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word);
            sb.append(separator);
        }
        String str = sb.toString();
        return str.substring(0, str.length() - 1);
    }

    static String parseString(String str, Set<String> words) {
        ArrayList<String> stack = new ArrayList<>();
        boolean strInSet = false;
        if (words.contains(str)) {
            strInSet = true;
            words.remove(str);
        }

        ArrayList<String> sortedWords = new ArrayList<>(words);
        sortedWords.sort((s1, s2) -> {
            if (s1.length() < s2.length()) {
                return 1;
            } else {
                return -1;
            }
        });
        recursiveParse(str, sortedWords, stack);

        if (stack.get(0) == null) {
            return "invalid target";
        }
        return buildString(stack, strInSet ? "+" : ",");
    }

    public static void main(String[] args) {
        Set<String> words = new HashSet<>();
        words.add("bad");
        words.add("mint");
        words.add("ton");
        words.add("on");
        words.add("min");
        words.add("badminton");
        System.out.println(parseString("badminton", words));
    }
}

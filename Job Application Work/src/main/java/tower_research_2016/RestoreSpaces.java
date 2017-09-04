package tower_research_2016;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RestoreSpaces {

    static String parseString(String str, Set<String> words) {
        ArrayList<String> stack = new ArrayList<>();
        StringBuild.recursiveParse(str, new ArrayList<>(words), stack);
        if (stack.get(0) == null) {
            return "invalid target";
        }
        return StringBuild.buildString(stack, " ");
    }

    public static void main(String[] args) {
        Set<String> words = new HashSet<>();
        words.add("butter");
        words.add("in");
        words.add("industry");
        words.add("invest");
        words.add("peanut");
        words.add("the");
        System.out.println(parseString("investinthepeanutbutterindustry", words));
    }
}

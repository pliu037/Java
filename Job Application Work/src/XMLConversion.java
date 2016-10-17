import java.util.ArrayList;
import java.util.HashMap;

public class XMLConversion {

    static HashMap<String, String> map = new HashMap<>();
    static String openingInv = "<inv>";
    static String openingB = "<b>";
    static String closingInv = "</inv>";
    static String closingB = "</b>";

    static {
        map.put(openingInv, closingInv);
        map.put(openingB, closingB);
    }

    static String convert(String str) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> stack = new ArrayList<>();
        boolean inverted = false;
        for (int i = 0; i < str.length(); i ++) {
            if (str.substring(i).startsWith(openingInv)) {
                i += openingInv.length() - 1;
                stack.add(openingInv);
                inverted = !inverted;
            } else if (str.substring(i).startsWith(openingB)) {
                i += openingB.length() - 1;
                stack.add(openingB);
                sb.append('*');
            } else if (str.substring(i).startsWith(closingInv)) {
                i += closingInv.length() - 1;
                if (stack.isEmpty() || !map.get(stack.remove(stack.size() - 1)).equals(closingInv)) {
                    return "Error";
                }
                inverted = !inverted;
            } else if (str.substring(i).startsWith("</b>")) {
                i += closingB.length() - 1;
                if (stack.isEmpty() || !map.get(stack.remove(stack.size() - 1)).equals(closingB)) {
                    return "Error";
                }
                sb.append('*');
            } else {
                if (inverted && str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                    sb.append(Character.toUpperCase(str.charAt(i)));
                } else if (inverted && str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') {
                    sb.append(Character.toLowerCase(str.charAt(i)));
                } else {
                    sb.append(str.charAt(i));
                }
            }
        }

        if (!stack.isEmpty()) {
            return "Error";
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String test = "<b><inv>inverted</inv></b>, not inverted. <inv>inverted, <inv>not inverted</inv>,inverted.</inv>";
        System.out.println(XMLConversion.convert(test));
    }
}

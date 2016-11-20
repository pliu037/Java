import java.util.*;

public class TranspositionCipher {

    static final Set<Character> alphanumeric = new HashSet<>();

    static {
        for (char c : "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
            alphanumeric.add(c);
        }
    }

    static String filterString(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i ++) {
            Character check = Character.toUpperCase(str.charAt(i));
            if (alphanumeric.contains(check)) {
                sb.append(check);
            }
        }
        return sb.toString();
    }

    static void traverseMatrix(StringBuilder output, String str, List<List<Character>> matrix) {
        ArrayList<HashMap<Integer, Character>> order = new ArrayList<>();
        int pos = 0;
        for (List<Character> row : matrix) {
            HashMap<Integer, Character> map = new HashMap<>();
            for (int i = 0; i < row.size(); i ++) {
                if (row.get(i) == '#') {
                    if (pos < str.length()) {
                        map.put(i, str.charAt(pos));
                        pos++;
                    } else {
                         break ;
                    }
                }
            }
            order.add(map);
        }

        for (int col = 0; col < matrix.get(0).size(); col ++) {
            for (int row = 0; row < matrix.size(); row ++) {
                if (order.get(row).containsKey(col)) {
                    output.append(order.get(row).get(col));
                }
            }
        }
    }

    static String transposeString(String str, List<List<Character>> matrix) {
        int length = 0;
        for (List<Character> row : matrix) {
            for (Character c : row) {
                if (c == '#') {
                    length ++;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int start = 0; start < str.length(); start += length) {
            traverseMatrix(sb, str.substring(start, Math.min(start + length, str.length())), matrix);
        }

        return sb.toString();
    }

    static void traverseMap(StringBuilder sb, String str, int[] map) {
        for (int oldIndex : map) {
            if (oldIndex < str.length()) {
                sb.append(str.charAt(oldIndex));
            }
        }
    }

    static String betterTransposeString(String str, List<List<Character>> matrix) {
        int count = 0;
        int[] rowCounts = new int[matrix.get(0).size()];
        for (int row = 0; row < matrix.size(); row ++) {
            List<Character> r = matrix.get(row);
            for (Character c : r) {
                if (c == '#') {
                    rowCounts[row] ++;
                    count ++;
                }
            }
        }

        int[] aggRowCounts = new int[rowCounts.length];
        for (int i = 1; i < rowCounts.length; i ++) {
            aggRowCounts[i] = rowCounts[i - 1];
            aggRowCounts[i] += aggRowCounts[i - 1];
        }

        int[] newPosIndexOldPosValue = new int[count];
        rowCounts = new int[matrix.get(0).size()];
        int index = 0;
        for (int col = 0; col < matrix.get(0).size(); col ++) {
            for (int row = 0; row < matrix.size(); row ++) {
                if (matrix.get(row).get(col) == '#') {
                    newPosIndexOldPosValue[index] = aggRowCounts[row] + rowCounts[row];
                    index ++;
                    rowCounts[row] ++;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int start = 0; start < str.length(); start += count) {
            traverseMap(sb, str.substring(start, Math.min(start + count, str.length())), newPosIndexOldPosValue);
        }
        return sb.toString();
    }

    static String splitString(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i ++) {
            sb.append(str.charAt(i));
            if ((i + 1) % 5 == 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    static void transpose(String str, List<List<Character>> matrix) {
        String filteredString = filterString(str);
        System.out.println(splitString(transposeString(filteredString, matrix)));
        System.out.println(splitString(betterTransposeString(filteredString, matrix)));
    }

    public static void main(String[] args) {
        List<List<Character>> matrix = new ArrayList<>();
        ArrayList<Character> row = new ArrayList<>();
        row.add('#');
        row.add('&');
        row.add('#');
        matrix.add(row);
        row = new ArrayList<>();
        row.add('&');
        row.add('#');
        row.add('#');
        matrix.add(row);
        row = new ArrayList<>();
        row.add('#');
        row.add('#');
        row.add('#');
        matrix.add(row);
        transpose("Hi , there!!!", matrix);
    }
}

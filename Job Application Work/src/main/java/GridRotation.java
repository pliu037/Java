import java.util.ArrayList;
import java.util.List;

public class GridRotation {

    static void rotate(List<List<Character>> matrix) {
        int length = matrix.size();
        for (int row = 0; row < length / 2; row ++) {
            for (int col = 0; col < (length + 1)/ 2; col ++) {
                Character temp = matrix.get(row).get(col);
                matrix.get(row).set(col, matrix.get(length - 1 - col).get(row));
                matrix.get(length - 1 - col).set(row, matrix.get(length - 1 - row).get(length - 1 - col));
                matrix.get(length - 1 - row).set(length - 1 - col, matrix.get(col).get(length - 1 - row));
                matrix.get(col).set(length - 1 - row, temp);
            }
        }
    }

    public static void main(String[] args) {
        List<List<Character>> matrix = new ArrayList<>();
        ArrayList<Character> row = new ArrayList<>();
        row.add('a');
        row.add('b');
        row.add('c');
        row.add('d');
        matrix.add(row);
        row = new ArrayList<>();
        row.add('e');
        row.add('f');
        row.add('g');
        row.add('h');
        matrix.add(row);
        row = new ArrayList<>();
        row.add('i');
        row.add('j');
        row.add('k');
        row.add('l');
        matrix.add(row);
        row = new ArrayList<>();
        row.add('m');
        row.add('n');
        row.add('o');
        row.add('p');
        matrix.add(row);
        rotate(matrix);
        for (List<Character> list : matrix) {
            for (Character c : list) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}

import DataStructs.HashTable;
import java.io.File;

public class ParseCSV {

    private HashTable<String, String[]> index = null;
    private String filename = null;
    private int key_column = -1, num_fields = -1;

    public ParseCSV () {

    }

    private boolean getHT (String filename, int key_column) {
        File file = new File (filename);
        if(!file.exists() || file.isDirectory()) {
            System.out.println ("The file does not exist at the path given.");
            return false;
        }

        index = new HashTable<>();
        In in = new In (filename);

        //The assumption is the number of fields in the first entry is the correct number of fields
        if (!in.isEmpty()) {
            String buffer = in.readLine();
            String[] strings = buffer.split(",");
            if (key_column >= strings.length) {
                System.out.println("Key index out of bounds (0-" + (strings.length - 1) + ").");
                return false;
            }
            num_fields = strings.length;
            index.add(strings[key_column], strings);
        }
        while (!in.isEmpty()) {
            String buffer = in.readLine();
            String[] strings = buffer.split(",");
            if (strings.length != num_fields) {
                System.out.println ("\"" + buffer + "\" does not contain the correct number of fields and was discarded.");
            }
            else {
                index.add(strings[key_column], strings);
            }
        }
        return true;
    }

    public String[] lookupValues (String filename, String query, int key_column) {
        if (query == null) {
            System.out.println ("No query given.");
            return null;
        }
        if (index == null || key_column != this.key_column || filename != this.filename) {
            if (filename == null) {
                if (this.filename == null) {
                    System.out.println ("No file given.");
                    return null;
                }
                filename = this.filename;
            }
            if (getHT (filename, key_column)) {
                this.filename = filename;
                this.key_column = key_column;
            }
            else {
                return null;
            }
        }
        return index.peek(query);
    }

    public String lookup (String filename, String query, int key_column, int value_column) {
        String[] values = lookupValues (filename, query, key_column);
        if (value_column < 0 || value_column >= num_fields) {
            System.out.println("Value index out of bounds (0-" + (num_fields - 1) + ").");
            return null;
        }
        return values != null ? values[value_column] : null;
    }

    public String lookup (String query, int key_column, int value_column) {
        return lookup (null, query, key_column, value_column);
    }

    public String lookup (String query, int value_column) {
        return lookup (null, query, key_column, value_column);
    }

    public static void main (String[] args) {
        ParseCSV pcsv = new ParseCSV();
        System.out.println(pcsv.lookup("C:/Users/Peng/Desktop/test.txt", "test", 0, 2));
        System.out.println(pcsv.lookup("C:/Users/Peng/Desktop/test.txt", "test", 0, 1));
    }
}

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Q6 {

    private static void output (PrintWriter output, ArrayList<String> validEquations, int problemNum) {
        output.println("Problem " + problemNum);
        if (validEquations.size() == 0) {
            output.println("IMPOSSIBLE");
        }
        else {
            for (int i = 0; i < validEquations.size(); i++) {
                output.println(validEquations.get(i));
            }
        }
        output.println();
    }

    private static void checkOption () {

    }

    private static void checkOptions (ArrayList<String> pieces, ArrayList<String> validEquations) {

    }

    private static void parser(String input, ArrayList<String> pieces, ArrayList<String> validEquations) {
        for (int i = 1; i < input.length(); i ++) {

        }
    }

    private static ArrayList<String> findEquations(String input) {
        ArrayList<String> validEquations = new ArrayList<>();
        ArrayList<String> pieces = new ArrayList<>();
        parser(input, pieces, validEquations);
        return validEquations;
    }

    public static void main(String[] args) {
        String inputPath = args[0];
        String outputPath = args[1];
        File file = new File(inputPath);
        File file2 = new File(outputPath);
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            PrintWriter output = new PrintWriter(file2);
            String line = "";
            int problemNum = 0;
            while (!(line = input.readLine()).equals("=")) {
                problemNum ++;
                StringTokenizer tokenizer = new StringTokenizer(line, "=");
                output(output, findEquations(tokenizer.nextToken()), problemNum);
            }
            input.close();
            output.close();
        }
        catch(IOException e) {
            return;
        }
    }
}

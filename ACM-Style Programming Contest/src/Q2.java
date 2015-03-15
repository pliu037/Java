import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class Q2 {

    private static void output (int problemNum, PrintWriter output, Hashtable<Integer, Integer> counts) {
        output.println("Problem #" + problemNum);

        if (counts.size() > 0) {
            Enumeration<Integer> keyEnum = counts.keys();
            int max = 0;
            while (keyEnum.hasMoreElements()) {
                int check = keyEnum.nextElement();
                if (check > max) {
                    max = check;
                }
            }

            for (int i = 1; i <= max; i++) {
                if (counts.containsKey(i)) {
                    output.print(counts.get(i));
                }
                else {
                    output.print("0");
                }
                output.println(" rectangle(s) of area " + i);
            }
        }
        else {
            output.println("No completed rectangles can be found.");
        }

        output.println("**********************************");
        output.println();
    }

    private static void completeRectangle(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY,
                                          boolean[][] horizontals, boolean[][] verticals,
                                          Hashtable<Integer, Integer> counts) {
        int x = topLeftX;
        while(x < bottomRightX - 1) {
            if (!horizontals[x][bottomRightY]) {
                return;
            }
            x ++;
        }
        int y = topLeftY + 1;
        while(y < bottomRightY) {
            if (!verticals[topLeftX][y]) {
                return;
            }
            y ++;
        }
        int area = (bottomRightX - topLeftX)*(bottomRightY - topLeftY);
        if (counts.containsKey(area)) {
            counts.put(area, counts.get(area) + 1);
        }
        else {
            counts.put(area, 1);
        }
    }

    private static void findBottomRightCorner(int topLeftX, int topLeftY, int topRightX, int topRightY,
                                              boolean[][] horizontals, boolean[][] verticals,
                                              Hashtable<Integer, Integer> counts) {
        int height = 0;
        while (verticals[topRightX][topRightY + height]) {
            if (horizontals[topRightX - 1][topRightY + height + 1]) {
                completeRectangle(topLeftX, topLeftY, topRightX, topRightY + height + 1, horizontals,
                        verticals, counts);
            }
            height ++;
        }
    }

    private static void findTopRightCorner(int topLeftX, int topLeftY, boolean[][] horizontals,
                                           boolean[][] verticals, Hashtable<Integer, Integer> counts) {
        int width = 0;
        while (horizontals[topLeftX + width][topLeftY]) {
            if (verticals[topLeftX + width + 1][topLeftY]) {
                findBottomRightCorner(topLeftX, topLeftY, topLeftX + width + 1, topLeftY, horizontals,
                        verticals, counts);
            }
            width ++;
        }
    }

    private static Hashtable<Integer, Integer> solveProblem(boolean[][] horizontals, boolean[][] verticals) {
        Hashtable<Integer, Integer> counts = new Hashtable<>();
        int dimensions = horizontals.length;
        for (int y = 0; y < dimensions - 1; y ++) {
            for (int x = 0; x < dimensions - 1; x ++) {
                if (horizontals[x][y] && verticals[x][y]) {
                    findTopRightCorner(x, y, horizontals, verticals, counts);
                }
            }
        }
        return counts;
    }

    public static void main (String[] args) {
        String inputPath = args[0];
        String outputPath = args[1];
        File file = new File(inputPath);
        File file2 = new File(outputPath);
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            PrintWriter output = new PrintWriter(file2);
            String line = "";
            int problemNum = 0;
            while ((line = input.readLine()) != null) {
                problemNum ++;
                int dimensions = Integer.parseInt(line);
                boolean[][] horizontals = new boolean[dimensions][dimensions];
                boolean[][] verticals = new boolean[dimensions][dimensions];
                int segments = Integer.parseInt(input.readLine());
                for (int i = 0; i < segments; i ++) {
                    line = input.readLine();
                    StringTokenizer tokenizer = new StringTokenizer(line, " ");
                    String token = tokenizer.nextToken();
                    int x = Integer.parseInt(tokenizer.nextToken()) - 1;
                    int y = Integer.parseInt(tokenizer.nextToken()) - 1;
                    if (token.equals("H")) {
                        horizontals[y][x] = true;
                    }
                    else {
                        verticals[x][y] = true;
                    }
                }

                /*for (int y = 0; y < dimensions; y ++) {
                    for (int x = 0; x < dimensions; x ++) {
                        System.out.print(horizontals[x][y]);
                    }
                    System.out.println();
                }
                System.out.println();
                for (int y = 0; y < dimensions; y ++) {
                    for (int x = 0; x < dimensions; x ++) {
                        System.out.print(verticals[x][y]);
                    }
                    System.out.println();
                }*/

                Hashtable<Integer, Integer> counts = solveProblem(horizontals, verticals);
                output(problemNum, output, counts);
            }
            input.close();
            output.close();
        }
        catch (IOException e) {
            return;
        }
    }
}

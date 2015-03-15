import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Q1 {

    private static final int RIGHT_ALIGNED = 0;
    private static final int CENTRE_ALIGNED = 1;
    private static final int LEFT_ALIGNED = 2;

    private static ArrayList<String> getCanLines(String canLabel) {
        StringTokenizer tokenizer = new StringTokenizer(canLabel, "#");
        ArrayList<String> lines = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            lines.add(tokenizer.nextToken());
        }
        return lines;
    }

    private static int getCanWidth(String canLabel) {
        StringTokenizer tokenizer = new StringTokenizer(canLabel, "#");
        int maxWidth = 0;
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.length() > maxWidth) {
                maxWidth = token.length();
            }
        }
        return maxWidth;
    }

    private static String getSegment(String primary, char filler, int length, int alignment) {
        StringBuilder strngbldr = new StringBuilder();
        int numFiller = length - primary.length();
        if (alignment == RIGHT_ALIGNED) {
            strngbldr.append(getSegment(numFiller, filler));
            strngbldr.append(primary);
        }
        else if (alignment == LEFT_ALIGNED) {
            strngbldr.append(primary);
            strngbldr.append(getSegment(numFiller, filler));
        }
        else /*if (alignment == CENTRE_ALIGNED)*/ {
            strngbldr.append(getSegment(numFiller/2, filler));
            strngbldr.append(primary);
            strngbldr.append(getSegment(length - numFiller/2 - primary.length(), filler));
        }
        return strngbldr.toString();
    }

    private static String getSegment(int length, char filler) {
        StringBuilder strngbldr = new StringBuilder();
        for (int i = 0; i < length; i ++) {
            strngbldr.append(filler);
        }
        return strngbldr.toString();
    }

    private static String[] drawCan(String canLabel) {
        StringBuilder[] rows = new StringBuilder[8];
        for (int i = 0; i < 8; i ++) {
            rows[i] = new StringBuilder();
        }
        ArrayList<String> lines = getCanLines(canLabel);
        int canWidth = getCanWidth(canLabel);

        rows[0].append(getSegment(canWidth + 2, '#'));
        rows[7].append(getSegment(canWidth + 2, '#'));
        for (int row = 1; row < 7; row ++) {
            rows[row].append("#");
        }

        int diff = 6 - lines.size();
        for (int i = 0; i < diff/2; i ++) {
            rows[1 + i].append(getSegment(canWidth, ' '));
        }
        for (int i = 0; i < lines.size(); i ++) {
            rows[1 + i + diff/2].append(getSegment(lines.get(i), ' ', canWidth, CENTRE_ALIGNED));
        }
        for (int i = 0; i < 6 - diff/2 - lines.size(); i ++) {
            rows[1 + i + lines.size() + diff/2].append(getSegment(canWidth, ' '));
        }

        for (int row = 1; row < 7; row ++) {
            rows[row].append("#");
        }

        String[] can = new String[8];
        for (int i = 0; i < 8; i ++) {
            can[i] = rows[i].toString();
        }
        return can;
    }

    private static String[] getAlignedCan(int layer, ArrayList<String> stack, int maxWidth) {
        String[] can = drawCan(stack.get(layer - 1));
        int offset = 0;
        for (int i = 1; i < layer; i ++) {
            offset += (getCanWidth(stack.get(i - 1)) - getCanWidth(stack.get(i)))/2;
        }

        for (int i = 0; i < 8; i ++) {
            can[i] = getSegment(offset, ' ') + can[i];
            can[i] = can[i] + getSegment(maxWidth + 2 - can[i].length(), ' ');
        }

        return can;
    }

    private static void output(int problemNum, PrintWriter output, ArrayList<ArrayList<String>> stacks) {
        int maxStackHeight = 0;
        for (int stackNum = 0; stackNum < stacks.size(); stackNum ++) {
            if (stacks.get(stackNum).size() > maxStackHeight) {
                maxStackHeight = stacks.get(stackNum).size();
            }
        }
        int[] maxWidths = new int[stacks.size()];
        for (int stackNum  = 0; stackNum < stacks.size(); stackNum ++) {
            maxWidths[stackNum] = getCanWidth(stacks.get(stackNum).get(0));
        }

        output.println("Can Stack #" + problemNum + ":");
        StringBuilder strngbldr = new StringBuilder();
        for (int i = 1; i <= 6; i ++) {
            strngbldr.append(getSegment(Integer.toString(i), ' ', 10, RIGHT_ALIGNED));
        }
        output.println(strngbldr.toString());
        for (int i = 0; i < 6; i ++) {
            for (int j = 1; j <= 10; j ++) {
                output.print(j % 10);
            }
        }
        output.println();

        for (int layer = maxStackHeight; layer > 0; layer --) {
            StringBuilder[] rows = new StringBuilder[8];
            for (int i = 0; i < 8; i ++) {
                rows[i] = new StringBuilder();
            }

            for (int stackNum = 0; stackNum < stacks.size(); stackNum ++) {
                if (stacks.get(stackNum).size() >= layer) {
                    String[] alignedCan = getAlignedCan(layer, stacks.get(stackNum), maxWidths[stackNum]);
                    for (int i = 0; i < 8; i ++) {
                        rows[i].append(alignedCan[i]);
                        rows[i].append(" ");
                    }
                }
                else {
                    for (int i = 0; i < 8; i ++) {
                        rows[i].append(getSegment(maxWidths[stackNum] + 3, ' '));
                    }
                }
            }

            for (int i = 0; i < 8; i ++) {
                output.println(rows[i]);
            }
        }
    }

    private static ArrayList<ArrayList<String>> generateStacks(String[] cans) {
        ArrayList<ArrayList<String>> stacks = new ArrayList<>();
        ArrayList<Integer> topStackWidths = new ArrayList<>();
        for (int i = 0; i < cans.length; i ++) {
            boolean placed = false;
            for (int stackNum = 0; !placed && stackNum < stacks.size(); stackNum ++) {
                if (getCanWidth(cans[i]) < topStackWidths.get(stackNum)) {
                    placed = true;
                    stacks.get(stackNum).add(cans[i]);
                    topStackWidths.set(stackNum, getCanWidth(cans[i]));
                }
            }
            if (!placed) {
                ArrayList<String> newStack = new ArrayList<>();
                newStack.add(cans[i]);
                stacks.add(newStack);
                topStackWidths.add(getCanWidth(cans[i]));
            }
        }
        return stacks;
    }

    public static void main (String[] args) {
        String inputPath = args[0];
        String outputPath = args[1];
        File file = new File(inputPath);
        File file2 = new File(outputPath);
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            PrintWriter output = new PrintWriter(file2);
            int numProblems = Integer.parseInt(input.readLine());
            for (int problem = 1; problem <= numProblems; problem ++) {
                int numCans = Integer.parseInt(input.readLine());
                String[] cans = new String[numCans];
                for (int canNum = 0; canNum < numCans; canNum ++) {
                    cans[canNum] = input.readLine();
                }
                ArrayList<ArrayList<String>> stacks = generateStacks(cans);

                /*for (int i = 0; i < stacks.size(); i ++) {
                    for (int j = 0; j < stacks.get(i).size(); j ++) {
                        System.out.print(stacks.get(i).get(j));
                    }
                    System.out.println();
                }*/

                output(problem, output, stacks);
                output.println();
            }
            input.close();
            output.close();
        }
        catch (IOException e) {
            return;
        }
    }
}

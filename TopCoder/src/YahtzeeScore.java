//1-25 SRM 146 DIV 2 250

public class YahtzeeScore {

    /**
     * For each distinct value in toss, finds the sum of elements in toss with that value and
     * returns the maximum sum.
     */
    public int maxPoints(int[] toss) {
        int currentMax = 0;
        for (int i = 0; i < toss.length; i ++) {
            int check = toss[i];
            for (int j = i + 1; j < toss.length; j ++) {
                if (toss[j] == toss[i]) {
                    check += toss[j];
                }
            }
            if (check > currentMax) {
                currentMax = check;
            }
        }
        return currentMax;
    }

    public static void main (String[] args) {
        YahtzeeScore test = new YahtzeeScore();
        int[] check = { 2, 2, 3, 5, 4 };
        System.out.println(test.maxPoints(check));
    }
}

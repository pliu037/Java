//1-25 SRM 147 DIV 1 500

import java.util.Arrays;

public class Dragons {

    private long[] numerator;
    private long[] denominator;

    /**
     * Reduces the fraction at position <pos>.
     */
    private String reducedForm(int pos) {
        while (numerator[pos] % 2 == 0 && denominator[pos] % 2 == 0) {
            numerator[pos] /= 2;
            denominator[pos] /= 2;
        }
        String reducedForm = String.valueOf(numerator[pos]);
        if (denominator[pos] != 1) {
            reducedForm += "/" + denominator[pos];
        }
        return reducedForm;
    }

    /**
     * Simulates each round of the dragons stealing from each other. Each dragon steals 1/4 of the
     * food in each adjacent square each round. Indices 0, 1, 2, 3, 4, 5, and 6 represent the front,
     * back, top, bottom, left, and right of a cube, respectively.
     */
    private void simulateRound() {
        long[] newNumerator = new long[numerator.length];

        /*
        Assuming all of the denominators are the same, taking 1/4 of the each adjacent square is
        equivalent to summing their numerators, dividing the sum by 4, and setting the sum as the
        new numerator.
         */
        newNumerator[0] = numerator[2] + numerator[3] + numerator[4] + numerator[5];
        newNumerator[1] = numerator[2] + numerator[3] + numerator[4] + numerator[5];
        newNumerator[2] = numerator[0] + numerator[1] + numerator[4] + numerator[5];
        newNumerator[3] = numerator[0] + numerator[1] + numerator[4] + numerator[5];
        newNumerator[4] = numerator[0] + numerator[1] + numerator[2] + numerator[3];
        newNumerator[5] = numerator[0] + numerator[1] + numerator[2] + numerator[3];

        /*
        To maintain integer-value numerators and uniform denominators, checks whether all of the new
        numerators are divisible by 4. If so, divides each by 4, otherwise multiplies all denominators
        by 4. This step further serves to minimize the magnitude of the denominators relative to
        just multiplying the denominators by 4 every round.
         */
        boolean allDivisibleBy4 = true;
        for (int i = 0; i < newNumerator.length; i ++) {
            if (newNumerator[i] % 4 != 0) {
                allDivisibleBy4 = false;
                for (int j = 0; j < denominator.length; j ++) {
                    denominator[j] *= 4;
                }
                break;
            }
        }
        if (allDivisibleBy4) {
            for (int i = 0; i < newNumerator.length; i ++) {
                newNumerator[i] /= 4;
            }
        }

        numerator = newNumerator;
    }

    /**
     * Returns the amount of food Snaug (index 2) has after <rounds> rounds as a single integer, if
     * it is, or as a reduced fraction, x/y, otherwise.
     */
    public String snaug(int[] initialFood, int rounds) {
        numerator = new long[initialFood.length];
        for (int i = 0; i < initialFood.length; i ++) {
            numerator[i] = initialFood[i];
        }
        denominator = new long[initialFood.length];
        Arrays.fill(denominator, 1);

        for (int i = 0; i < rounds; i ++) {
            simulateRound();
        }

        return reducedForm(2);
    }

    public static void main (String[] args) {
        Dragons test = new Dragons();
        int[] initialFood = {1000, 0, 1000, 1000, 1000, 1000};
        System.out.println(test.snaug(initialFood, 45));
    }
}

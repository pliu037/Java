//1-25 SRM 147 DIV 2 600

public class PeopleCircle {

    /**
     * Given <numMales> males and <numFemales> females arranged in a circle, determines the order of
     * males and females such that removing every k-th person, accounting for wrap-around and
     * previously removed individuals, results in all females being removed after <numFemales> steps.
     */
    public String order (int numMales, int numFemales, int k) {
        int total = numMales + numFemales;
        char[] order = new char[total];
        int currentPos = 0;

        /*
        Every round, a female is removed, leaving <numMales + numFemales - numRounds> open positions.
        Thus, k - 1 (1 signifies the current position) mod (numMales + numFemales - numRounds) steps
        are taken from the current position, with wrap-around. Skipping assigned positions (the only
        assigned positions are females that were removed in previous rounds), this is the position
        of the next female to be removed.
         */
        for (int numRounds = 0; numRounds < numFemales; numRounds ++) {
            int remainder = (k - 1) % (total - numRounds);
            while (remainder > 0) {
                currentPos++;
                currentPos %= total;
                if (order[currentPos] != 'F') {
                    remainder--;
                }
            }
            order[currentPos] = 'F';

            /*
            After each removal, the current position is moved to the next unassigned position.
             */
            do {
                currentPos++;
                currentPos %= total;
            } while (order[currentPos] == 'F');
        }

        for (int i = 0; i < total; i ++) {
            if (order[i] != 'F') {
                order[i] = 'M';
            }
        }

        return new String(order);
    }

    public static void main (String[] args) {
        PeopleCircle test = new PeopleCircle();
        System.out.println(test.order(25, 25, 1000));
    }

}

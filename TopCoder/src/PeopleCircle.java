//1-25 SRM 147 DIV 2 600

public class PeopleCircle {

    /**
     * Given numMales number of males and numFemales number of females arranged in a circle,
     * removing every k-th person results in only removing females. Determines the original order of
     * males and females such that the above condition is satisfied.
     */
    public String order (int numMales, int numFemales, int k) {
        int total = numMales + numFemales;
        char[] order = new char[total];
        int currentPos = 0;

        /*

         */
        for (int i = 0; i < numFemales; i ++) {
            int remainder = (k - 1) % (total - i);
            while (remainder > 0) {
                currentPos++;
                currentPos %= total;
                if (order[currentPos] != 'F') {
                    remainder--;
                }
            }
            order[currentPos] = 'F';

            /*

             */
            currentPos++;
            currentPos %= total;
            while (order[currentPos] == 'F') {
                currentPos++;
                currentPos %= total;
            }
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
        System.out.println(test.order(25, 25, 2));
    }

}

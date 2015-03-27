//1-25 SRM 148 DIV 2 250

public class DivisorDigits {

    /**
     * Returns the number of digits in <number> that divide evenly into <number> itself.
     */
    public int howMany(int number) {
        int count = 0;
        String num = String.valueOf(number);
        for (int i = 0; i < num.length(); i ++) {
            int check =  Character.getNumericValue(num.charAt(i));
            if (check != 0 && number % check == 0) {
                count ++;
            }
        }
        return count;
    }

    public static void main (String[] args) {
        DivisorDigits test = new DivisorDigits();
        System.out.println(test.howMany(12345));
    }
}

package ieee_coding_challenge;

import ieee_coding_challenge.q1.Fibonacci;

public class Q1Tests extends AbstractTests<Fibonacci> {

    private Q1Tests(Class aClass, String path, String packagePath) {
        super(aClass, path, packagePath);
    }

    @Test
    public static void testBase(Fibonacci f) {
        long check = f.getNthFibonacci(0);
        if (check != 1) {
            throw new Error("Got " + check + " expecting 1");
        }
    }

    @Test
    public static void testCorrect(Fibonacci f) {
        long check = f.getNthFibonacci(4);
        if (check != 5) {
            throw new Error("Got " + check + " expecting 5");
        }
    }

    @Test
    @Timed
    public static void testEfficiencySingle(Fibonacci f) {
        long check = f.getNthFibonacci(50);
    }

    public static void main(String[] args) {
        Q1Tests q1 = new Q1Tests(Fibonacci.class,
                "C:\\Users\\pengl\\Documents\\Programming\\Java\\Miscellaneous\\src\\ieee_coding_challenge\\q1",
                "ieee_coding_challenge.q1");
        q1.runTestsOnClasses();
    }
}

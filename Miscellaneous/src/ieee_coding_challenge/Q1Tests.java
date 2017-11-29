package ieee_coding_challenge;

import ieee_coding_challenge.q1.Fibonacci;

public class Q1Tests {

    @Test
    static void test(Fibonacci f) {

    }

    public static void main(String[] args) {
        TestRunner<Fibonacci> t = new TestRunner<>(Fibonacci.class, "", "");
        t.runTest(Q1Tests.class);
    }
}

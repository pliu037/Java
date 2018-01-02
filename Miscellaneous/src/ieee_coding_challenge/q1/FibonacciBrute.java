package ieee_coding_challenge.q1;

public class FibonacciBrute extends Fibonacci {
    @Override
    public long getNthFibonacci(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return getNthFibonacci(n - 1) + getNthFibonacci(n - 2);
    }
}

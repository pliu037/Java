package ieee_coding_challenge.q1;

import java.util.ArrayList;

public class FibonacciFast extends Fibonacci {

    private final ArrayList<Integer> results;

    public FibonacciFast() {
        results = new ArrayList<>();
        results.add(1);
        results.add(1);
    }

    @Override
    public long getNthFibonacci(int n) {
        if (n >= results.size()) {
            for (int i = results.size() - 1; i <= n; i ++) {
                results.add(results.get(i) + results.get(i - 1));
            }
        }

        return results.get(n);
    }
}

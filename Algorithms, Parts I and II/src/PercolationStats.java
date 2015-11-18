import java.lang.IllegalArgumentException;
import java.util.Random;
import java.lang.Math;

public class PercolationStats {
	
	private double[] steps;
	private int T;

	public PercolationStats(int N, int T) {  // perform T independent computational experiments on an N-by-N grid
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException ();
		}
		this.T = T;
		Percolation test;
		Random gen = new Random();
		steps = new double[T];
		for (int i = 0; i < T; i ++) {
			int j = 0;
			test = new Percolation(N);
			while (!test.percolates()) {
				int x = gen.nextInt(N);
				int y = gen.nextInt(N);
				if (!test.isOpen(x, y)) {
					test.open(x, y);
					j ++;
				}
			}
			steps[i] = (double)j/(N*N);
		}
		
	}
	
	public double mean() {                   // sample mean of percolation threshold
		double sum = 0;
		for (int i = 0; i < T; i ++) {
			sum += steps[i];
		}
		return sum/T;
	}
	
	public double stddev() {                 // sample standard deviation of percolation threshold
		double var = 0;
		double mean = mean();
		for (int i = 0; i < T; i ++) {
			var += (steps[i] - mean)*(steps[i] - mean);
		}
		return Math.sqrt(var/(T-1));
	}
	
	public static void main(String[] args) { // test client, described below
		PercolationStats test = new PercolationStats(200,100);
		System.out.println(test.mean());
		System.out.println(test.stddev());
	}

}

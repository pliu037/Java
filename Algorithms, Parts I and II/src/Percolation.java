//import java.lang.IllegalArgumentException;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private boolean[][] grid;
	private WeightedQuickUnionUF linear;
	private int N;

	public Percolation(int N) {            // create N-by-N grid, with all sites blocked
		this.N = N;
		grid = new boolean[N][N];
		linear = new WeightedQuickUnionUF(N*N+2);
	}
	
	public void open(int i, int j) {       // open site (row i, column j) if it is not already
		/*if (i < 0 || j < 0 || i > N-1 || j > N-1) {
			throw new IllegalArgumentException ();
		}*/
		grid[i][j] = true;
		if (i == 0) {
			linear.union(0, j+1);
		}
		if (i == N-1) {
			linear.union(N*N+1, i*N+j+1);
		}
		if (i > 0) {
			if (isOpen(i-1,j)) {
				linear.union(i*N+j+1, (i-1)*N+j+1);
			}
		}
		if (i < N-1) {
			if (isOpen(i+1,j)) {
				linear.union(i*N+j+1, (i+1)*N+j+1);
			}
		}
		if (j%N > 0) {
			if (isOpen(i,j-1)) {
				linear.union(i*N+j+1, i*N+j);
			}
		}
		if (j%N < N-1) {
			if (isOpen(i,j+1)) {
				linear.union(i*N+j+1, i*N+j+2);
			}
		}
	}
	
	public boolean isOpen(int i, int j) {  // is site (row i, column j) open?
		/*if (i < 0 || j < 0 || i > N-1 || j > N-1) {
			throw new IllegalArgumentException ();
		}*/
		return grid[i][j];
	}
	
	public boolean isFull(int i, int j) {  // is site (row i, column j) full?
		/*if (i < 0 || j < 0 || i > N-1 || j > N-1) {
			throw new IllegalArgumentException ();
		}*/
		return linear.connected(0, i*N+j+1);
	}
	
	public boolean percolates() {          // does the system percolate?
		return linear.connected(0, N*N+1);
	}
	
	public static void main(String[] args) {
		
	}

}

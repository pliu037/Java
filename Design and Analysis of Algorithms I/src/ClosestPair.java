import java.util.ArrayList;
import java.util.Random;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class ClosestPair {
	
	public static double[] RandomDoubleArray (int dim, double range) {
		double[] random = new double [dim];
		Random gen = new Random();
		for (int i = 0; i < dim; i ++) {
			random [i] = gen.nextDouble()*range;
		}
		return random;
	}
	
	public static void PrintList (ArrayList <double[]> list) {
		for (int j = 0; j < list.size(); j ++) {
			double[] current = list.get(j);
			for (int i = 0; i < current.length; i ++) {
				System.out.print (current[i] + " ");
			}
			System.out.println ();
		}
	}
	
	public static void Zipper (ArrayList<double[]> list, int start, int end, int sortby) {
		//Merge two virtual sublists by comparing the right-most element of both, assigning the element
		//with the lower value at each step to the final list (from right to left) and repeating
		int length = end - start, midpoint = (start + end)/2;
		ArrayList<double[]> tmp_list = new ArrayList<double[]>();
		for (int left = start, right = midpoint; left < midpoint || right < end;) {
			if (left < midpoint && right < end) {
				if (list.get(left)[sortby] <= list.get(right)[sortby]) {
					tmp_list.add(list.get(left));
					left++;
				}
				else {
					tmp_list.add(list.get(right));
					right++;
				}
			}
			else if (right >= end) {
				for (int i = left; i < midpoint; i ++) {
					tmp_list.add(list.get(i));
				}
				break;
			}
			else /*if (left >= midpoint)*/ {
				for (int i = right; i < end; i ++) {
					tmp_list.add(list.get(i));
				}
				break;
			}
		}
		for (int i = 0; i < length; i ++) {
			list.set(start + i, tmp_list.get(i));
		}
	}
	
	public static void MergeSort (ArrayList<double[]> list, int start, int end, int sortby) {
		//Sort the list in descending order
		if (end - start > 1) {
			//Divide: Create two virtual sublists corresponding to the first and second half of the
			//        original list
			int midpoint = (start + end)/2;
			//Conquer: Sort both virtual sublists
			MergeSort (list, start, midpoint, sortby);
			MergeSort (list, midpoint, end, sortby);
			//Merge: Reassign the original list by zippering the sorted virtual sublists
			Zipper (list, start, end, sortby);
		}
		//Base case: Do nothing
	}
	
	public static double ComputeDistance (double[] a, double[] b) {
		//Compute the Euclidean distance between points a and b
		double distance = 0;
		for (int i = 0; i < a.length; i ++) {
			distance += pow(a[i] - b[i], 2);
		}
		return sqrt(distance);
	}
	
	public static void CheckSpanning (ArrayList<double[]> sortedbyY, double midpoint, ArrayList<double[]> closest) {
		//Construct a list of points (sorted by Y) whose X values are within the current closest distance
		//of the X value at the split
		ArrayList<double[]> admissibleY = new ArrayList <double[]>();
		double min_distance = ComputeDistance (closest.get(0), closest.get(1));
		for (int i = 0; i < sortedbyY.size(); i ++) {
			double[] check = sortedbyY.get(i);
			if (check[0] >= midpoint - min_distance && check[0] <= midpoint + min_distance) {
				admissibleY.add(check);
			}
		}
		//Determine if any pair of points lying within 7 units of each other in the constructed list is
		//closer than the current closest pair; if so, replace the closest pair with the closest of them
		for (int i = 0; i < admissibleY.size() - 1; i ++) {
			for (int j = i + 1; j - i < 7 && j < admissibleY.size(); j ++) {
				double check = ComputeDistance (admissibleY.get(i), admissibleY.get(j));
				if (check < min_distance) {
					min_distance = check;
					closest.set(0, admissibleY.get(i));
					closest.set(1, admissibleY.get(j));
				}
			}
		}
	}
	
	public static ArrayList<double[]> Find2DClosestPair (ArrayList<double[]> sortedbyX, ArrayList<double[]> sortedbyY, int start, int end) {
		//Find the closest pair of points in 2-dimensional space
		ArrayList<double[]> closest = new ArrayList <double[]>();
		int size = end - start;
		if (size > 100) { //How and why is run-time dependent on this threshold?
			//Divide and Conquer: Find the closest pair from both the first and second half of the list
			closest = Find2DClosestPair (sortedbyX, sortedbyY, start, start + size/2);
			ArrayList<double[]> right_closest = Find2DClosestPair (sortedbyX, sortedbyY, start + size/2, end);
			if (ComputeDistance (closest.get(0), closest.get(1)) > ComputeDistance (right_closest.get(0), right_closest.get(1))) {
				closest = right_closest;
			}
			//Merge: Determine if there exists a closer pair spanning the two halves
			CheckSpanning(sortedbyY, sortedbyX.get(start + size/2 - 1)[0], closest);
		}
		else {
			//Base case: Find the closest pair given a specified number of points by brute force
			double min_distance = -1;
			closest.add(null);
			closest.add(null);
			for (int i = start; i < end - 1; i ++) {
				for (int j = i + 1; j < end; j ++) {
					double check = ComputeDistance(sortedbyX.get(i), sortedbyX.get(j));
					if (check < min_distance || min_distance == -1) {
						min_distance = check;
						closest.set(0, sortedbyX.get(i));
						closest.set(1, sortedbyX.get(j));
					}
				}
			}
		}
		return closest;
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		//Generate random data
		int n = 100000;
		double range = n*10;
		ArrayList <double[]> list = new ArrayList <double[]>();
		list.ensureCapacity(n);
		for (int i = 0; i < n; i ++) {
			list.add(RandomDoubleArray(2, range));
		}
		//Procedure:
		ArrayList <double[]> sortedbyY = new ArrayList <double[]>(list);
		MergeSort (list, 0, list.size(), 0);
		MergeSort (sortedbyY, 0, list.size(), 1);
		ArrayList <double[]> closest = Find2DClosestPair(list, sortedbyY, 0, list.size());
		System.out.println(ComputeDistance(closest.get(0), closest.get(1)));
		PrintList(closest);
		
		//Brute-force test
		/*double min = -1;
		for (int i = 0; i < n - 1; i ++) {
			for (int j = i + 1; j < n; j ++) {
				double check = ComputeDistance(list.get(i), list.get(j));
				if (check < min || min == -1) {
					min = check;
				}
			}
		}
		System.out.println(min);*/
		
		System.out.println ("Time taken (ms): " + (System.currentTimeMillis() - start));
	}
}

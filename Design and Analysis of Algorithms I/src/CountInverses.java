import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class CountInverses {

	public static long Zipper (double[] list, int start, int end) {
		//Merge two virtual subarrays by walking pointers down both (from left to right), assigning the
		//lower of the two values being compared at each step to the final array (from left to right) and
		//moving the corresponding pointer one step
		long inversions = 0;
		int length = end - start, midpoint = (start + end)/2;
		double[] tmp_list = new double [length];
		for (int i = 0, left = start, right = midpoint; i < length; i++) {
			if (left < midpoint && right < end) {
				if (list[left] <= list[right]) {
					tmp_list[i] = list[left];
					left++;
				}
				else {
					tmp_list[i] = list[right];
					right++;
					inversions += midpoint - left;
				}
			}
			else if (right >= end) {
				System.arraycopy(list, left, tmp_list, i, length - i);
				break;
			}
			else /*if (left >= midpoint)*/ {
				System.arraycopy(list, right, tmp_list, i, length - i);
				break;
			}
		}
		System.arraycopy(tmp_list, 0, list, start, length);
		return inversions;
	}
	
	public static long CountInversions_1DMergeSort (double[] list, int start, int end) {
		//Sort the array in ascending order
		long inversions = 0;
		if (end - start > 1) {
			//Divide: Create two virtual subarrays corresponding to the first and second half of the
			//        original array
			int midpoint = (start + end)/2;
			//Conquer: Sort both virtual subarrays
			inversions += CountInversions_1DMergeSort (list, start, midpoint);
			inversions += CountInversions_1DMergeSort (list, midpoint, end);
			//Merge: Reassign the original array by zippering the sorted virtual subarrays
			inversions += Zipper (list, start, end);
		}
		//Base case: Do nothing
		return inversions;
	}
	
	public static void main(String[] args) {
		//Find # of lines in the file
		String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms I/IntegerArray.txt";
		int lines = 0;
		try {
			BufferedReader input = new BufferedReader (new FileReader (path));
			while (input.readLine()!=null) {
				lines++;
			}
			input.close ();
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
		//Get data from the file
		double [] list = new double [lines];
		String line;
		int i = 0;
		try {
			BufferedReader input = new BufferedReader (new FileReader (path));
			while ((line=input.readLine())!=null) {
				list [i] = Double.parseDouble(line);
				i++;
			}
			input.close ();
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
		//Procedure
		System.out.println(CountInversions_1DMergeSort (list, 0, list.length));
	}
}

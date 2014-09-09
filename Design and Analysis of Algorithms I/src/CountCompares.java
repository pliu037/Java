import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

public class CountCompares {
	
	public static int ChoosePivot (double[] list, int start, int end) {
		//Use the first element as the pivot
		//return start;
		
		//Use the last element as the pivot
		//return end - 1;
		
		//Use the median of first, last, and middle elements as the pivot
		double[][] temp = new double [3][];
		double[] temp_s = {list[start],start};
		temp[0] = temp_s;
		double[] temp_e = {list[end-1],end-1};
		temp[1] = temp_e;
		double[] temp_m = {list[(end - 1 + start)/2],(end - 1 + start)/2};
		temp[2] = temp_m;
		for (int i = 0; i < 2; i ++) {
			for (int j = 2; j > i; j --) {
				if (temp[j][0] < temp[j-1][0]) {
					double[] temp2 = temp[j];
					temp[j] = temp[j-1];
					temp[j-1] = temp2;
				}
			}
		}
		return (int)temp[1][1];
		
		//Use a random element as the pivot
		//Random gen = new Random();
		//return gen.nextInt(end-start) + start;
	}
	
	public static void Swap (double[] list, int a, int b) {
		//Swap the values in positions a and b
		double temp = list[b];
		list[b] = list[a];
		list[a] = temp;
	}
	
	public static int Partition (double[] list, int start, int end, int pivot) {
		//Partition the array around the pivot; smaller values to the left, larger values to the right
		Swap (list, start, pivot);
		int left = start + 1;
		for (int i = left; i < end; i++) {
			if (list[i] <= list [start]) {
				Swap (list, left, i);
				left ++;
			}
		}
		Swap (list, start, left - 1);
		return left - 1;
	}
	
	public static long CountComparisons_1DQuickSort (double[] list, int start, int end) {
		//Sort the array (in place, eliminating the need for a merge step) in ascending order
		long count = 0;
		if (end - start > 1) {
			count += end - start - 1;
			//Divide: Find the correct (sorted) position of the pivot
			int split = Partition (list, start, end, ChoosePivot (list, start, end));
			//Conquer: Sort on both sides of the pivot
			count += CountComparisons_1DQuickSort (list, start, split); // Sort left side
			count += CountComparisons_1DQuickSort (list, split + 1, end); // Sort right side
		}
		//Base case: Do nothing
		return count;
	}

	public static void main(String[] args) {
		//Find # of lines in the file
		String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms I/QuickSort.txt";
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
		System.out.println(CountComparisons_1DQuickSort (list, 0, list.length));
	}
}

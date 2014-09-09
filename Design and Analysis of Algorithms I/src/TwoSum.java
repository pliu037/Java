import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.ArrayList;

public class TwoSum {
	
	public static ArrayList<ArrayList<double[]>> SumSolutions (Hashtable<Double, Boolean> list, double[] targets) {
		//Determine, for each target value, the pairs of values whose sum is the target
		ArrayList<ArrayList<double[]>> solutions = new ArrayList<ArrayList<double[]>>();
		for (int i = 0; i < targets.length; i ++) {
			ArrayList<double[]> temp = new ArrayList<double[]>();
			solutions.add(temp);
		}
		//For each value in the list, find its compliments to each target value and determine if they
		//exist in the list
		Enumeration<Double> e = list.keys();
		while (e.hasMoreElements()) {
			double check = e.nextElement();
			for (int i = 0; i < targets.length; i ++) {
				if (list.containsKey (targets[i] - check)) {
					double[] temp2 = new double[2];
					temp2[0] = check;
					temp2[1] = targets[i] - check;
					solutions.get(i).add(temp2);
				}
			}
		}
		return solutions;
	}

	public static void main(String[] args) {
		//Get data from the file
		String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms I/HashInt.txt";
		double[] targets = {231552,234756,596873,648219,726312,981237,988331,1277361,1283379};
		String line;
		Hashtable<Double, Boolean> list = new Hashtable<>();
		try {
			BufferedReader input = new BufferedReader (new FileReader (path));
			while ((line=input.readLine())!=null) {
				list.put(Double.parseDouble(line),true);
			}
			input.close ();
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
		//Procedure
		ArrayList<ArrayList<double[]>> solutions = SumSolutions (list, targets);
		for (int i = 0; i < solutions.size(); i ++) {
			if (solutions.get(i).size() > 0) {
				System.out.print(1);
			}
			else {
				System.out.print(0);
			}
		}
	}
}

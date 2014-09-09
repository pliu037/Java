import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Random;
import java.lang.Math;

public class TwoSAT {
	
	static class Condition {
		
		public int[] cond;
		
		public Condition (int[] cond) {
			this.cond = cond;
		}
	}
	
	public static boolean checkPosition (boolean[] values, int pos) {
		if (pos > 0) {
			return values[pos];
		}
		return !values[-pos];
	}
	
	public static boolean checkCondition (boolean[] values, Condition cond) {
		if (!checkPosition(values, cond.cond[0]) && !checkPosition(values, cond.cond[1])) {
			Random gen = new Random();
			int flip = gen.nextInt(2);
			values[Math.abs(cond.cond[flip])] = !values[Math.abs(cond.cond[flip])];
			return false;
		}
		return true;
	}

	public static boolean checkSatisfiabilityIter (int num_bool, Condition[] conditions) {
		Random gen = new Random();
		boolean[] values = new boolean[num_bool+1];
		for (int i = 1; i < num_bool+1; i ++) {
			values[i] = gen.nextBoolean();
		}
		
		long count = 0, i = 0, spree = 0, max = 2*(long)num_bool*(long)num_bool;
		while (count < max) {
			if (!checkCondition(values, conditions[(int)i%num_bool])) {
				count ++;
				spree = 0;
			}
			i = i+1 == num_bool ? 0 : i+1;
			spree ++;
			if (spree > num_bool) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkSatisfiability (int num_bool, Condition[] conditions) {
		for (int i = 0, max = (int)(Math.log(num_bool)/Math.log(2))+1; i < max; i ++) {
			if (checkSatisfiabilityIter(num_bool, conditions)) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		try {
			String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms II/SAT2.txt";
			//Get data from the file
			BufferedReader input = new BufferedReader (new FileReader (path));
			String line = input.readLine();
			StringTokenizer st = new StringTokenizer (line, " \t");
			int num = Integer.parseInt(st.nextToken());
			Condition[] conditions = new Condition [num];
			int count = 0;
			while ((line=input.readLine()) != null) {
				st = new StringTokenizer (line, " \t");
				int[] cond = new int[2];
				cond[0] = Integer.parseInt(st.nextToken());
				cond[1] = Integer.parseInt(st.nextToken());
				conditions[count] = new Condition (cond);
				count ++;
			}
			input.close ();
			System.out.println(checkSatisfiability(num, conditions));
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
	}
}

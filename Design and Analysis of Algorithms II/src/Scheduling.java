import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Scheduling {

	static class Job implements Comparable<Job> {
		
		public double weight;
		public double length;
		
		public Job (double weight, double length) {
			this.weight = weight;
			this.length = length;
		}
		
		public double greedyCriterion () {
			return weight / length;
		}
		
		public int compareTo (Job job) {
			//Inverted design (will sort in descending rather than ascending order)
			double temp = job.greedyCriterion() - this.greedyCriterion();
			double temp2 = job.weight - this.weight;
			if (temp < 0) {
				return -1;
			}
			else if (temp > 0) {
				return 1;
			}
			else if (temp2 < 0) {
				return -1;
			}
			else if (temp2 > 0) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}
	
	public static double scheduling (Job[] jobs) {
		int size = jobs.length;
		double total = 0;
		double total_length = 0;
		Arrays.sort(jobs);
		for (int i = 0; i < size; i ++) {
			total_length += jobs[i].length;
			total += jobs[i].weight*total_length;
		}
		return total;
	}
	
	public static void main(String[] args) {
		try {
			String path = "D:/Work/Programming/Java/Design and Analysis of Algorithms II/jobs.txt";
			String line;
			Job[] jobs;
			int n = 0;
			//Get data from the file
			BufferedReader input = new BufferedReader (new FileReader (path));
			line=input.readLine();
			jobs = new Job [Integer.parseInt(line)];
			while ((line=input.readLine())!=null) {
				StringTokenizer st = new StringTokenizer (line, " \t");
				jobs[n] = new Job (Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
				n ++;
			}
			input.close ();
			//Procedure
			System.out.println((long) scheduling(jobs));
		}
		catch (IOException e) {
			System.out.println ("IOException!!!");
		}
	}

}

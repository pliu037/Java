public class RandomizedSelect {
	
	public static double FindSelection (double[] list, int order, int start, int end) {
		//Find the value of the desired order statistic
		double value = 0;
		//Divide: Find the correct (sorted) position of the pivot
		int split = CountCompares.Partition(list, start, end, CountCompares.ChoosePivot(list, start, end));
		//Conquer: Partition on the side of the pivot that contains the desired order statistic
		if (split < order) {
			value = FindSelection(list,order,split+1,end);
		}
		else if (split > order) {
			value = FindSelection(list,order,start,split);
		}
		//Base case: Return the value of the pivot when its position is the desired order
		else {
			value = list[split];
		}
		return value;
	}

	public static void main(String[] args) {
		//Generate random data
		int n = 10000000;
		double range = n*10;
		double[] list = ClosestPair.RandomDoubleArray(n, range);
		//Procedure
		int order = 0;
		System.out.println(FindSelection(list, order, 0, list.length));
	}
}

//1-25 SRM 145 DIV 1 250

import java.util.ArrayList;

public class Bonuses {

    /**
     * Determines the percentage of bonuses earned by each worker based on the points that worker
     * has accumulated. Each worker receives a percentage equal to the truncated percentage of the
     * points they've earned. If there are x extra %, each of top x workers (by point amount) is
     * given an extra %, with initial order breaking any ties in point amount.
     */
    public int[] getDivision(int[] points) {
        int sum = 0;
        for (int i = 0; i < points.length; i ++) {
            sum += points[i];
        }

        int[] bonusPercentages = new int[points.length];
        int remainingPercentage = 100;
        for (int i = 0; i < points.length; i ++) {
            bonusPercentages[i] = 100*points[i]/sum;
            remainingPercentage -= bonusPercentages[i];
        }

        if (remainingPercentage == 0) {
            return bonusPercentages;
        }

        /*
        Sorts the workers in descending order by points earned. The outer ArrayList is descending
        based on points while the inner ArrayList contains workers who have the same number of
        points but is ascending based on index. Every iteration through the points array, an
        ArrayList is created to track the indices of workers that have the current maximum number of
        points. If a worker with more points is discovered, the ArrayList is reset, tracking the
        indices of workers with the new current maximum number of points. At the end of every
        iteration, the new ArrayList is added to the outer ArrayList and the ceiling is updated
        downward to the maximum number of points seen in that iteration. During any iteration, only
        values less than this ceiling are considered, which prevents workers already assigned into
        an ArrayList bucket from being rediscovered in subsequent iterations.
        The above method is inefficient (O(n^2) time complexity). A better implementation would be
        to create a Worker class that is stable-sortable by points and by original index. An array
        of Worker objects could be sorted by points to distribute extra % and then sorted by original
        index to return the original order. This method has time complexity O(n*log n).
         */
        ArrayList<ArrayList<Integer>> descendingIndices = new ArrayList<>();
        int upper = Integer.MAX_VALUE;
        while (true) {
            int currentUpper = -1;
            ArrayList<Integer> currentMaxIndices = new ArrayList<>();
            for (int i = 0; i < points.length; i ++) {
                if (points[i] > currentUpper && points[i] < upper) {
                    currentMaxIndices = new ArrayList<>();
                    currentMaxIndices.add(i);
                    currentUpper = points[i];
                }
                else if (points[i] == currentUpper) {
                    currentMaxIndices.add(i);
                }
            }
            if (currentUpper == -1) {
                break;
            }
            descendingIndices.add(currentMaxIndices);
            upper = currentUpper;
        }

        /*
        Distributes extra % to the top workers as described above using descendingIndices.
         */
        for (int i = 0; i < descendingIndices.size(); i ++) {
            for (int j = 0; j < descendingIndices.get(i).size(); j ++) {
                bonusPercentages[descendingIndices.get(i).get(j)] += 1;
                remainingPercentage -= 1;
                if (remainingPercentage <= 0) {
                    return bonusPercentages;
                }
            }
        }

        return null;
    }

    public static void main (String[] args) {
        Bonuses test = new Bonuses();
        int[] check = {1,2,3,4,5};
        int[] answer = test.getDivision(check);
        for (int i = 0; i < answer.length; i ++) {
            System.out.print(answer[i] + " ");
        }
    }
}

//1-25 SRM 145 DIV 2 500

public class ExerciseMachine {

    /**
     * Given a duration in hours:minutes:seconds, returns the number of times that an integer
     * percentage is attained if percentages are checked every second.
     */
    public int getPercentages(String time) {
        String[] parsed = time.split(":");
        int seconds = Integer.parseInt(parsed[0])*3600;
        seconds += Integer.parseInt(parsed[1])*60;
        seconds += Integer.parseInt(parsed[2]);
        int count = 0;
        for (int i = 1; i < 100; i ++) {
            if (i*seconds % 100 == 0) {
                count ++;
            }
        }
        return count;
    }

    public static void main (String[] args) {
        ExerciseMachine test = new ExerciseMachine();
        System.out.println(test.getPercentages("00:14:10"));
    }
}

//1-25 SRM 144 DIV 2 200

public class Time {

    /**
     * Given the time in seconds, returns the time in hours:minutes:seconds.
     */
    public String whatTime(int seconds) {
        int minutes = seconds/60;
        int hours = (minutes/60);
        minutes = minutes % 60;
        return hours + ":" + minutes + ":" + seconds % 60;
    }

    public static void main (String[] args) {
        Time test = new Time();
        System.out.println(test.whatTime(3661));
    }
}

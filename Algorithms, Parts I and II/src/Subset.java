import edu.princeton.cs.algs4.StdIn;

/**
 * Created by pengl on 2015-11-15.
 */
public class Subset {

    public static void main(String[] args) {
        int num = Integer.valueOf(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        String input = StdIn.readLine();
        String[] tokens = input.split(" ");
        for (String token : tokens) {
            rq.enqueue(token);
        }
        for (int i = 0; i < num; i ++) {
            System.out.println(rq.dequeue());
        }
    }
}

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 * Created by pengl on 12/24/2015.
 */
public class Solver {

    private MinPQ<BoardNode> minHeap;
    private MinPQ<BoardNode> minHeapTwin;
    private boolean solveable;
    private BoardNode solution;

    public Solver(BoardNode initial, Comparator<BoardNode> cmp) {
        minHeap = new MinPQ<>(cmp);
        minHeapTwin = new MinPQ<>(cmp);
        minHeap.insert(initial);
        minHeapTwin.insert(new BoardNode(initial.getBoard().twin(), null, 0));
        solve();
    }

    private void solve() {
        BoardNode check = minHeap.delMin();
        BoardNode checkTwin = minHeapTwin.delMin();
        while (true) {
            if (check.getBoard().isGoal()) {
                solveable = true;
                solution = check;
                return;
            }
            else if (checkTwin.getBoard().isGoal()) {
                solveable = false;
                solution = checkTwin;
                return;
            }
            move(check, minHeap);
            move(checkTwin, minHeapTwin);
            check = minHeap.delMin();
            checkTwin = minHeapTwin.delMin();
        }
    }

    private void move(BoardNode current, MinPQ<BoardNode> heap) {
        Iterable<Board> nextBoards = current.getBoard().neighbours();
        for (Board next : nextBoards) {
            if (current.getPrev() == null || !current.getPrev().getBoard().equals(next)) {
                heap.insert(new BoardNode (next, current, current.moves() + 1));
            }
        }
    }

    public boolean isSolvable() {
        return solveable;
    }

    public int moves() {
        return solution.moves();
    }

    public Iterable<Board> solution() {
        Deque<Board> moves = new Deque<>();
        BoardNode current = solution;
        do {
            moves.addLast(current.getBoard());
            current = current.getPrev();
        }
        while (current.getPrev() != null);
        return moves;
    }

    public static void main (String[] args) {
        //Board testB = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        Board testB = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}});
        BoardNode testBN = new BoardNode(testB, null, 0);
        Solver test = new Solver(testBN, new BoardNode.ManhattanCmp());
        System.out.println(test.isSolvable() + " " + test.moves());
        Iterable<Board> solution = test.solution();
        for (Board move : solution) {
            System.out.println(move + " " + move.manhattan());
        }
    }

}

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.HashSet;

public class Solver {

    private MinPQ<BoardNode> minHeap;
    private MinPQ<BoardNode> minHeapTwin;
    private boolean solveable;
    private BoardNode solution;

    public Solver(BoardNode initial, BoardNode.Cmp cmp) {
        minHeap = new MinPQ<>(cmp);
        minHeapTwin = new MinPQ<>(cmp);
        minHeap.insert(initial);
        minHeapTwin.insert(new BoardNode(initial.getBoard().twin(), null, 0));
        if (cmp.shortest) {
            solveShortest();
        }
        else {
            solve();
        }
    }

    private void solve() {
        BoardNode check = minHeap.delMin();
        BoardNode checkTwin = minHeapTwin.delMin();
        HashSet<Board> visited = new HashSet<>();
        HashSet<Board> visitedTwin = new HashSet<>();
        int moves = 0;

        while (true) {
            if (check.getBoard().isGoal()) {
                solveable = true;
                solution = check;
                System.out.println("Moves: " + moves);
                return;
            }
            else if (checkTwin.getBoard().isGoal()) {
                solveable = false;
                solution = checkTwin;
                System.out.println("Moves: " + moves);
                return;
            }
            visited.add(check.getBoard());
            visitedTwin.add(checkTwin.getBoard());
            move(check, visited, minHeap);
            move(checkTwin, visitedTwin, minHeapTwin);
            check = minHeap.delMin();
            checkTwin = minHeapTwin.delMin();
            moves ++;
        }
    }

    private void solveShortest() {
        BoardNode check = minHeap.delMin();
        BoardNode checkTwin = minHeapTwin.delMin();
        int moves = 0;

        while (true) {
            if (check.getBoard().isGoal()) {
                solveable = true;
                solution = check;
                System.out.println("Shortest moves: " + moves);
                return;
            }
            else if (checkTwin.getBoard().isGoal()) {
                solveable = false;
                solution = checkTwin;
                System.out.println("Shortest moves: " + moves);
                return;
            }
            moveShortest(check, minHeap);
            moveShortest(checkTwin, minHeapTwin);
            check = minHeap.delMin();
            checkTwin = minHeapTwin.delMin();
            moves ++;
        }
    }

    private void move(BoardNode current, HashSet<Board> visited, MinPQ<BoardNode> heap) {
        Iterable<Board> nextBoards = current.getBoard().neighbours();
        for (Board next : nextBoards) {
            if (!visited.contains(next)) {
                heap.insert(new BoardNode (next, current, current.moves() + 1));
            }
        }
    }

    private void moveShortest(BoardNode current, MinPQ<BoardNode> heap) {
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
        while (true) {
            moves.addLast(current.getBoard());
            if (current.getPrev() != null) {
                current = current.getPrev();
            }
            else {
                break;
            }
        }
        return moves;
    }

    public static void main (String[] args) {
        //Board testB = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        Board testB = new Board(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 15, 14, 0}});
        BoardNode testBN = new BoardNode(testB, null, 0);
        Solver test = new Solver(testBN, new BoardNode.ManhattanCmp());
        System.out.println("Solveable: " + test.isSolvable() + " in " + test.moves() + " moves");
        Iterable<Board> solution = test.solution();
        for (Board move : solution) {
            System.out.println(move + "\t" + move.manhattan() + "\t" + move.hamming());
        }
    }

}

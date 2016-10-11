import java.util.Comparator;

public class BoardNode {

    public static abstract class Cmp implements Comparator<BoardNode> {

        public final boolean shortest;

        public Cmp(boolean shortest) {
            this.shortest = shortest;
        }

    }

    public static class HammingShortestCmp extends Cmp {

        public HammingShortestCmp() {
            super(true);
        }

        public int compare(BoardNode b1, BoardNode b2) {
            return b1.board.hamming() + b1.moves - b2.board.hamming() - b2.moves;
        }
    }

    public static class ManhattanShortestCmp extends Cmp {

        public ManhattanShortestCmp() {
            super(true);
        }

        public int compare(BoardNode b1, BoardNode b2) {
            return b1.board.manhattan() + b1.moves - b2.board.manhattan() - b2.moves;
        }
    }

    public static class HammingCmp extends Cmp {

        public HammingCmp() {
            super(false);
        }

        public int compare(BoardNode b1, BoardNode b2) {
            return b1.board.hamming() - b2.board.hamming();
        }
    }

    public static class ManhattanCmp extends Cmp {

        public ManhattanCmp() {
            super(false);
        }

        public int compare(BoardNode b1, BoardNode b2) {
            return b1.board.manhattan() - b2.board.manhattan();
        }
    }

    private Board board;
    private BoardNode prev;
    private int moves;

    public BoardNode (Board board, BoardNode prev, int moves) {
        this.board = board;
        this.prev = prev;
        this.moves = moves;
    }

    public Board getBoard() {
        return board;
    }

    public BoardNode getPrev() {
        return prev;
    }

    public int moves() {
        return moves;
    }
}

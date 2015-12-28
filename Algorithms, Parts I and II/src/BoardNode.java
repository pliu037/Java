import java.util.Comparator;

/**
 * Created by pengl on 12/28/2015.
 */
public class BoardNode {

    public static class HammingCmp implements Comparator<BoardNode> {
        public int compare(BoardNode b1, BoardNode b2) {
            return b1.board.hamming() + b1.moves - b2.board.hamming() - b2.moves;
        }
    }

    public static class ManhattanCmp implements Comparator<BoardNode> {
        public int compare(BoardNode b1, BoardNode b2) {
            return b1.board.manhattan() + b1.moves - b2.board.manhattan() - b2.moves;
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

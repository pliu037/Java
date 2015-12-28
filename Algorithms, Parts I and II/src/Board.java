import java.util.ArrayList;

/**
 * Created by pengl on 12/24/2015.
 */
public class Board {

    private int[] blocks;
    private int dimensions;
    private int posEmpty;

    private Board(Board other) {
        dimensions = other.dimensions;
        blocks = new int[other.blocks.length];
        for (int pos = 0; pos < blocks.length; pos ++) {
            blocks[pos] = other.blocks[pos];
        }
        posEmpty = other.posEmpty;
    }

    public Board(int[][] blocks) {
        dimensions = blocks.length;
        this.blocks = new int[dimensions*dimensions];
        for (int y = 0; y < dimensions; y ++) {
            for (int x = 0; x < dimensions; x ++) {
                this.blocks[dimensions*y + x] = blocks[y][x];
                if (blocks[y][x] == 0) {
                    posEmpty = dimensions*y + x;
                }
            }
        }
    }

    public int dimension() {
        return dimensions;
    }

    public int hamming() {
        int hamming = 0;
        for (int pos = 0; pos < blocks.length; pos ++) {
            if (blocks[pos] != 0 && blocks[pos] != pos + 1) {
                hamming ++;
            }
        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int pos = 0; pos < blocks.length; pos ++) {
            if (blocks[pos] != 0) {
                int expectedY = (blocks[pos] - 1) / dimensions;
                int expectedX = (blocks[pos] - 1) % dimensions + 1;
                int currentY = pos / dimensions;
                int currentX = pos % dimensions + 1;
                manhattan += Math.abs(expectedX - currentX);
                manhattan += Math.abs(expectedY - currentY);
            }
        }
        return manhattan;
    }

    public boolean isGoal() {
        if (hamming() == 0) {
            return true;
        }
        return false;
    }

    private void swap(int pos1, int pos2) {
        int temp = blocks[pos1];
        blocks[pos1] = blocks[pos2];
        blocks[pos2] = temp;
        if (blocks[pos1] == 0) {
            posEmpty = pos1;
        }
        else if (blocks[pos2] == 0) {
            posEmpty = pos2;
        }
    }

    public Board twin() {
        Board other = new Board(this);
        if (blocks[0] == 0 || blocks[1] == 0) {
            other.swap(2, 3);
        }
        else {
            other.swap(0, 1);
        }
        return other;
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y == this) {
            return true;
        }
        if (!(y instanceof Board)) {
            return false;
        }
        Board other = (Board) y;
        if (dimensions != other.dimensions) {
            return false;
        }
        for (int pos = 0; pos < blocks.length; pos ++) {
            if (blocks[pos] != other.blocks[pos]) {
                return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbours() {
        ArrayList<Board> neighbours = new ArrayList<>();
        if (posEmpty - dimensions >= 0) {
            Board temp = new Board(this);
            temp.swap(posEmpty, posEmpty - dimensions);
            neighbours.add(temp);
        }
        if (posEmpty + dimensions < blocks.length) {
            Board temp = new Board(this);
            temp.swap(posEmpty, posEmpty + dimensions);
            neighbours.add(temp);
        }
        if (posEmpty%dimensions != 0) {
            Board temp = new Board(this);
            temp.swap(posEmpty, posEmpty - 1);
            neighbours.add(temp);
        }
        if ((posEmpty + 1)%dimensions != 0) {
            Board temp = new Board(this);
            temp.swap(posEmpty, posEmpty + 1);
            neighbours.add(temp);
        }
        return neighbours;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int pos = 0; pos < blocks.length; pos ++) {
            sb.append(blocks[pos]);
        }
        return sb.toString();
    }

    public static void main (String[] args) {
        Board test = new Board(new int[][]{{3, 2, 0},{4, 5, 1},{7, 8, 6}});
        Board test2 = new Board(new int[][]{{3, 2, 0},{4, 5, 1},{7, 8, 6}});
        Board test3 = new Board(new int[][]{{1, 2, 3},{4, 5, 6},{7, 8, 0}});
        System.out.println(test.hamming());
        System.out.println(test.manhattan());
        System.out.println(test.equals(test2));
        System.out.println(test.equals(test3));
        System.out.println(test.isGoal());
        System.out.println(test3.isGoal());
        for (Board b : test.neighbours()) {
            System.out.println(b);
        }
    }

}

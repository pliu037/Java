//1-25 SRM 146 DIV 2 500

public class RectangularGrid {

    /**
     * Returns the number of rectangles (not including squares) contained in a width-by-height
     * grid.
     */
    public long countRectangles(int width, int height) {
        long count = 0;
        for (int x = 1; x <= width; x ++) {
            for (int y = 1; y <= height; y ++) {
                if (x != y) {

                    /*
                    There are width - x + 1 contiguous blocks of length x across width and height -
                    y + 1 contiguous block of length y across height. Each combination of these
                    blocks yields a distinct rectangle.
                     */
                    count += (width - x + 1)*(height - y + 1);
                }
            }
        }
        return count;
    }

    public static void main (String[] args) {
        RectangularGrid test = new RectangularGrid();
        System.out.println(test.countRectangles(592, 964));
    }
}

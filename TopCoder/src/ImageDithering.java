//1-25 SRM 145 DIV 2 250

public class ImageDithering {

    /**
     * Counts the number of characters in screen that are one of the letters in dithering.
     */
    public int count (String dithering, String[] screen) {
        int count = 0;
        for (int i = 0; i < screen.length; i ++) {
            for (int j = 0; j < screen[i].length(); j ++) {
                if (dithering.contains(String.valueOf(screen[i].charAt(j)))) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public static void main (String[] args) {
        ImageDithering test = new ImageDithering();
        String[] check = {"AAAAAAAA",
                "ABWBWBWA",
                "AWBWBWBA",
                "ABWBWBWA",
                "AWBWBWBA",
                "AAAAAAAA"};
        System.out.println(test.count("BW", check));
    }
}

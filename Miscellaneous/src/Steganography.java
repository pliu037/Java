import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class Steganography {

    static boolean isSet(byte b, int position) {
        return (b >> (7 - position) & 1) == 1;
    }

    static void hideMessage(Picture pic, String outPath, String message) {
        int index = 0;
        for (int y = 0; y < pic.height(); y ++) {
            for (int x = 0; x < pic.width(); x ++) {
                if (index / 8 == message.length()) {
                    break;
                }
                Color c = pic.get(x, y);
                int[] colors = new int[]{c.getRed(), c.getGreen(), c.getBlue()};
                for (int i = 0; i < 3; i ++) {
                    if (index / 8 == message.length()) {
                        break;
                    }
                    if (isSet((byte)message.charAt(index/8), index % 8)) {
                        if (colors[i] % 2 == 0) {
                            colors[i] ++;
                        }
                    } else {
                        if (colors[i] % 2 == 1) {
                            colors[i] --;
                        }
                    }
                    index ++;
                }
                c = new Color(colors[0], colors[1], colors[2]);
                pic.set(x, y, c);
            }
        }
        pic.save(outPath);
    }

    static String revealMessage(String inPath, int length) {
        Picture pic = new Picture(inPath);
        StringBuilder sb = new StringBuilder();
        int index = 0;
        byte b = 0;
        for (int y = 0; y < pic.height(); y ++) {
            for (int x = 0; x < pic.width(); x ++) {
                Color c = pic.get(x, y);;
                int[] colors = new int[]{c.getRed(), c.getGreen(), c.getBlue()};
                for (int i = 0; i < 3; i ++) {
                    b = (byte) (b << 1);
                    if (colors[i] % 2 == 1) {
                        b ++;
                    }
                    index ++;
                    if (index % 8 == 0) {
                        sb.append((char) b);
                    }
                    if (index == length) {
                        return sb.toString();
                    }

                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            String message = "I'm going to hide a fairly long message in here, not because I want to, but because I " +
                    "can. I'm curious how much I can actually hide in this thing, but I assume it's a fair amount. " +
                    "Today is October 10th, 2016. Matt told me he likes CTFs so this is for him mainly, I guess. " +
                    "Anybody is welcome to try though. I wonder if I could fit a whole book in here... Anyway, " +
                    "whoever finds this is in for a treat!";
            Picture pic = new Picture(args[0]);
            hideMessage(pic, args[1], message);
            System.out.println(revealMessage(args[1], message.length() * 8));
        } else {
            return;
        }
    }
}

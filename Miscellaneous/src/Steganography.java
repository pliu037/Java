import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class Steganography {

    static boolean isSet(byte b, int position) {
        return (b >> (7 - position) & 1) == 1;
    }

    static String convertToBinary(String message) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        while (true) {
            int charPosition = count / 8;
            if (charPosition == message.length()) {
                break;
            }
            int bitPosition = count % 8;
            if (isSet((byte) message.charAt(charPosition), bitPosition)) {
                sb.append(1);
            } else {
                sb.append(0);
            }
            count++;
        }
        return sb.toString();
    }

    static void hideBinaryMessage(Picture pic, String outPath, String binaryMessage) {
        int index = 0;
        for (int y = 0; y < pic.height(); y ++) {
            for (int x = 0; x < pic.width(); x ++) {
                if (index == binaryMessage.length()) {
                    break;
                }
                Color c = pic.get(x, y);
                int[] colors = new int[]{c.getRed(), c.getGreen(), c.getBlue()};
                for (int i = 0; i < 3; i ++) {
                    if (index == binaryMessage.length()) {
                        break;
                    }
                    if (binaryMessage.charAt(index) == '1') {
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

    static String revealBinaryMessage(String inPath, int length) {
        Picture pic = new Picture(inPath);
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < pic.height(); y ++) {
            for (int x = 0; x < pic.width(); x ++) {
                Color c = pic.get(x, y);;
                int[] colors = new int[]{c.getRed(), c.getGreen(), c.getBlue()};
                for (int i = 0; i < 3; i ++) {
                    if (colors[i] % 2 == 1) {
                        sb.append(1);
                    } else {
                        sb.append(0);
                    }
                    if (sb.length() == length) {
                        return sb.toString();
                    }
                }
            }
        }
        return sb.toString();
    }

    static String convertToString(String binaryMessage) {
        int index = 0;
        StringBuilder sb = new StringBuilder();
        while (index + 8 <= binaryMessage.length()) {
            int b = 0;
            for (int i = 0; i < 8; i ++) {
                b = (byte) (b << 1);
                if (binaryMessage.charAt(index) == '1') {
                    b++;
                }
                index ++;
            }
            sb.append((char)b);
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
            String binaryMessage = convertToBinary(message);
            hideBinaryMessage(pic, args[1], binaryMessage);
            String revealedBinaryMessage = revealBinaryMessage(args[1], binaryMessage.length());
            System.out.println(convertToString(revealedBinaryMessage));
            System.out.println(message.length() * 8 + " " + pic.width() * pic.height() * 3);
        } else {
            return;
        }
    }
}

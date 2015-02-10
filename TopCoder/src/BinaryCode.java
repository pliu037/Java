//1-25 SRM 144 DIV 1 300

public class BinaryCode {

    /**
     * Decodes the encoded bit-string, given an assumption of whether the first bit is 1 or 0. If a
     * decrypted bit-string is invalid, "NONE" is returned.
     */
    private String decode (String encoded, boolean first) {

        //Initialized the decoded char[], setting the first char as either '1' or '0' based on first
        char[] decoded = new char[encoded.length()];
        if (first) {
            decoded[0] = '1';
        }
        else {
            decoded[0] = '0';
        }

        /*
        Iterates through encoded, deducing the (i + 1)-st position of decoded. encoded[i] = decoded[i]
        + decoded[i - 1] + decoded[i + 1] <=> decoded[i + 1] = encoded[i] - decoded[i] - decoded[i - 1].
        If the index falls off the edge of the string, the default value is 0.
         */
        for (int i = 0; i < encoded.length() - 1; i ++) {
            int current = Character.getNumericValue(encoded.charAt(i));
            current -= Character.getNumericValue(decoded[i]);
            if (i - 1 >= 0) {
                current -= Character.getNumericValue(decoded[i - 1]);
            }
            if (current != 0 && current != 1) {
                return "NONE";
            }
            decoded[i + 1] = Character.forDigit(current, 10);
        }

        /*
        Since the above loop only deduces the last position without using it in a subsequent deduction
        (which would lock in its value), it needs to be validated. encoded[last] = decoded[last] +
        decoded[last - 1] if it is valid. If the index falls off the edge of the string, the default
        value is 0.
         */
        int checkLast = Character.getNumericValue(encoded.charAt(encoded.length() - 1));
        int compareTo = Character.getNumericValue(decoded[decoded.length - 1]);
        if (decoded.length - 2 >= 0) {
            compareTo += Character.getNumericValue(decoded[decoded.length - 2]);
        }
        if (checkLast != compareTo) {
            return "NONE";
        }

        return new String(decoded);
    }

    /**
     * Decodes the encoded bit-string, returning both possible decrypted bit-strings (assuming the
     * first position is 0 or 1, respectively).
     */
    public String[] decode (String encoded) {
        String[] decodedPair = new String[2];
        decodedPair[0] = decode(encoded, false);
        decodedPair[1] = decode(encoded, true);

        return decodedPair;
    }

    public static void main (String[] args) {
        BinaryCode bc = new BinaryCode();
        String[] results = bc.decode("123210120");
        for (int i = 0; i < results.length; i ++) {
            System.out.println(results[i]);
        }
    }
}

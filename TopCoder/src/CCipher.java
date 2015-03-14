//1-25 SRM 147 DIV 2 250

public class CCipher {

    /**
     * Given cipherText and a shift value (0 - 25 inclusive, but can support any integer), shifts
     * every character in cipherText down by <shift> positions, wrapping around (A - Z, capitalized).
     */
    public String decode (String cipherText, int shift)
    {
        StringBuilder deciphered = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i ++) {
            int currentLetter = cipherText.charAt(i) - shift;
            while (currentLetter < 'A') {
                currentLetter += 26;
            }
            while (currentLetter > 'Z') {
                currentLetter -= 26;
            }
            deciphered.append((char)currentLetter);
        }
        return deciphered.toString();
    }

    public static void main (String[] args) {
        CCipher test = new CCipher();
        System.out.println(test.decode("LIPPSASVPH", 4));
    }
}

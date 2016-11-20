public class DecodingPassword {

    static int getNext(int current, int[] counts) {
        while(counts[current] <= 0) {
            current ++;
            if (current >= counts.length) {
                return -1;
            }
        }
        return current;
    }

    static int[] decode(int[] keys, int[] candidates) {
        int[] counts = new int[10];
        for (int i : candidates) {
            counts[i] ++;
        }

        int[] password = new int[keys.length];
        for (int i = keys.length - 1; i >= 0; i --) {
            int j = 0;
            while (keys[i] > 0) {
                if (j >= 10) {
                    return new int[0];
                }

                keys[i] -= counts[j];
                j ++;
            }

            if (keys[i] < 0) {
                return new int[0];
            }

            int nextJ = getNext(j, counts);
            if (nextJ == -1) {
                return new int[0];
            }

            counts[nextJ] --;
            password[i] = nextJ;
        }

        return password;
    }

    public static void main(String[] args) {
        int[] keys = new int[]{0, 1, 1, 0};
        int[] candidates = new int[]{2, 1, 1, 5};
        int[] password = decode(keys, candidates);
        for (int i : password) {
            System.out.print(i + " ");
        }
    }
}

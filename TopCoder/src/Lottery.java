//1-25 SRM 144 DIV 1 550

import java.util.Arrays;

public class Lottery {

    public static class Game implements Comparable<Game> {

        private String name;
        private long possibilities;

        /**
         * Constructs the Game object by parsing the initial String into the name and the rules,
         * storing the name and passing the rules to calculatePossibilities.
         */
        public Game (String data) {
            String[] parsed = data.split(":");
            name = parsed[0];
            calculatePossibilities(parsed[1]);
        }

        /**
         * Parses the rules to calculate the number of potential possibilities.
         */
        private void calculatePossibilities(String rules) {
            String[] parsed = rules.trim().split(" ");
            int choices = Integer.parseInt(parsed[0]);
            int blanks = Integer.parseInt(parsed[1]);
            boolean sorted, unique;
            if (parsed[2].equals("T")) {
                sorted = true;
            }
            else {
                sorted = false;
            }
            if (parsed[3].equals("T")) {
                unique = true;
            }
            else {
                unique = false;
            }

            possibilities = 1;

            /*
            If the numbers must be in sorted order and unique, there is one valid entry for every
            combination without replacement (choices choose blanks).
             */
            if (sorted && unique) {
                for (int i = 0; i < blanks; i ++) {
                    possibilities *= choices - i;
                }
                possibilities /= factorial(blanks);
            }

            /*
            If the numbers must be in sorted order but are not required to be unique, there is one
            valid entry for every combination with replacement ((choices + blanks - 1) choose blanks).
             */
            else if (sorted && !unique) {
                for (int i = 0; i < blanks; i ++) {
                    possibilities *= choices + blanks - 1 - i;
                }
                possibilities /= factorial(blanks);
            }

            /*
            If the numbers must be unique but are not required to be sorted, each additional position
            has one less option, starting with <choices> options.
             */
            else if (!sorted && unique) {
                for (int i = 0; i < blanks; i ++) {
                    possibilities *= choices - i;
                }
            }

            /*
            If the numbers can be unsorted and non-unique, each position can have <choices> options.
             */
            else {
                possibilities = (long)Math.pow(choices, blanks);
            }
        }

        /**
         * Returns the factorial of n.
         */
        private long factorial (int n) {
            long current = 1;
            for (int i = 1; i <= n; i ++) {
                current *= i;
            }
            return current;
        }

        /**
         * Returns the name of the Game.
         */
        public String getName() {
            return name;
        }

        /**
         * Implemented as part of the Comparable interface so that Games can be sorted. Since odds
         * are the reciprocal of possibilities
         */
        public int compareTo(Game other) {
            if (this.possibilities < other.possibilities) {
                return -1;
            }
            else if (this.possibilities > other.possibilities) {
                return 1;
            }
            return name.compareTo(other.name);
        }
    }

    /**
     * Takes an array of Strings, each String representing a game, creates an array of Games, sorts
     * the Games from best to worst odds, and returns the names of the sorted Games in a String[].
     */
    public String[] sortByOdds(String[] rules) {
        Game[] games = new Game[rules.length];
        for (int i = 0; i < rules.length; i ++) {
            games[i] = new Game(rules[i]);
        }

        Arrays.sort(games);

        String[] sortedGames = new String[rules.length];
        for (int i = 0; i < rules.length; i ++) {
            sortedGames[i] = games[i].getName();
        }

        return sortedGames;
    }

    public static void main (String[] args) {
        String[] rules = {"INDIGO: 93 8 T F",
                "ORANGE: 29 8 F T",
                "VIOLET: 76 6 F F",
                "BLUE: 100 8 T T",
                "RED: 99 8 T T",
                "GREEN: 78 6 F T",
                "YELLOW: 75 6 F F"};
        Lottery test2 = new Lottery();
        String[] test = test2.sortByOdds(rules);
        for (int i = 0; i < test.length; i ++) {
            System.out.println(test[i]);
        }
    }
}

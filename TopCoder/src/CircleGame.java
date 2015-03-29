//1-25 SRM 148 DIV 1 250

import java.util.ArrayList;

public class CircleGame {

    ArrayList<Integer> deck;

    /**
     * Simulates the following rules until convergence:
     * a) 13s are removed
     * b) Adjacent cards (the deck wraps around) whose sum is 13 are removed
     */
    private void simulate() {
        int lastRemoved = 0;
        int position = 0;

        /*
        If deck.size() rounds have elapsed since the last removal, then every remaining card has
        been checked with no changes to the system: the system has converged.
         */
        while (deck.size() > 0 && lastRemoved != deck.size()) {
            position %= deck.size();
            if (deck.get(position) == 13) {
                deck.remove(position);
                lastRemoved = 0;
            }
            else if (deck.size() > 1 && deck.get(position) + deck.get((position + 1)%deck.size()) == 13) {
                deck.remove(position);
                deck.remove(position%deck.size());
                lastRemoved = 0;
            }
            else {
                position ++;
                lastRemoved ++;
            }
        }
    }

    /**
     * Returns the number of cards remaining in a deck, whose order is given by <deck>, after
     * simulating the game.
     */
    public int cardsLeft (String deck) {
        this.deck = new ArrayList<>();
        for (int i = 0; i < deck.length(); i ++) {
            char check = deck.charAt(i);
            if ("ATJQK".indexOf(check) != -1) {
                switch (check) {
                    case 'A':
                        this.deck.add(1);
                        break;
                    case 'T':
                        this.deck.add(10);
                        break;
                    case 'J':
                        this.deck.add(11);
                        break;
                    case 'Q':
                        this.deck.add(12);
                        break;
                    default:
                        this.deck.add(13);
                        break;
                }
            }
            else {
                this.deck.add(Character.getNumericValue(check));
            }
        }

        simulate();

        return this.deck.size();
    }

    public static void main (String[] args) {
        CircleGame test = new CircleGame();
        System.out.println(test.cardsLeft("KKKKKAQT23"));
    }
}

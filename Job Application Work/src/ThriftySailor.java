import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThriftySailor {

    static int minDrinks(List<Integer> drinks, int min, int max) {
        HashMap<Integer, Integer> minDrinksPerAmt = new HashMap<>();
        minDrinksPerAmt.put(0, 0);
        for (Integer drink : drinks) {
            HashMap<Integer, Integer> newKeys = new HashMap<>();
            for (Integer key : minDrinksPerAmt.keySet()) {
                Integer sum = drink + key;
                if (sum <= max) {
                    Integer currentMin = Math.min(minDrinksPerAmt.containsKey(sum) ? minDrinksPerAmt.get(sum) : Integer.MAX_VALUE, minDrinksPerAmt.get(key) + 1);
                    newKeys.put(sum, newKeys.containsKey(sum) ? Math.min(currentMin, newKeys.get(sum)) : currentMin);
                }
            }

            for (Map.Entry<Integer, Integer> entry : newKeys.entrySet()) {
                minDrinksPerAmt.put(entry.getKey(), entry.getValue());
            }
        }

        int lowest = Integer.MAX_VALUE;
        for (Integer i = min; i <= max; i ++) {
            if (minDrinksPerAmt.containsKey(i) && minDrinksPerAmt.get(i) < lowest) {
                lowest = minDrinksPerAmt.get(i);
            }
        }

        return lowest;
    }

    public static void main(String[] args) {
        ArrayList<Integer> drinks = new ArrayList<>();
        drinks.add(5);
        drinks.add(5);
        drinks.add(5);
        drinks.add(3);
        drinks.add(3);
        drinks.add(3);
        drinks.add(3);
        drinks.add(10);
        drinks.add(10);
        System.out.println(minDrinks(drinks, 11, 12));
    }
}

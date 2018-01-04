package ieee_coding_challenge;

import java.util.HashMap;

class Stats {

    private final String className;
    private final HashMap<String, String> errors;
    private final HashMap<String, String> timings;

    Stats(String className) {
        this.className = className;
        this.errors = new HashMap<>();
        this.timings = new HashMap<>();
    }

    void printStats() {
        System.out.println(className);
        for (String key : errors.keySet()) {
            System.out.println(key + ": " + errors.get(key));
        }
        for (String key: timings.keySet()) {
            System.out.println(key + ": " + timings.get(key));
        }
    }

    void putErr(String testName, String errMsg) {
        errors.put(testName, errMsg);
    }

    void putTiming(String testName, String timing) {
        timings.put(testName, timing);
    }
}

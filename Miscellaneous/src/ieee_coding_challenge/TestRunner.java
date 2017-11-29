package ieee_coding_challenge;

import java.util.function.Consumer;

class TestRunner<T> {

    private final CLoader<T> cLoader;

    TestRunner(Class<T> tClass, String path, String packagePath) {
        this.cLoader = new CLoader<>(tClass, path, packagePath);
    }

    void runTest(Class c) {

    }

    static <T> Consumer<T> testWrapper(Consumer<T> c) {
        Consumer<T> newC = t -> {

        };
        return newC;
    }

    static <T> Consumer<T> timerWrapper(Consumer<T> c) {
        Consumer<T> newC = t -> {

        };
        return newC;
    }
}

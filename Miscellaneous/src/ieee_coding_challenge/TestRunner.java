package ieee_coding_challenge;

import java.util.concurrent.Callable;

public class TestRunner<T> {

    private final CLoader<T> cLoader;

    public TestRunner(Class<T> tClass, Callable<T> f, String path) {
        this.cLoader = new CLoader<>(tClass, path);
    }

    public void run() {

    }
}

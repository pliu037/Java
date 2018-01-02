package ieee_coding_challenge;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class TestRunner<T> {

    private final CLoader<T> cLoader;
    protected ArrayList<Consumer<T>> tests;

    protected TestRunner(Class<T> tClass, String path, String packagePath) {
        this.cLoader = new CLoader<>(tClass, path, packagePath);
        this.tests = getTests(this.getClass());
    }

    public void runTestsOnClasses() {
        while (cLoader.hasNext()) {
            try {
                CLoader.LoaderOutput<T> cd = cLoader.getNext();
                T toTest = cd.obj;
            } catch (CLoader.LoaderException e) {

            }
        }
    }

    private void runTestsOnClass(T t) {

    }

    protected static <T> ArrayList<Consumer<T>> getTests(Class c) {
        for (Method m : c.getDeclaredMethods()) {
            if (m.getAnnotation(Test.class) != null) {
                System.out.println(m.getName());
            }
        }
        return new ArrayList<>();
    }

    private static <T> Consumer<T> testWrapper(Consumer<T> c) {
        Consumer<T> newC = t -> {

        };
        return newC;
    }

    private static <T> Consumer<T> timerWrapper(Consumer<T> c) {
        Consumer<T> newC = t -> {

        };
        return newC;
    }

    public static void main(String[] args) {
        getTests(Q1Tests.class);
    }
}

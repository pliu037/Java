package ieee_coding_challenge;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class TestRunner<T> {

    private final CLoader<T> cLoader;
    private final ArrayList<NamedConsumer<T>> tests;

    private Stats stats;

    protected TestRunner(Class<T> tClass, String path, String packagePath) {
        this.cLoader = new CLoader<>(tClass, path, packagePath);
        this.tests = getTests(this.getClass());
    }

    public void runTestsOnClasses() {
        while (cLoader.hasNext()) {
            try {
                CLoader.LoaderOutput<T> o = cLoader.getNext();
                stats = new Stats(o.className);
                for (NamedConsumer<T> test : tests) {
                    test.c.accept(o.obj);
                }
            } catch (CLoader.LoaderError e) {
                stats = new Stats(e.className);
                stats.putErr("Loading class", e.getMessage());
            }
            stats.printStats();
        }
    }

    private NamedConsumer<T> consumerWrapper(Method m) {
        NamedConsumer<T> newNC = new NamedConsumer<>(m.getName(), t -> {
            try {
                m.invoke(null, t);
            } catch (InvocationTargetException e) {
                stats.putErr(m.getName(), e.getCause().getMessage());
            } catch (Exception e) {
                stats.putErr(m.getName(), e.getMessage());
            }
        });
        return newNC;
    }

    private NamedConsumer<T> testWrapper(NamedConsumer<T> nc) {
        NamedConsumer<T> newNC = new NamedConsumer<>(nc.methodName, t -> {
            final ExecutorService executor = Executors.newSingleThreadExecutor();
            final Future future = executor.submit(new Thread(() -> nc.c.accept(t)));
            executor.shutdown();
            try {
                future.get(10, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException e) {

            } catch (TimeoutException e) {
                stats.putErr(nc.methodName, "Timed out");
                future.cancel(true);
            }
        });
        return newNC;
    }

    private NamedConsumer<T> timerWrapper(NamedConsumer<T> nc) {
        NamedConsumer<T> newNC = new NamedConsumer<>(nc.methodName, t -> {
            long start = System.nanoTime();
            nc.c.accept(t);
            stats.putTiming(nc.methodName, (System.nanoTime() - start)/1000 + " us");
        });
        return newNC;
    }

    private ArrayList<NamedConsumer<T>> getTests(Class c) {
        ArrayList<NamedConsumer<T>> tests = new ArrayList<>();
        for (Method m : c.getDeclaredMethods()) {
            if (m.getAnnotation(Test.class) != null) {
                NamedConsumer<T> test = consumerWrapper(m);
                if (m.getAnnotation(Timed.class) != null) {
                    test = timerWrapper(test);
                }
                test = testWrapper(test);
                tests.add(test);
            }
        }
        return tests;
    }
}

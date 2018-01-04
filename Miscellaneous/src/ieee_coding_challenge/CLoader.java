package ieee_coding_challenge;

import ieee_coding_challenge.q1.Fibonacci;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;

class CLoader<T> {

    private static LoaderOutput ABSTRACT = new LoaderOutput<>(null, null);
    private static int NO_NEXT = -1;

    static class LoaderOutput<T> {
        T obj;
        String className;

        LoaderOutput(T obj, String className) {
            this.obj = obj;
            this.className = className;
        }
    }

    static class LoaderError extends Error {
        String className;

        LoaderError(String className, String errMsg) {
            super(errMsg);
            this.className = className;
        }
    }

    private final Class<T> tClass;
    private final String packagePath;
    private final File[] files;
    private int count = 0;

    CLoader(Class<T> tClass, String path, String packagePath) {
        this.tClass = tClass;
        this.packagePath = packagePath;

        File dir = new File(path);
        if (isDirectoryPath(dir)) {
            this.files = dir.listFiles((file, name) -> name.endsWith(".class"));
        } else {
            this.files = null;
            System.out.println("Invalid path: " + path);
            System.exit(1);
        }
    }

    LoaderOutput<T> getNext() {
        int i = findNext();

        if (i == NO_NEXT) {
            throw new IndexOutOfBoundsException();
        }

        count = i;
        return getClassFromFile(files[count++]);
    }

    boolean hasNext() {
        return findNext() != NO_NEXT;
    }

    private int findNext() {
        int c = count;
        while (true) {
            try {
                if (c >= files.length) {
                    return NO_NEXT;
                }

                if (getClassFromFile(files[c]) == ABSTRACT) {
                    c++;
                    continue;
                }

                return c;
            } catch (LoaderError e) {
                return c;
            }
        }
    }

    private LoaderOutput<T> getClassFromFile(File file) {
        String fileName = file.getName().split("\\.")[0];
        String className = packagePath + "." + fileName;
        Class c;
        try {
            c = new URLClassLoader(new URL[]{file.toURI().toURL()}).loadClass(className);
        } catch (Exception e) {
            throw new LoaderError(className, "Can't load class " + className + " from " + file.getName() + ": " +
                    e.getMessage());
        }

        if (!tClass.isAssignableFrom(c)) {
            throw new LoaderError(className, className + " from " + file.getName() + " is not an instance of " +
                    tClass.getName());
        }

        if (Modifier.isAbstract(c.getModifiers())) {
            return ABSTRACT;
        }

        try {
            return new LoaderOutput<>((T)c.newInstance(), fileName);
        } catch (Exception e) {
            throw new LoaderError(className, className + " from " + file.getName() + " is missing a constructor: " +
                    e.getMessage());
        }
    }

    private static boolean isDirectoryPath(File path) {
        return path.exists() && path.isDirectory();
    }

    public static void main(String[] args) {
        CLoader<Fibonacci> cl = new CLoader<>(Fibonacci.class,
                "C:\\Users\\pengl\\Documents\\Programming\\Java\\Miscellaneous\\src\\ieee_coding_challenge\\q1",
                "ieee_coding_challenge.q1");
        while (cl.hasNext()) {
            try {
                LoaderOutput lo = cl.getNext();
                if (lo != ABSTRACT) {
                    System.out.println(lo.className);
                    Fibonacci f = (Fibonacci) lo.obj;
                    System.out.println(f.getNthFibonacci(40));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

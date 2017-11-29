package ieee_coding_challenge;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;

class CLoader<T> {

    private final Class<T> tClass;
    private final String path;
    private final String packagePath;
    private final File[] files;
    private int count = 0;

    CLoader(Class<T> tClass, String path, String packagePath) {
        this.tClass = tClass;
        this.path = path;
        this.packagePath = packagePath;
        File dir = new File(this.path);
        if (isDirectoryPath(dir)) {
            this.files = dir.listFiles((file, name) -> name.endsWith(".class"));
        } else {
            this.files = null;
            System.out.println("Invalid path: " + this.path);
            System.exit(1);
        }
    }

    T getNext() throws Exception {
        if (count >= files.length) {
            return null;
        }
        return getClassFromFile(files[count++]);
    }

    boolean hasNext() {
        return count < files.length;
    }

    private T getClassFromFile(File file) throws Exception {
        String className = packagePath + "." + file.getName().split("\\.")[0];
        Class c;
        try {
            c = new URLClassLoader(new URL[]{file.toURI().toURL()}).loadClass(className);
        } catch (Exception e) {
            throw new Exception("Can't load class " + className + " from " + file.getName());
        }
        if (!tClass.isAssignableFrom(c)) {
            throw new Exception(className + " from " + file.getName() + " is not an instance of " + tClass.getName());
        }
        if (Modifier.isAbstract(c.getModifiers())) {
            return null;
        }
        T t;
        try {
            t = (T)c.newInstance();
        } catch (Exception e) {
            throw new Exception(className + " from " + file.getName() + " is missing a constructor: " + e.getMessage());
        }
        return t;
    }

    private static boolean isDirectoryPath(File path) {
        return path.exists() && path.isDirectory();
    }
}

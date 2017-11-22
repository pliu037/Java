package ieee_coding_challenge;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class CLoader<T> {

    private final Class<T> tClass;
    private final String path;
    private final File[] files;
    private int count = 0;

    public CLoader(Class<T> tClass, String path) {
        this.tClass = tClass;
        this.path = path;
        File dir = new File(this.path);
        if (isDirectoryPath(dir)) {
            this.files = dir.listFiles((file, name) -> name.endsWith(".class"));
        } else {
            this.files = null;
            System.out.println("Invalid path: " + this.path);
            System.exit(1);
        }
    }

    public T getNext() {
        if (count >= files.length) {
            return null;
        }
        count ++;
        return getClassFromFile(files[count]);
    }

    public boolean hasNext() {
        return count < files.length;
    }

    private T getClassFromFile(File file) {
        String className = file.getName().split(".")[0];
        Class c = null;
        try {
            c = new URLClassLoader(new URL[]{file.toURI().toURL()}).loadClass(className);
        } catch (Exception e) {
            System.out.println("Can't load class " + className + " from " + file.getName());
            return null;
        }
        if (!tClass.isAssignableFrom(c)) {
            System.out.println(className + " from " + file.getName() + " is not an instance of " + tClass.getName());
            return null;
        }
        T t = null;
        try {
            t = (T)c.getConstructor(null).newInstance();
        } catch (Exception e) {
            System.out.println(className + " from " + file.getName() + " is missing a constructor");
        }
        return t;
    }

    private static boolean isDirectoryPath(File path) {
        return path.exists() && path.isDirectory();
    }

    public static void main(String[] args) {
        CLoader c = new CLoader(null, "D:\\Work\\Programming\\Java\\Job Application Work\\target\\classes\\tower_research_2016");
    }
}

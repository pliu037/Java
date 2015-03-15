import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class Q5 {

    public static class Cavern {

        public String name;
        public boolean minotaurVisited = false;
        public boolean candleBurning = false;
        public ArrayList<Cavern> paths;
        public ArrayList<Boolean> ryanMarked = new ArrayList<>();
        public ArrayList<Boolean> minotaurMarked = new ArrayList<>();

        public Cavern(String name) {
            this.name = name;
        }

        public void setPaths (ArrayList<Cavern> paths) {
            this.paths = paths;
            for (int i = 0; i < paths.size(); i ++) {
                ryanMarked.add(false);
                minotaurMarked.add(false);
            }
        }

        public Cavern ryansPath (Cavern previous) {
            for (int i = 0; i < paths.size(); i ++) {
                if (paths.get(i) == previous) {
                    i ++;
                    while (ryanMarked.get(i%paths.size())) {
                        i ++;
                    }
                    ryanMarked.set(i%paths.size(), true);
                    return paths.get(i%paths.size());
                }
            }
            return null;
        }

        public Cavern minotaursPath (Cavern previous) {
            for (int i = paths.size() - 1; i >= 0; i --) {
                if (paths.get(i) == previous) {
                    i --;
                    if (i < 0) {
                        i += paths.size();
                    }
                    while (minotaurMarked.get(i%paths.size())) {
                        i --;
                        if (i < 0) {
                            i += paths.size();
                        }
                    }
                    minotaurMarked.set(i, true);
                    return paths.get(i);
                }
            }
            return null;
        }
    }

    public static class Character {
        public Cavern previousLocation;
        public Cavern nextLocation;

        public Character(Cavern previous, Cavern next) {
            previousLocation = previous;
            nextLocation = next;
        }
    }

    private static ArrayList<Cavern> getPaths(String pathsString, Hashtable<String, Cavern> map) {
        ArrayList<Cavern> paths = new ArrayList<>();
        for (int i = 0; i < pathsString.length(); i ++) {
            String pathName = pathsString.substring(i, i + 1);
            if (map.containsKey(pathName)) {
                paths.add(map.get(pathName));
            }
            else {
                Cavern c = new Cavern(pathName);
                map.put(pathName, c);
                paths.add(c);
            }
        }
        return paths;
    }

    // Returns true if Ryan dies
    private static boolean inTransit(Character ryan, Character minotaur) {
        if ((ryan.previousLocation == minotaur.nextLocation || ryan.previousLocation == minotaur.previousLocation)
                && (ryan.nextLocation == minotaur.previousLocation || ryan.nextLocation == minotaur.nextLocation)) {
            return true;
        }
        else {
            if (minotaur.nextLocation.candleBurning) {
                Cavern c = minotaur.previousLocation;
                minotaur.previousLocation = minotaur.nextLocation;
                minotaur.nextLocation = c;
            }
            return false;
        }
    }

    //Returns true if minotaur dies
    private static boolean inCavern(Character ryan, Character minotaur) {
        if (ryan.nextLocation == minotaur.nextLocation) {
            return true;
        }

        Cavern ryansLocation = ryan.nextLocation;
        Cavern minotaursLocation = minotaur.nextLocation;

        minotaursLocation.minotaurVisited = true;
        if (ryansLocation.minotaurVisited) {
            ryansLocation.candleBurning = true;
        }

        ryan.nextLocation = ryansLocation.ryansPath(ryan.previousLocation);
        ryan.previousLocation = ryansLocation;

        minotaur.nextLocation = minotaursLocation.minotaursPath(minotaur.previousLocation);
        minotaur.previousLocation = minotaursLocation;

        return false;
    }

    private static void runSimulation(Hashtable<String, Cavern> map, String start, PrintWriter output) {
        Character ryan = new Character(map.get(start.substring(1, 2)), map.get(start.substring(2, 3)));
        Character minotaur = new Character(map.get(start.substring(3, 4)), map.get(start.substring(4, 5)));


        while (true) {
            if (inTransit(ryan, minotaur)) {
                output.println("Ryan is killed between " + ryan.previousLocation.name + " and " + ryan.nextLocation.name);
                return;
            }

            if (inCavern(ryan, minotaur)) {
                output.println("The Minotaur is slain in " + minotaur.nextLocation.name);
                return;
            }
        }
    }

    public static void main (String[] args) {
        String inputPath = args[0];
        String outputPath = args[1];
        File file = new File(inputPath);
        File file2 = new File(outputPath);
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            PrintWriter output = new PrintWriter(file2);
            String line = "";
            while (!(line = input.readLine()).equals("#")) {
                Hashtable<String, Cavern> map = new Hashtable<>();
                while (line.charAt(0) != '@') {
                    StringTokenizer tokenizer = new StringTokenizer(line, ":");
                    String cavernName = tokenizer.nextToken();
                    ArrayList<Cavern> paths = getPaths(tokenizer.nextToken(), map);
                    if (map.containsKey(cavernName)) {
                        map.get(cavernName).setPaths(paths);
                    }
                    else {
                        Cavern c = new Cavern(cavernName);
                        c.setPaths(paths);
                        map.put(cavernName, c);
                    }
                    line = input.readLine();
                }
                runSimulation(map, line, output);
            }
            input.close();
            output.close();
        }
        catch(IOException e) {
            return;
        }
    }
}

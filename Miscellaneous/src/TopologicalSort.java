import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TopologicalSort {

    private static class Story implements TopologicalSortable {

        private static int counter = 1;
        private int id;
        private HashSet<Integer> dependencies;

        private Story(int[] deps) {
            id = counter ++;
            dependencies = new HashSet<>();
            for (int dep : deps) {
                dependencies.add(dep);
            }
        }

        private void addDependency(int dependency_id) {
            dependencies.add(dependency_id);
        }

        @Override
        public int getID() {
            return id;
        }

        @Override
        public HashSet<Integer> getDependencies() {
            return dependencies;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public boolean equals(Object other) {
            if (other.getClass() != Story.class) {
                return false;
            }
            Story other_story = (Story) other;
            return id == other_story.id;
        }
    }

    public static ArrayList<ArrayList<Integer>> sort(HashSet<TopologicalSortable> stories) {
        HashMap<Integer, HashSet<Integer>> dependee_map = new HashMap<>();
        HashMap<Integer, HashSet<Integer>> dependency_map = new HashMap<>();
        for (TopologicalSortable story : stories) {
            if (!dependee_map.containsKey(story.getID())) {
                dependee_map.put(story.getID(), new HashSet<>());
            }
            for (Integer dependency : story.getDependencies()) {
                if (!dependee_map.containsKey(dependency)) {
                    dependee_map.put(dependency, new HashSet<>());
                }
                dependee_map.get(dependency).add(story.getID());
            }
            dependency_map.put(story.getID(), new HashSet<>(story.getDependencies()));
        }

        ArrayList<Integer> frontier = new ArrayList<>();
        for (Integer check : dependency_map.keySet()) {
            if (dependency_map.get(check).isEmpty()) {
                frontier.add(check);
            }
        }
        for (Integer check : frontier) {
            dependency_map.remove(check);
        }

        ArrayList<ArrayList<Integer>> ordering = new ArrayList<>();
        int round = 1;
        while (round <= stories.size()) {
            ArrayList<Integer> next_frontier = new ArrayList<>();
            ordering.add(frontier);

            for (Integer check : frontier) {
                for (Integer out_path : dependee_map.get(check)) {
                    dependency_map.get(out_path).remove(check);
                    if (dependency_map.get(out_path).isEmpty()) {
                        next_frontier.add(out_path);
                        dependency_map.remove(out_path);
                    }
                }
            }

            if (next_frontier.isEmpty()) {
                break;
            }
            frontier = next_frontier;
            round ++;
        }

        if (!dependency_map.isEmpty()) {
            return null;
        }

        return ordering;
    }

    public static void main(String[] args) {
        HashSet<TopologicalSortable> stories = new HashSet<>();
        stories.add(new Story(new int[]{2, 3}));
        stories.add(new Story(new int[]{4}));
        stories.add(new Story(new int[]{5}));
        stories.add(new Story(new int[]{5, 6}));
        stories.add(new Story(new int[]{}));        // Add 3 here to test a cycle
        stories.add(new Story(new int[]{}));

        ArrayList<ArrayList<Integer>> ordering = sort(stories);
        if (ordering != null) {
            for (ArrayList<Integer> tier : ordering) {
                for (Integer i : tier) {
                    System.out.print(i + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("#rip");
        }
    }
}

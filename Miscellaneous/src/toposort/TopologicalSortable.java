package toposort;

import java.util.HashSet;

public interface TopologicalSortable extends Identifiable {
    public HashSet<Integer> getDependencies();
}

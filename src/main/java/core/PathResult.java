package core;

import java.util.List;

public class PathResult {
    public final List<int[]> path;  // cells from start -> goal
    public final boolean success;
    public final int nodesExpanded;
    public final long millis;

    public PathResult(List<int[]> path, boolean success, int nodesExpanded, long millis) {
        this.path = path;
        this.success = success;
        this.nodesExpanded = nodesExpanded;
        this.millis = millis;
    }
}

package algo;

import core.Grid;
import core.PathResult;

public interface Pathfinder {
    PathResult findPath(Grid grid, int sx, int sy, int gx, int gy);
    String name();
}

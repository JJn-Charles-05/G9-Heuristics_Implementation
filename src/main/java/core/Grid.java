package core;

import java.util.*;

public class Grid {
    public final int w, h;
    private final boolean[][] blocked;
    private final Random rng;

    public Settings.Point start;
    public Settings.Point goal;

    public Grid(int w, int h, double obstacleDensity, long seed) {
        this.w = w;
        this.h = h;
        this.blocked = new boolean[h][w];
        this.rng = new Random(seed);
        generate(obstacleDensity);
    }

    private void generate(double density) {
        for (int y=0; y<h; y++) {
            for (int x=0; x<w; x++) {
                blocked[y][x] = (rng.nextDouble() < density);
            }
        }
        // Keep corners free so default start/goal are valid
        blocked[0][0] = false;
        blocked[h-1][w-1] = false;
    }

    public boolean inBounds(int x, int y){ return x>=0 && x<w && y>=0 && y<h; }
    public boolean isBlocked(int x, int y){ return blocked[y][x]; }
    public void setBlocked(int x,int y, boolean b){ blocked[y][x] = b; }

    public java.util.List<int[]> neighbors(int x, int y) {
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}}; // 4-way
        java.util.List<int[]> out = new java.util.ArrayList<>();
        for (int[] d : dirs) {
            int nx = x + d[0], ny = y + d[1];
            if (inBounds(nx, ny) && !isBlocked(nx, ny)) out.add(new int[]{nx, ny});
        }
        return out;
    }

    // Randomly move some obstacles one step into empty cells (simulate dynamics)
    public void jiggleObstacles(double moveProb) {
        if (moveProb <= 0) return;

        java.util.List<int[]> obstacles = new java.util.ArrayList<>();
        for (int y=0; y<h; y++) for (int x=0; x<w; x++)
            if (blocked[y][x]) obstacles.add(new int[]{x,y});

        java.util.Collections.shuffle(obstacles, rng);

        for (int[] cell : obstacles) {
            if (rng.nextDouble() > moveProb) continue;
            int x = cell[0], y = cell[1];
            int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
            java.util.List<int[]> dirList = new java.util.ArrayList<>();
            for (int[] d : dirs) dirList.add(d);
            java.util.Collections.shuffle(dirList, rng);

            for (int[] d : dirList) {
                int nx = x + d[0], ny = y + d[1];
                if (!inBounds(nx, ny)) continue;
                if (!blocked[ny][nx]) {
                    blocked[y][x] = false;
                    blocked[ny][nx] = true;
                    break;
                }
            }
        }
        // Ensure start/goal remain free
        if (start != null) blocked[start.y][start.x] = false;
        if (goal  != null) blocked[goal.y][goal.x]  = false;
    }
}

package core;

public class Settings {
    public enum Algorithm { ASTAR, DIJKSTRA }

    public int width = 20;
    public int height = 10;
    public double obstacleDensity = 0.2; // 0..1
    public boolean dynamicObstacles = true;
    public double dynamicObstacleMoveProb = 0.2; // per obstacle per tick
    public int maxTicks = 30;
    public Algorithm algo = Algorithm.ASTAR;
    public long seed = System.currentTimeMillis();

    public Point start = null;
    public Point goal = null;

    public static class Point {
        public final int x, y;
        public Point(int x, int y){ this.x = x; this.y = y; }
    }
}

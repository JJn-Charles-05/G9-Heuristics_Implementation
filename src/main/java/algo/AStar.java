package algo;

import core.Grid;
import core.PathResult;

import java.util.*;

public class AStar implements Pathfinder {
    static class Node implements Comparable<Node> {
        int x,y;
        double g, f; // f = g + h
        Node(int x,int y,double g,double f){ this.x=x; this.y=y; this.g=g; this.f=f; }
        public int compareTo(Node o){ return Double.compare(this.f, o.f); }
    }

    private static int manhattan(int x1,int y1,int x2,int y2){
        return Math.abs(x1-x2)+Math.abs(y1-y2);
    }

    @Override
    public PathResult findPath(Grid grid, int sx, int sy, int gx, int gy) {
        long t0 = System.currentTimeMillis();
        int nodesExpanded = 0;

        double[][] gCost = new double[grid.h][grid.w];
        for (int y=0;y<grid.h;y++) Arrays.fill(gCost[y], Double.POSITIVE_INFINITY);
        gCost[sy][sx] = 0.0;

        int[][] parent = new int[grid.h][grid.w];
        for (int y=0;y<grid.h;y++) Arrays.fill(parent[y], -1);

        PriorityQueue<Node> open = new PriorityQueue<>();
        open.add(new Node(sx, sy, 0.0, manhattan(sx,sy,gx,gy)));
        boolean[][] closed = new boolean[grid.h][grid.w];

        while(!open.isEmpty()){
            Node cur = open.poll();
            nodesExpanded++;

            if (cur.x == gx && cur.y == gy) break;
            if (closed[cur.y][cur.x]) continue;
            closed[cur.y][cur.x] = true;

            for (int[] nb : grid.neighbors(cur.x, cur.y)){
                int nx = nb[0], ny = nb[1];
                if (closed[ny][nx]) continue;

                double tentativeG = gCost[cur.y][cur.x] + 1.0;
                if (tentativeG < gCost[ny][nx]){
                    gCost[ny][nx] = tentativeG;
                    parent[ny][nx] = pack(cur.x, cur.y);
                    double f = tentativeG + manhattan(nx, ny, gx, gy);
                    open.add(new Node(nx, ny, tentativeG, f));
                }
            }
        }

        boolean success = gCost[gy][gx] < Double.POSITIVE_INFINITY;
        java.util.List<int[]> path = reconstruct(parent, sx, sy, gx, gy, success);
        long ms = System.currentTimeMillis() - t0;
        return new PathResult(path, success, nodesExpanded, ms);
    }

    @Override public String name(){ return "A*"; }

    private static int pack(int x,int y){ return (y<<16) ^ x; }
    private static int[] unpack(int v){ return new int[]{ v & 0xFFFF, (v>>>16) & 0xFFFF }; }

    private static java.util.List<int[]> reconstruct(int[][] parent, int sx,int sy,int gx,int gy, boolean ok){
        java.util.LinkedList<int[]> path = new java.util.LinkedList<>();
        if (!ok) return path;
        int cx = gx, cy = gy;
        while (!(cx==sx && cy==sy)){
            path.addFirst(new int[]{cx,cy});
            int p = parent[cy][cx];
            if (p == -1) break;
            int[] up = unpack(p);
            cx = up[0]; cy = up[1];
        }
        path.addFirst(new int[]{sx,sy});
        return path;
    }
}


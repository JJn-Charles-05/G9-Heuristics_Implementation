package algo;

import core.Grid;
import core.PathResult;

import java.util.*;

public class Dijkstra implements Pathfinder {
    static class Node implements Comparable<Node> {
        int x,y;
        double g; // cost from start
        Node(int x,int y,double g){ this.x=x; this.y=y; this.g=g; }
        public int compareTo(Node o){ return Double.compare(this.g, o.g); }
    }

    @Override
    public PathResult findPath(Grid grid, int sx, int sy, int gx, int gy) {
        long t0 = System.currentTimeMillis();
        int nodesExpanded = 0;

        double[][] dist = new double[grid.h][grid.w];
        for (int y=0;y<grid.h;y++) Arrays.fill(dist[y], Double.POSITIVE_INFINITY);
        dist[sy][sx] = 0.0;

        int[][] parent = new int[grid.h][grid.w];
        for (int y=0;y<grid.h;y++) Arrays.fill(parent[y], -1);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(sx, sy, 0.0));

        while(!pq.isEmpty()){
            Node cur = pq.poll();
            nodesExpanded++;
            if (cur.x == gx && cur.y == gy) break;
            if (cur.g > dist[cur.y][cur.x]) continue; // stale

            for (int[] nb : grid.neighbors(cur.x, cur.y)){
                int nx = nb[0], ny = nb[1];
                double ng = cur.g + 1.0; // unit step cost
                if (ng < dist[ny][nx]){
                    dist[ny][nx] = ng;
                    parent[ny][nx] = pack(cur.x, cur.y);
                    pq.add(new Node(nx, ny, ng));
                }
            }
        }

        boolean success = dist[gy][gx] < Double.POSITIVE_INFINITY;
        java.util.List<int[]> path = reconstruct(parent, sx, sy, gx, gy, success);
        long ms = System.currentTimeMillis() - t0;
        return new PathResult(path, success, nodesExpanded, ms);
    }

    @Override public String name(){ return "Dijkstra"; }

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

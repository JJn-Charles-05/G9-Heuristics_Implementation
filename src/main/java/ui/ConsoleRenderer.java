package ui;

import core.Grid;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConsoleRenderer {
    public void draw(Grid grid, List<int[]> path) {
        Set<Long> pathCells = new HashSet<>();
        if (path != null) {
            for (int[] p : path) pathCells.add(((long)p[1] << 32) | (p[0] & 0xffffffffL));
        }
        StringBuilder sb = new StringBuilder();
        for (int y=0;y<grid.h;y++){
            for (int x=0;x<grid.w;x++){
                boolean isS = (grid.start != null && grid.start.x==x && grid.start.y==y);
                boolean isG = (grid.goal  != null && grid.goal.x==x  && grid.goal.y==y);
                boolean block = grid.isBlocked(x,y);
                long key = ((long)y<<32)|(x&0xffffffffL);

                char c;
                if (isS) c='S';
                else if (isG) c='G';
                else if (block) c='#';
                else if (pathCells.contains(key)) c='·';
                else c=' ';

                sb.append(c);
            }
            sb.append('\n');
        }
        System.out.print(sb.toString());
    }
}

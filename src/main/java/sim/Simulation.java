package sim;

import algo.AStar;
import algo.Dijkstra;
import algo.Pathfinder;
import core.Grid;
import core.PathResult;
import core.Settings;
import metrics.Metrics;
import ui.ConsoleRenderer;

public class Simulation {
    private final Settings s;
    private final ConsoleRenderer renderer;
    private final Grid grid;
    private final Pathfinder pf;

    public Simulation(Settings s, ConsoleRenderer renderer){
        this.s = s;
        this.renderer = renderer;
        this.grid = new Grid(s.width, s.height, s.obstacleDensity, s.seed);

        if (s.start == null) s.start = new Settings.Point(0,0);
        if (s.goal  == null) s.goal  = new Settings.Point(s.width-1, s.height-1);
        grid.start = s.start;
        grid.goal  = s.goal;

        this.pf = switch (s.algo) {
            case ASTAR -> new AStar();
            case DIJKSTRA -> new Dijkstra();
        };
    }

    public void run(){
        System.out.println("=== Group 9 Comparative Simulation ===");
        System.out.println("Algorithm: " + pf.name());
        System.out.printf("Grid: %dx%d, density=%.2f, dynamic=%s, ticks=%d%n",
                s.width, s.height, s.obstacleDensity, s.dynamicObstacles, s.maxTicks);
        System.out.printf("Start=(%d,%d), Goal=(%d,%d)%n%n", s.start.x, s.start.y, s.goal.x, s.goal.y);

        for (int tick = 1; tick <= s.maxTicks; tick++){
            if (s.dynamicObstacles) grid.jiggleObstacles(s.dynamicObstacleMoveProb);
            grid.setBlocked(s.start.x, s.start.y, false);
            grid.setBlocked(s.goal.x,  s.goal.y,  false);

            PathResult r = pf.findPath(grid, s.start.x, s.start.y, s.goal.x, s.goal.y);

            System.out.println("--- Tick " + tick + " ---");
            renderer.draw(grid, r.path);
            System.out.println(Metrics.summarize(r));
            System.out.println();

            try { Thread.sleep(150); } catch (InterruptedException ignored) {}
        }
        System.out.println("=== Done ===");
    }
}


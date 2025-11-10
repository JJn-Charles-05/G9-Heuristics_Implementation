import core.Settings;
import sim.Simulation;
import ui.ConsoleRenderer;

public class App {
    public static void main(String[] args) {
        Settings s = new Settings();
        s.width = 20;
        s.height = 12;
        s.obstacleDensity = 0.18;
        s.dynamicObstacles = true;
        s.dynamicObstacleMoveProb = 0.25;
        s.maxTicks = 40;
        s.seed = 42L;

        // AStar
        s.algo = Settings.Algorithm.ASTAR; // ASTAR or DIJKSTRA
        Simulation simASTAR = new Simulation(s, new ConsoleRenderer());
        String ASTARMetrics = simASTAR.run();

        // Dijkstra
        s.algo = Settings.Algorithm.DIJKSTRA; // ASTAR or DIJKSTRA
        Simulation simDIJKSTRA = new Simulation(s, new ConsoleRenderer());
        String DikjstraMetrics = simDIJKSTRA.run();

        // sum
        System.out.print("A* Metrics");
        System.out.print(ASTARMetrics);
        System.out.print("Dikjstra Metrics");
        System.out.print(DikjstraMetrics);
    }
}

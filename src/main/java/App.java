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
        s.algo = Settings.Algorithm.ASTAR; // ASTAR or DIJKSTRA
        s.seed = 42L;

        Simulation sim = new Simulation(s, new ConsoleRenderer());
        sim.run();
    }
}

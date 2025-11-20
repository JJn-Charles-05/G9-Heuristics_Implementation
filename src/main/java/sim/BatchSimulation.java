package sim;
import core.Settings;
import sim.Simulation;
import ui.ConsoleRenderer;
import java.util.ArrayList;

import java.util.Random;

public class BatchSimulation
{
    public void RunSimulations(int numberOfSimulations)
    {
        Settings s = new Settings();
        Simulation sim;
        s.width = 90;
        s.height = 90;
        s.obstacleDensity = 0.18;
        s.dynamicObstacles = true;
        s.dynamicObstacleMoveProb = 0.25;
        s.maxTicks = 1;

        Random rand = new Random();
        ArrayList<Long> seeds = new ArrayList<Long>();
        for(int i = 0; i < numberOfSimulations; i++)
        {
            seeds.add(rand.nextLong());
        }

        s.algo = Settings.Algorithm.ASTAR;
        long startTimeASTAR = System.nanoTime();
        for(int i = 0; i < numberOfSimulations; i++)
        {
            s.seed = seeds.get(i);
            sim = new Simulation(s, new ConsoleRenderer());
            System.out.println(sim.run());
        }
        long endTimeASTAR = System.nanoTime();

        s.algo = Settings.Algorithm.DIJKSTRA;
        long startTimeDijkstra = System.nanoTime();
        for(int i = 0; i < numberOfSimulations; i++)
        {
            s.seed = seeds.get(i);
            sim = new Simulation(s, new ConsoleRenderer());
            System.out.println(sim.run());
        }
        long endTimeDijkstra = System.nanoTime();

        long totalTimeASTAR = (endTimeASTAR - startTimeASTAR) / 1_000_000;
        long totalTimeDijkstra = (endTimeDijkstra - startTimeDijkstra) / 1_000_000;

        System.out.println("=== Done ===");
        System.out.printf("Total time for %d simulations using A* : %d ms%n", numberOfSimulations, totalTimeASTAR);
        System.out.printf("Total time for %d simulations using Dijkstra : %d ms%n", numberOfSimulations, totalTimeDijkstra);
    }
}

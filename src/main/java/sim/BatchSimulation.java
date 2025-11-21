package sim;
import core.Settings;
import core.SimulationResult;
import sim.Simulation;
import ui.ConsoleRenderer;
import java.util.ArrayList;

import java.util.Random;

public class BatchSimulation
{
    // total run time
    public long ASTAR_Total_Time;
    public long DIJKSTRA_Total_Time;

    public Settings settings;

    // avg path length
    public double ASTAR_Average_Path;
    public double DIJKSTRA_Average_Path;

    // number of success
    public double ASTAR_Successes;
    public double DIJKSTRA_Successes;

    // average nodes expanded
    public double ASTAR_Average_Nodes;
    public double DIJKSTRA_Average_Nodes;

    public BatchSimulation(Settings s)
    {
        settings = s;
    }
    public BatchSimulation()
    {

    }

    public void RunSimulations(int numberOfSimulations)
    {
        Simulation sim;
        if(settings == null)
        {
            settings = new Settings();
            settings.width = 100;
            settings.height = 100;
            settings.obstacleDensity = 0.28;
            settings.dynamicObstacles = true;
            settings.dynamicObstacleMoveProb = 0.25;
            settings.maxTicks = 1;
        }

        Random rand = new Random();
        ArrayList<Long> seeds = new ArrayList<Long>();
        for(int i = 0; i < numberOfSimulations; i++)
        {
            seeds.add(rand.nextLong());
        }
        ArrayList<SimulationResult> ASTARresults = new ArrayList<SimulationResult>();
        ArrayList<SimulationResult> DIJKSTRAresults = new ArrayList<SimulationResult>();
        settings.algo = Settings.Algorithm.ASTAR;
        long startTimeASTAR = System.nanoTime();
        for(int i = 0; i < numberOfSimulations; i++)
        {
            settings.seed = seeds.get(i);
            sim = new Simulation(settings, new ConsoleRenderer());
            ASTARresults.add(sim.run());
        }
        long endTimeASTAR = System.nanoTime();

        settings.algo = Settings.Algorithm.DIJKSTRA;
        long startTimeDijkstra = System.nanoTime();
        for(int i = 0; i < numberOfSimulations; i++)
        {
            settings.seed = seeds.get(i);
            sim = new Simulation(settings, new ConsoleRenderer());
            DIJKSTRAresults.add(sim.run());
        }
        long endTimeDijkstra = System.nanoTime();

        // set total time
        ASTAR_Total_Time = (endTimeASTAR - startTimeASTAR) / 1_000_000;
        DIJKSTRA_Total_Time = (endTimeDijkstra - startTimeDijkstra) / 1_000_000;

        ASTAR_Average_Path = 0;
        DIJKSTRA_Average_Path = 0;
        ASTAR_Successes = 0;
        DIJKSTRA_Successes = 0;
        ASTAR_Average_Nodes = 0;
        DIJKSTRA_Average_Nodes = 0;
        SimulationResult currResult = null;
        for(int i = 0; i < numberOfSimulations; i++)
        {
            currResult = ASTARresults.get(i);
            ASTAR_Average_Path += currResult.pathLength;
            ASTAR_Successes += currResult.isReachedGoal() ? 1 : 0;
            ASTAR_Average_Nodes += currResult.totalNodesExpanded;
            currResult = DIJKSTRAresults.get(i);
            DIJKSTRA_Average_Path += currResult.pathLength;
            DIJKSTRA_Successes += currResult.isReachedGoal() ? 1 : 0;
            DIJKSTRA_Average_Nodes += currResult.totalNodesExpanded;
        }
        ASTAR_Average_Path = ASTAR_Average_Path / numberOfSimulations;
        DIJKSTRA_Average_Path = DIJKSTRA_Average_Path / numberOfSimulations;
        ASTAR_Average_Nodes = ASTAR_Average_Nodes / numberOfSimulations;
        DIJKSTRA_Average_Nodes = DIJKSTRA_Average_Nodes / numberOfSimulations;
        System.out.println("=== Done ===");

    }
}

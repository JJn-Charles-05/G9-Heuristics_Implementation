import HeuristicsCompareGUI.HeuristicsCompareGUI;
import core.Settings;
import sim.Simulation;
import ui.ConsoleRenderer;

import javax.swing.*;

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

        //Linking the Heuristics Comparison GUI package to the app package
        HeuristicsCompareGUI heuristicsGUI = new HeuristicsCompareGUI();
        JFrame frame = new JFrame("Heuristics Comparison GUI");

        //frame.setContentPane(heuristicsGUI.getPanel()); make getPanel in GUI.java
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
}

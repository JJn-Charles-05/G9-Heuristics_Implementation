import core.Settings;
import sim.BatchSimulation;
import sim.Simulation;
import ui.ConsoleRenderer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        System.out.println("testing batch simulations");
        BatchSimulation batch = new BatchSimulation();
        batch.RunSimulations(5);
        try {
            System.out.println(getClass().getResource("/heuristicCompareGUI.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/heuristicCompareGUI.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Heuristics Comparison GUI");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("!! - FXML failed to load!");
        }
    }

    public static void main(String[] args) {
        launch(args);

        /*Settings s = new Settings();
        s.width = 20;
        s.height = 12;
        s.obstacleDensity = 0.18;
        s.dynamicObstacles = true;
        s.dynamicObstacleMoveProb = 0.25;
        s.maxTicks = 40;
        s.algo = Settings.Algorithm.ASTAR; // ASTAR or DIJKSTRA
        s.seed = 42L;

        Simulation sim = new Simulation(s, new ConsoleRenderer());
        System.out.println(sim.run());

        System.out.println("performing batch simulations");
        BatchSimulation batch = new BatchSimulation();
        batch.RunSimulations(10);*/
    }
}

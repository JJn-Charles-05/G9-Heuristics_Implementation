import core.Settings;
import sim.Simulation;
import ui.ConsoleRenderer;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello, World!");
        Scene scene = new Scene(label, 400, 400);
        stage.setTitle("JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

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

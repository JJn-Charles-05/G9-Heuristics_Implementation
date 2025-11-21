package ui;

import core.Settings;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import sim.BatchSimulation;
import sim.Simulation;
import ui.ConsoleRenderer;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.event.ActionEvent;

public class MainController {
    // All labels
    @FXML
    private Label titleLabel;

    @FXML
    private Label uiWidthLabel;

    @FXML
    private Label uiHeightLabel;

    @FXML
    private Label uiODLabel;

    @FXML
    private Label uiDynamicLabel;

    @FXML
    private Label uiDynamicProbLabel;

    @FXML
    private Label uiTicksLabel;

    @FXML
    private Label uiAlgoLabel;

    @FXML
    private Label uiSeedLabel;

    // All UI Tools
    @FXML
    private Button runSimButton;

    @FXML
    private TextField widthInput;

    @FXML
    private TextField heightInput;

    @FXML
    private TextField odInput;

    @FXML
    private CheckBox dynamicInput;

    @FXML
    private TextField dynamicProbInput;

    @FXML
    private TextField ticksInput;

    @FXML
    private MenuButton algoInput;

    @FXML
    private TextField seedInput;

    // Program-Generated
    @FXML
    private TextArea textArea;

    @FXML
    private BarChart<String, Number> runtimeBarChart;

    @FXML
    private BarChart<String, Number> nodesBarChart;

    @FXML
    private BarChart<String, Number> pathLenBarChart;

    @FXML
    private BarChart<String, Number> successBarChart;


    /*@FXML
    private void initialize() {
        // runSimButton.setOnAction(this::runSimulations);
    }*/

    // Method to run the Sim; used for button press functionality
    @FXML
    private void runSimulations (ActionEvent event) {
        /*Settings s = new Settings();
        s.width = 20;
        s.height = 12;
        s.obstacleDensity = 0.18;
        s.dynamicObstacles = true;
        s.dynamicObstacleMoveProb = 0.25;
        s.maxTicks = 40;
        s.algo = Settings.Algorithm.ASTAR; // ASTAR or DIJKSTRA
        s.seed = 42L;*/

        // Simulation sim = new Simulation(s, new ConsoleRenderer(textArea));
        //sim.run(); returns sim results
        //System.out.println(sim.run());


        System.out.println("performing batch simulations");
        BatchSimulation batch = new BatchSimulation();
        batch.RunSimulations(10);

        // Runtime BarChart
        XYChart.Series<String, Number> runtimeSeries = new XYChart.Series<>();
        runtimeSeries.setName("Avg. Runtime (ms)");
        runtimeSeries.getData().add(new XYChart.Data("ASTAR", batch.ASTAR_Total_Time ));
        runtimeSeries.getData().add(new XYChart.Data("Dijkstra", batch.DIJKSTRA_Total_Time));
        runtimeBarChart.getData().add(runtimeSeries);

        // Nodes BarChart
        XYChart.Series<String, Number> NodesSeries = new XYChart.Series<>();
        NodesSeries.setName("Avg. Nodes");
        NodesSeries.getData().add(new XYChart.Data("ASTAR", batch.ASTAR_Average_Nodes));
        NodesSeries.getData().add(new XYChart.Data("Dijkstra", batch.DIJKSTRA_Average_Nodes));
        nodesBarChart.getData().add(NodesSeries);

        // Path Length BarChart
        XYChart.Series<String, Number> PathSeries = new XYChart.Series<>();
        PathSeries.setName("Avg. Path");
        PathSeries.getData().add(new XYChart.Data("ASTAR", batch.ASTAR_Average_Path));
        PathSeries.getData().add(new XYChart.Data("Dijkstra", batch.DIJKSTRA_Average_Path));
        pathLenBarChart.getData().add(PathSeries);

        // Num. Successes BarChart
        XYChart.Series<String, Number> SuccessSeries = new XYChart.Series<>();
        SuccessSeries.setName("Num. Successes");
        SuccessSeries.getData().add(new XYChart.Data("ASTAR", batch.ASTAR_Successes));
        SuccessSeries.getData().add(new XYChart.Data("Dijkstra", batch.DIJKSTRA_Successes));
        successBarChart.getData().add(SuccessSeries);
    }
}

package ui;

import core.Settings;
import core.SimulationResult;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

    @FXML
    private Label uiNumBatchesLabel;

    // All Input Tools
    @FXML
    private Button runSimButton;

    @FXML
    private RadioButton singleSimRadio;

    @FXML
    private RadioButton batchSimRadio;

    @FXML
    private TextField widthInput;

    @FXML
    private TextField heightInput;

    @FXML
    private TextArea statsArea;

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

    @FXML
    private TextField numBatchesInput;

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

    @FXML
    private void setSingleSimRadio (ActionEvent event)
    {
        batchSimRadio.setSelected(false);
        runtimeBarChart.getData().clear();
        nodesBarChart.getData().clear();
        pathLenBarChart.getData().clear();
        successBarChart.getData().clear();
        seedInput.setDisable(false);
        uiSeedLabel.setDisable(false);
        numBatchesInput.setDisable(true);
        uiNumBatchesLabel.setDisable(true);
        algoInput.setDisable(false);
        statsArea.setText("");
        textArea.setText("");
    }

    @FXML
    private void setBatchSimRadio (ActionEvent event)
    {
        singleSimRadio.setSelected(false);
        runtimeBarChart.getData().clear();
        nodesBarChart.getData().clear();
        pathLenBarChart.getData().clear();
        successBarChart.getData().clear();
        seedInput.setDisable(true);
        uiSeedLabel.setDisable(true);
        numBatchesInput.setDisable(false);
        uiNumBatchesLabel.setDisable(false);
        algoInput.setDisable(true);
        statsArea.setText("");
        textArea.setText("");
    }

    @FXML
    private void astarPressed (){
        algoInput.setText("A star");
    }

    @FXML
    private void dijkstrasPressed (){
        algoInput.setText("Dijkstras");
    }

    private int simnum;

    @FXML
    private void initialize() {
        simnum = 0;
        textArea.setFont(Font.font("Consolas", FontWeight.BOLD, 14));
        // runSimButton.setOnAction(this::runSimulations);
    }

    // Method to run the Sim; used for button press functionality
    @FXML
    private void runSimulations (ActionEvent event) {
        try {
            Settings s = new Settings();
            s.width = Integer.parseInt(widthInput.getText());
            s.height = Integer.parseInt(heightInput.getText());
            s.obstacleDensity = Double.parseDouble(odInput.getText());
            s.dynamicObstacles = dynamicInput.isSelected();
            s.dynamicObstacleMoveProb = Double.parseDouble(dynamicProbInput.getText());
            s.maxTicks = Integer.parseInt(ticksInput.getText());
            // ASTAR or DIJKSTRA
            if (algoInput.getText().contains("Dijkstras")) {
                s.algo = Settings.Algorithm.DIJKSTRA;
            } else {
                s.algo = Settings.Algorithm.ASTAR;
            }

            // add check for batch or single
            if(singleSimRadio.isSelected()) {
                s.seed = Long.parseLong(seedInput.getText());
            }

            BatchSimulation batch = new BatchSimulation(s);
            if (singleSimRadio.isSelected()) {
                Simulation sim = new Simulation(s, new ConsoleRenderer(textArea));
                SimulationResult simresults;
                simresults = sim.run();
                simnum += 1;

                statsArea.appendText("Sim: " + simresults.toString() + "\n");


                if(algoInput.getText().contains("Dijkstras")) {
                    // Runtime BarChart
                    XYChart.Series<String, Number> runtimeSeries = new XYChart.Series<>();
                    runtimeSeries.setName("Runtime (ms)");
                    runtimeSeries.getData().add(new XYChart.Data("Dijkstras", simresults.totalRunTimeMillis));
                    runtimeBarChart.getData().add(runtimeSeries);

                    // Nodes BarChart
                    XYChart.Series<String, Number> NodesSeries = new XYChart.Series<>();
                    NodesSeries.setName("Nodes");
                    NodesSeries.getData().add(new XYChart.Data("Dijkstras", simresults.totalNodesExpanded));

                    nodesBarChart.getData().add(NodesSeries);

                    // Path Length BarChart
                    XYChart.Series<String, Number> PathSeries = new XYChart.Series<>();
                    PathSeries.setName("Path");
                    PathSeries.getData().add(new XYChart.Data("Dijkstras", simresults.pathLength));
                    pathLenBarChart.getData().add(PathSeries);


                    // Success BarChart
                    XYChart.Series<String, Number> SuccessSeries = new XYChart.Series<>();
                    SuccessSeries.setName("Success");
                    SuccessSeries.getData().add(new XYChart.Data("Dijkstras", simresults.isReachedGoal() ? 1 : 0));
                    successBarChart.getData().add(SuccessSeries);

                }
                else
                {
                    XYChart.Series<String, Number> runtimeSeries = new XYChart.Series<>();
                    runtimeSeries.setName("Runtime (ms)");
                    runtimeSeries.getData().add(new XYChart.Data("A Star", simresults.totalRunTimeMillis));
                    runtimeBarChart.getData().add(runtimeSeries);

                    // Nodes BarChart
                    XYChart.Series<String, Number> NodesSeries = new XYChart.Series<>();
                    NodesSeries.setName("Nodes");
                    NodesSeries.getData().add(new XYChart.Data("A Star", simresults.totalNodesExpanded));

                    nodesBarChart.getData().add(NodesSeries);

                    // Path Length BarChart
                    XYChart.Series<String, Number> PathSeries = new XYChart.Series<>();
                    PathSeries.setName("Path");
                    PathSeries.getData().add(new XYChart.Data("A Star", simresults.pathLength));
                    pathLenBarChart.getData().add(PathSeries);


                    // Success BarChart
                    XYChart.Series<String, Number> SuccessSeries = new XYChart.Series<>();
                    SuccessSeries.setName("Success");
                    SuccessSeries.getData().add(new XYChart.Data("A Star", simresults.isReachedGoal() ? 1 : 0));
                    successBarChart.getData().add(SuccessSeries);
                }
            } else {
                System.out.println("performing batch simulations");
                batch.RunSimulations(Integer.parseInt(numBatchesInput.getText()));

                // Avg. Runtime BarChart
                XYChart.Series<String, Number> runtimeSeries = new XYChart.Series<>();
                runtimeSeries.setName("Avg. Runtime (ms)");
                runtimeSeries.getData().add(new XYChart.Data("ASTAR", batch.ASTAR_Total_Time));
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
                statsArea.setText(batch.toString());
            }


        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: Please Validate Inputs");
            alert.showAndWait();
        }
    }
}

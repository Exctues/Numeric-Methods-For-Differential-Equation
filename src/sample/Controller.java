package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private LineChart<Number, Number> functChart;
    @FXML
    private LineChart<Number, Number> errorChart;
    @FXML
    private LineChart<Number, Number> globalErrorChart;
    @FXML
    private Button plotButton;
    @FXML
    private TextField x0field;
    @FXML
    private TextField y0field;
    @FXML
    private TextField n0field;
    @FXML
    private TextField globalNfield;
    @FXML
    private TextField Nfield;
    @FXML
    private TextField Xfield;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        plotButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearAllCharts();

                double x0 = Double.parseDouble(x0field.getText());
                double y0 = Double.parseDouble(y0field.getText());
                int N = Integer.parseInt(Nfield.getText());
                int X = Integer.parseInt(Xfield.getText());
                int n0 = Integer.parseInt(n0field.getText());
                int globalN = Integer.parseInt(globalNfield.getText());

                if (N <= 1 || n0 >= globalN || x0 >= X || n0 <= 0) {
                    System.err.println("WARNING: Incorrect input");
                    return;
                }

                if (x0 == 0) {
                    System.err.println("WARNING: x0 cannot be zero. x0 automatically has been set to 1");
                    x0field.setText("1");
                    x0 = 1;
                }

                Solver solver = new Solver(x0, y0, n0, globalN, N, X);
                solver.solve();

                solver.plotSolution(functChart);
                solver.plotErrors(errorChart);
                solver.plotGlobalErrors(globalErrorChart);
            }
        });

    }

    private void clearAllCharts() {
        functChart.getData().clear();
        errorChart.getData().clear();
        globalErrorChart.getData().clear();
    }
}

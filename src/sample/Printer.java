package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.math.BigDecimal;

public class Printer {

    public static void print(LineChart<Number, Number> chart, final Grid grid, String name) {
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < grid.x.length; i++) {
            BigDecimal bd = new BigDecimal(Double.toString(grid.x[i])).setScale(2,BigDecimal.ROUND_HALF_UP);
            series.getData().add(new XYChart.Data(bd.toString(), grid.y[i]));
        }
        series.setName(name);
        chart.getData().addAll(series);
    }
}

package sample;

import javafx.scene.chart.LineChart;
import java.util.Arrays;

public class Solver {
    private double x0;
    private double y0;
    private int n0;
    private int globalN;
    private int N;
    private int X;

    private Grid exactSolution;

    private Grid eulerResult;
    private Grid improvedEulerResult;
    private Grid rungeKuttaResult;

    private Grid eulerError;
    private Grid improvedEulerError;
    private Grid rungeKuttaError;

    private Grid eulerErrorGlobal;
    private Grid improvedEulerErrorGlobal;
    private Grid rungeKuttaErrorGlobal;

    public Solver(double x0, double y0, int n0, int globalN, int N, int X) {
        this.x0 = x0;
        this.y0 = y0;
        this.n0 = n0;
        this.globalN = globalN;
        this.N = N;
        this.X = X;
    }

    public void solve() {
        int n = globalN - n0;

        eulerErrorGlobal = new Grid(n);
        improvedEulerErrorGlobal = new Grid(n);
        rungeKuttaErrorGlobal = new Grid(n);

        for (int i = n0; i < globalN; i++) {
            eulerErrorGlobal.x[i - n0] = i;
            improvedEulerErrorGlobal.x[i - n0] = i;
            rungeKuttaErrorGlobal.x[i - n0] = i;
        }

        for (int i = n0; i < globalN; i++) {
            calculateSolution(i);
            calculateErrors(i);

            double maxEulerError = Arrays.stream(eulerError.y).max().getAsDouble();
            double maxImprovedEulerError = Arrays.stream(improvedEulerError.y).max().getAsDouble();
            double maxRungeKuttaError = Arrays.stream(rungeKuttaError.y).max().getAsDouble();

            eulerErrorGlobal.y[i - n0] = maxEulerError;
            improvedEulerErrorGlobal.y[i - n0] = maxImprovedEulerError;
            rungeKuttaErrorGlobal.y[i - n0] = maxRungeKuttaError;
        }

        calculateSolution(N);
        calculateErrors(N);

    }

    private void calculateSolution(int curN) {
        eulerResult = new Euler(x0, y0, curN, X).calculate();
        improvedEulerResult = new ImprovedEuler(x0, y0, curN, X).calculate();
        rungeKuttaResult = new RungeKutta(x0, y0, curN, X).calculate();
    }

    private void calculateErrors(int curN) {
        getExactSolution(curN);

        // Grid init
        eulerError = new Grid(curN);
        improvedEulerError = new Grid(curN);
        rungeKuttaError = new Grid(curN);

        eulerError.x = eulerResult.x.clone();
        improvedEulerError.x = eulerResult.x.clone();
        rungeKuttaError.x = eulerResult.x.clone();

        //Compare with exact
        for (int i = 0; i < N; i++) {
            eulerError.y[i] = Math.abs(exactSolution.y[i] - eulerResult.y[i]);
            improvedEulerError.y[i] = Math.abs(exactSolution.y[i] - improvedEulerResult.y[i]);
            rungeKuttaError.y[i] = Math.abs(exactSolution.y[i] - rungeKuttaResult.y[i]);
        }

    }

    private void getExactSolution(int curN) {
        double[] x = new double[curN];
        double[] y = new double[curN];
        x[0] = x0;
        y[0] = y0;
        double h = (X - x0) / curN;

        for (int i = 0; i < curN - 1; i++) {
            x[i + 1] = x[i] + h;
        }
        double c = (y0 - x0 * x0) / (x0 * x0 * Math.exp(1 / x0));
        for (int i = 1; i < curN; i++) {
            y[i] = x[i] * x[i] + c * x[i] * x[i] * Math.exp(1 / x[i]);
        }
        exactSolution = new Grid(x, y);
    }

    public void plotSolution(LineChart<Number, Number> chart) {
        Printer.print(chart, eulerResult, "Euler");
        Printer.print(chart, improvedEulerResult, "Improved");
        Printer.print(chart, rungeKuttaResult, "Runge-Kutta");
       //Printer.print(chart, exactSolution, "exact");
    }

    public void plotErrors(LineChart<Number, Number> chart) {
        Printer.print(chart, eulerError, "Euler");
        Printer.print(chart, improvedEulerError, "Improved");
        Printer.print(chart, rungeKuttaError, "Runge-Kutta");
    }

    public void plotGlobalErrors(LineChart<Number, Number> chart) {
        Printer.print(chart, eulerErrorGlobal, "Euler");
        Printer.print(chart, improvedEulerErrorGlobal, "Improved");
        Printer.print(chart, rungeKuttaErrorGlobal, "Runge-Kutta");
    }
}

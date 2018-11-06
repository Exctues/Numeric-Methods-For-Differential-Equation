package sample;

public class RungeKutta extends Euler {

    public RungeKutta(double x0, double y0, int N, int X) {
        super(x0, y0, N, X);
    }

    @Override
    public Grid calculate() {
        double h = (X - x0) / N;
        x = new double[N];
        y = new double[N];
        x[0] = x0;
        y[0] = y0;

        for (int i = 0; i < N - 1; i++) {
            x[i + 1] = x[i] + h;
        }

        for (int i = 0; i < N - 1; i++) {
            double k1 = f(x[i], y[i]);
            double k2 = f(x[i] + h / 2, y[i] + k1 * h / 2);
            double k3 = f(x[i] + h / 2, y[i] + k2 * h / 2);
            double k4 = f(x[i] + h, y[i] + k3 * h);
            y[i + 1] = y[i] + (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4);
        }

        return new Grid(x, y);
    }

}

//            double k1 = y[i] * (2 * x[i] - 1) / (x[i] * x[i]) + 1;
//            double k2 = (y[i] + k1 * h / 2) * (2 * (x[i] + h / 2) - 1) / ((x[i] + h / 2) * (x[i] + h / 2)) + 1;
//            double k3 = (y[i] + k2 * h / 2) * (2 * (x[i] + h / 2) - 1) / ((x[i] + h / 2) * (x[i] + h / 2)) + 1;
//            double k4 = (y[i] + k3 * h) * (2 * (x[i] + h) - 1) / ((x[i] + h) * (x[i] + h)) + 1;

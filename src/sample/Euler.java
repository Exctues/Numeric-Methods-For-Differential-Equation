package sample;

public class Euler extends Grid {
    protected double x0;
    protected double y0;
    protected int N;
    protected int X;

    public Euler(double x0, double y0, int N, int X) {
        this.x0 = x0;
        this.y0 = y0;
        this.N = N;
        this.X = X;
    }

    protected double f(double x, double y) {
        return 1 + y * (2 * x - 1) / (x * x);
    }

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
            y[i + 1] = y[i] + h * f(x[i], y[i]);
        }

        return new Grid(x, y);
    }
}

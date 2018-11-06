package sample;

public class ImprovedEuler extends Euler {

    public ImprovedEuler(double x0, double y0, int N, int X) {
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

            y[i + 1] = y[i] + (h / 2) * (f(x[i], y[i]) + f(x[i + 1], y[i] + h * f(x[i], y[i])));
        }


        return new Grid(x, y);
    }
}

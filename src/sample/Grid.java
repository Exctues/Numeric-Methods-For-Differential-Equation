package sample;

public class Grid {
    double[] x;
    double[] y;

    public Grid() {
    }
    public Grid(int length){
        x = new double[length];
        y = new double[length];
    }

    public Grid(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }
}

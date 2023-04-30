package ai.maths.mandelbrot;

import java.awt.Color;

public class MandelbrotCalculator {

    private static final int MAX_ITERATION = 36000;//32768;
    private static final double INITIAL_DELTA = 1.25;
    //private static final int MAX_ITERATION = 256*256*256;
    private int width;
    private int height;
    private double deltaProp;
    private double centerX = -0.75;
    private double centerY = 0;

    public void zoomIn(int x, int y) {
        centerX = coordOfXPixel(x);
        centerY = coordOfYPixel(y);
        deltaProp = deltaProp / 2;
    }

    public void zoomOut(int x, int y) {
        centerX = coordOfXPixel(x);
        centerY = coordOfYPixel(y);
        deltaProp = deltaProp * 2;
    }

    private double coordOfXPixel(int x) {
        return (double) (x * 2 - width) * deltaProp + centerX;
    }

    private double coordOfYPixel(int y) {
        return (double) (y * 2 - height) * deltaProp + centerY;
    }

    public int pixelColor(int x, int y) {
        return pixelColor(coordOfXPixel(x), coordOfYPixel(y));
    }

    private int pixelColor(double x, double y) {
        double a2 = 0;
        double b2 = 0;
        double a = 0;
        double b = 0;
        int iteration = 0;
        while (a2 + b2 < 4 && iteration < MAX_ITERATION) {
            b = 2 * a * b + y;
            a = a2 - b2 + x;
            a2 = a * a;
            b2 = b * b;
            iteration++;
        }
        return Color.HSBtoRGB((float) (iteration % 360) / 360, 1f, 1f - (float) iteration / 36000);
    }

    public void setWidthHeight(int width, int height) {
        if (this.width == 0) {
            this.deltaProp = INITIAL_DELTA / width;
        } else {
            this.deltaProp = deltaProp * this.width / width;
        }
        this.width = width;
        this.height = height;
    }
}

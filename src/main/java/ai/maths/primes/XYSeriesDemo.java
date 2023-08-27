package ai.maths.primes;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

class XYSeriesDemo extends ApplicationFrame {

    private final MyXYSeries series;

    XYSeriesDemo() {
        super("Sum of prime exponents.");
        series = new MyXYSeries("Sum of prime exponents.");
    }

    void postProcess() {
        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Node division",
                "X",
                "Y",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
        setContentPane(chartPanel);
    }

    void addDot(double x, double y) {
        series.add(x, y, false);
    }

    static class MyXYSeries extends XYSeries {

        MyXYSeries(Comparable key) {
            super(key, false, true);
            data = new ArrayList<>(100000000);
        }
    }
}

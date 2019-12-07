package ai.maths.collatz;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;

class XYSeriesDemo extends ApplicationFrame {

    private final MyXYSeries series;

    XYSeriesDemo() {
        super("Collatz");
        series = new MyXYSeries("Collatz");
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

    class MyXYSeries extends XYSeries {
        MyXYSeries(Comparable key) {
            super(key, false, true);
            data = new ArrayList(100000000);
        }
    }


}

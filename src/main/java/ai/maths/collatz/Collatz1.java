package ai.maths.collatz;

import java.util.function.Function;

public class Collatz1 {

    private static int steps = 2;
    private static int maxiPowIter = 24;

    public static void main(String[] args) {
        long maxIter = getEndOfSymmetry(maxiPowIter);
        System.out.println(maxIter);
        printStatsInternal(maxiPowIter);
        printStatsExternal(maxiPowIter);
        plotGraph(maxIter, aLong -> (double) aLong / getNextValue(aLong));
    }

    private static void printStatsInternal(int maxiPowIter) {
        long s = getStartOfSymmetryOfTwoAdjacentCentersOfSymmetry(maxiPowIter);
        long e = getEndOfSymmetryOfTwoAdjacentCentersOfSymmetry(maxiPowIter);
        long v = 1;
        while (s <= e) {
            long batch_index = v % 100000000000L;
            if (batch_index >= 1 && batch_index <= 100) {
                long nextS = getNextValue(s);
                long nextE = getNextValue(e);
                double difference = ((double) e / nextE) - ((double) s / nextS);
                double ratio = (((double) s / nextS) / ((double) e / nextE));
                double estimateRatio = getEstimatedRatioInternal(v);
                double error = Math.abs(ratio - estimateRatio);
                System.out.print("Values : ");
                System.out.print(s + " " + nextS + " " + e + " " + nextE);
                System.out.print(" Difference: ");
                System.out.print(difference);
                System.out.print(", Ratio: ");
                System.out.print(ratio);
                System.out.print(", Estimate Ratio: ");
                System.out.print(estimateRatio);
                System.out.print(", Error: ");
                System.out.print(error);
                System.out.print(", Error less than 1: ");
                System.out.print(error < 1);
                System.out.println();
            }
            s += steps;
            e -= steps;
            v += steps;
        }
    }

    private static void printStatsExternal(int maxiPowIter) {
        long s = 1;
        long e = getEndOfSymmetry(maxiPowIter);
        while (s <= e) {
            if (s % 100000000 >= 1 && s % 100000000 <= 100) {
                long nextS = getNextValue(s);
                long nextE = getNextValue(e);
                double difference = ((double) e / nextE) - ((double) s / nextS);
                double ratio = (((double) s / nextS) / ((double) e / nextE));
                double estimateRatio = getEstimatedRatioExternal(s);
                double error = Math.abs(ratio - estimateRatio);
                System.out.print("Values : ");
                System.out.print(s + " " + nextS + " " + e + " " + nextE);
                System.out.print(" Difference: ");
                System.out.print(difference);
                System.out.print(", Ratio: ");
                System.out.print(ratio);
                System.out.print(", Estimate Ratio: ");
                System.out.print(estimateRatio);
                System.out.print(", Error: ");
                System.out.print(error);
                System.out.print(", Error less than 1: ");
                System.out.print(error < 1);
                System.out.println();
            }
            s += steps;
            e -= steps;
        }
    }

    private static long getNextValue(long i) {
        long a = i >> Long.numberOfTrailingZeros(i); // like dividing by two until we reach an odd number
        if (a == i) {
            a = (i << 1) + i + 1;
            a = a >> Long.numberOfTrailingZeros(a); // like dividing by two until we reach an odd number
        }
        return a;
    }

    private static void plotGraph(long maxIter, Function<Long, Double> function) {
        final XYSeriesDemo demo = new XYSeriesDemo();

        for (long i = 1; i <= maxIter; i += steps) {
            demo.addDot(i, function.apply(i));
        }
        demo.postProcess();
        demo.pack();
        demo.setVisible(true);
    }

    private static long getEndOfSymmetry(long i) {
        return ((long) Math.pow(2, 2 * i + 1) - 2) / 3 - 1;
    }

    private static long getCenterOfSymmetry(long i) {
        return ((long) Math.pow(2, 2 * i) - 1) / 3;
    }

    private static long getMiddleOfTwoAdjacentCentersOfSymmetry(int i) {
        return (getCenterOfSymmetry(i) + getCenterOfSymmetry(i - 1)) / 2;
    }


    private static long getStartOfSymmetryOfTwoAdjacentCentersOfSymmetry(int i) {
        return getCenterOfSymmetry(i - 1);
    }

    private static long getEndOfSymmetryOfTwoAdjacentCentersOfSymmetry(int i) {
        return getCenterOfSymmetry(i);
    }

    private static double getEstimatedRatioExternal(long s) {
        return ((double) (3 + 3 * (s - 1)) / (4 + 3 * (s - 1)));
    }

    private static double getEstimatedRatioInternal(long s) {
        if (s == 1) {
            return (double) 1 / 4;
        }
        return 1;
    }
}


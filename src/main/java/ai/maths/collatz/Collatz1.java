package ai.maths.collatz;

import java.util.HashMap;

public class Collatz1 {

    public static void main(String[] args) {
        int maxiPowIter = 12;
        long maxIter = getEndOfSymmetry(maxiPowIter);
        System.out.println(maxIter);
        HashMap<Long, Long> graph = new HashMap<>((int) maxIter * 2);

        for (long i = 1; i <= maxIter; i += 2) {
            long a = (i << 1) + i + 1;
            a = a >> Long.numberOfTrailingZeros(a); // like dividing by two until we reach an odd number
            graph.put(i, a);

        }

        long s = 1;
        long e = getEndOfSymmetry(7);
        while (s <= e) {
            //System.out.print((((double) s / graph.get(s)) / ((double) e / graph.get(e))) + " " + ((double) (3 + 3 * (s-1)) / (4 + 3 * (s-1))) + " ");
            System.out.print((((double) s / graph.get(s)) / ((double) e / graph.get(e))) - ((double) (3 + 3 * (s - 1)) / (4 + 3 * (s - 1))));
            System.out.print(" " + (((((double) s / graph.get(s)) / ((double) e / graph.get(e))) - ((double) (3 + 3 * (s - 1)) / (4 + 3 * (s - 1)))) < 1));
            System.out.print(" ");
            s = s + 2;
            e = e - 2;
        }

        final XYSeriesDemo demo = new XYSeriesDemo("Collatz");

        for (long i = 1; i <= maxIter; i += 2) {
            //System.out.println(i + " " + (double) i / graph.get(i));
            demo.addDot(i, (double) i / graph.get(i));
        }
        demo.postProcess();
        demo.pack();
        demo.setVisible(true);
    }

    private static long getEndOfSymmetry(long i) {
        return ((long) Math.pow(2, 2 * i + 1) - 5) / 3;
    }

    private static long getCenterOfSymmetry(long i) {
        return ((long) Math.pow(2, 2 * i) - 1) / 3;
    }

    private static long getMiddleOfTwoAdjacentCentersOfSymmetry(int i) {
        return (long) (5 * Math.pow(2, 2 * i) - 2) / 6;

    }

}


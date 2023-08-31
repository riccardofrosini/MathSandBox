package ai.maths.euler.p1to20;

public class Euler14 {

    public static void main(String[] args) {
        int maxChain = 1;
        int startingNumber = 1;
        for (int i = 1; i < 1000000; i++) {
            int newChainForI = collatzIterations(i);
            if (maxChain < newChainForI) {
                maxChain = newChainForI;
                startingNumber = i;
            }
        }
        System.out.println(startingNumber);
    }

    private static int collatzIterations(long start) {
        int iter = 1;
        while (start != 1) {
            if (start % 2 == 0) {
                int trailingZeros = Long.numberOfTrailingZeros(start);
                iter += trailingZeros;
                start = start >> trailingZeros;
            } else {
                start = start * 3 + 1;
                iter++;
            }
        }
        return iter;
    }
}

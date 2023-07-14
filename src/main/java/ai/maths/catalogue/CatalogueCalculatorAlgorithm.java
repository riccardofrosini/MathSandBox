package ai.maths.catalogue;

import java.util.HashMap;

public class CatalogueCalculatorAlgorithm {

    public static void main(String[] args) {
        long max = 100000000;
        HashMap<Long, Long> catalogueCounter = new HashMap<>();
        for (long i = 0; i < max; i++) {
            long count = catalogueCounter.computeIfAbsent(i, val -> 0L);
            System.out.print(count + " ");
            catalogueCounter.merge(count, 1L, Long::sum);
            if (count == 0) {
                System.out.println();
                i = -1;
            }
        }
    }
}

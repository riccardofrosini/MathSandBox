package ai.maths.euler.p21to40;

import ai.maths.Utils;

public class Euler38 {


    public static void main(String[] args) {
        long maxPandigital = 0;
        for (long i = 2; i < 10000; i++) {
            long result = 0;
            for (long j = 1; result < 1000000000; j++) {
                String pandigitalCandidate = result + "" + (i * j);
                result = Long.parseLong(pandigitalCandidate);

                if (Utils.isPandigital(pandigitalCandidate) && maxPandigital < result) {
                    maxPandigital = result;
                }
            }
        }
        System.out.println(maxPandigital);
    }
}

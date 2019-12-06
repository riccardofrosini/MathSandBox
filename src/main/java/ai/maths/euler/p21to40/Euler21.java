package ai.maths.euler.p21to40;

import ai.maths.Utils;

public class Euler21 {
    public static void main(String[] args) {
        long sumOfAmicableNumbers = 0;
        for (int i = 2; i < 10000; i++) {
            long sumOfFactors = Utils.sumOfFactors(i);
            if (sumOfFactors > i && Utils.sumOfFactors(sumOfFactors) == i && sumOfFactors < 10000) {
                sumOfAmicableNumbers = sumOfAmicableNumbers + i + sumOfFactors;

            }
        }
        System.out.println(sumOfAmicableNumbers);
    }
}

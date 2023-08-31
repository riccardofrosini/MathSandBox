package ai.maths.euler.p1to20;

import ai.maths.euler.Utils;

public class Euler7 {

    public static void main(String[] args) {
        System.out.println(nthPrime(10001));
    }

    private static int nthPrime(int n) {
        if (n == 1) {
            return 2;
        }
        if (n == 2) {
            return 3;
        }
        n = n - 2;
        for (int i = 1; ; i++) {
            int nthPrime = i * 6 - 1;
            if (Utils.isPrime(nthPrime)) {
                n--;
                if (n == 0) {
                    return nthPrime;
                }
            }
            nthPrime = i * 6 + 1;
            if (Utils.isPrime(nthPrime)) {
                n--;
                if (n == 0) {
                    return nthPrime;
                }
            }

        }
    }

}

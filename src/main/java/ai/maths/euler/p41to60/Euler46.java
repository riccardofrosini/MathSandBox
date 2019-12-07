package ai.maths.euler.p41to60;

import ai.maths.Utils;

import java.util.LinkedList;

public class Euler46 {

    private static final LinkedList<Long> primes = new LinkedList<>();

    public static void main(String[] args) {
        System.out.println(smallestNonGoldbach());
    }

    private static long smallestNonGoldbach() {
        for (long i = 3; ; i = i + 2) {
            if (!Utils.isPrime(i)) {
                if (!isGoldbach(i)) {
                    return i;
                }
            } else {
                primes.addFirst(i);// so we scan from largest to smallest
            }
        }
    }

    private static boolean isGoldbach(long i) {
        for (long p : primes) {
            if (Utils.isPerfectSquare((i - p) / 2)) {
                return true;
            }
        }
        return false;
    }
}

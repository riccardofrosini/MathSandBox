package ai.maths.euler.p21to40;

import ai.maths.Utils;

public class Euler35 {

    public static void main(String[] args) {
        int count = 0;
        for (int i = 2; i < 1000000; i++) {
            String candidate = String.valueOf(i);
            if (isRotatingPrime(candidate)) {
                count++;
            }
        }
        System.out.println(count);
    }

    private static boolean isRotatingPrime(String candidate) {
        for (int j = 0; j < candidate.length(); j++) {
            candidate = candidate.substring(1) + candidate.substring(0, 1);
            if (!Utils.isPrime(Integer.parseInt(candidate))) {
                return false;
            }
        }
        return true;
    }
}

package ai.maths.euler.p1to20;

import ai.maths.Utils;

public class Euler10 {

    public static void main(String[] args) {
        long max = 2000000;
        long sum = 2;
        sum += 3;
        for (int i = 1; i * 6 - 1 < max; i++) {
            int candidatePrime = i * 6 - 1;
            if (Utils.isPrime(candidatePrime)) {
                sum += candidatePrime;
            }
            candidatePrime = i * 6 + 1;
            if (candidatePrime < max && Utils.isPrime(candidatePrime)) {
                sum += candidatePrime;
            }
        }
        System.out.println(sum);
    }
}

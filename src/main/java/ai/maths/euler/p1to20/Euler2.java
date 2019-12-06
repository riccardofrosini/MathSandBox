package ai.maths.euler.p1to20;

import ai.maths.Utils;

public class Euler2 {
    public static void main(String[] args) {
        int max = 4000000;
        // nth fibonacci number round((PHI^n)/sqrt(5) = Fn
        // find n such that (PHI^n)/sqrt(5)<max
        // n < log(max * sqrt(5))/log(PHI)
        long n = (int) Math.floor(Math.log(max * Utils.SQRT5) / Math.log(Utils.PHI));
        // sum of all fibonacci up to Fn = F(n+2) - 1
        // sum of all even fibonacci up to Fn = (F(n+2) - 1)/2 when Fn is even
        // Fn = round((PHI^n)/ SQRT5) which is even and therefore
        if (Math.round(Math.pow(Utils.PHI, n) / Utils.SQRT5) % 2 == 1) n--;
        if (Math.round(Math.pow(Utils.PHI, n) / Utils.SQRT5) % 2 == 1) n--;
        System.out.println((Math.round(Math.pow(Utils.PHI, n + 2) / Utils.SQRT5) - 1) / 2);
    }
}
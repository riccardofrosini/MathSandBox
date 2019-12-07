package ai.maths.euler.p41to60;

import java.math.BigInteger;

public class Euler48 {

    private static final BigInteger bi10000000000 = new BigInteger("10000000000");

    public static void main(String[] args) {
        BigInteger sumOfxTox = BigInteger.ZERO;
        for (long i = 1; i <= 1000; i++) {
            sumOfxTox = (sumOfxTox.add(powMod10000000000(i, i))).mod(bi10000000000);
        }
        System.out.println(sumOfxTox);
    }


    private static BigInteger powMod10000000000(long x, long p) {
        BigInteger biX = BigInteger.valueOf(x);
        BigInteger solution = BigInteger.ONE;
        String s = Long.toBinaryString(p);
        for (byte aByte : s.getBytes()) {
            if (aByte == '1') solution = solution.multiply(solution).multiply(biX).mod(bi10000000000);
            if (aByte == '0') solution = solution.multiply(solution).mod(bi10000000000);
            if (solution.equals(BigInteger.ZERO)) return BigInteger.ZERO;
        }
        return solution;
    }
}

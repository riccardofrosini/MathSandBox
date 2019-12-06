package ai.maths.euler.p1to20;

import ai.maths.Utils;

import java.math.BigInteger;

public class Euler16 {
    public static void main(String[] args) {
        BigInteger n = BigInteger.ONE;

        n = n.shiftLeft(1000);
        long sum = Utils.getSumOfDigits(n);
        System.out.println(sum);
    }

}

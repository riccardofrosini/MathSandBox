package ai.maths.euler.p21to40;

import ai.maths.euler.Utils;

public class Euler25 {
    public static void main(String[] args) {
        long n = (long) Math.ceil((Math.log10(Utils.SQRT5) + 999) / Math.log10(Utils.PHI));
        System.out.println(n);
    }
}

package ai.maths.euler.p21to40;

import ai.maths.euler.Utils;

public class Euler37 {


    private static final int[] magicNumbersLeft = {2, 3, 5, 7};
    private static final int[] magicNumbersRight = {1, 3, 7, 9};

    public static void main(String[] args) {
        long total = 0;
        for (int i : magicNumbersLeft) {
            total += searchTruncateablePrimesRLAndSum(i);
        }
        System.out.println(total);
    }

    private static long searchTruncateablePrimesRLAndSum(long val) {
        long sumOfTruncatable;
        if (isTruncateablePrimeLR(val)) {
            sumOfTruncatable = val;
        } else {
            sumOfTruncatable = 0;
        }
        for (int d : magicNumbersRight) {
            long newVal = val * 10 + d;
            if (Utils.isPrime(newVal)) {
                sumOfTruncatable += searchTruncateablePrimesRLAndSum(newVal);
            }
        }

        return sumOfTruncatable;
    }

    private static boolean isTruncateablePrimeLR(long val) {
        if (val < 10) return false;
        String s = Long.toString(val);
        for (int i = 1; i < s.length(); i++) {
            if (!Utils.isPrime(Long.parseLong(s.substring(i)))) {
                return false;
            }
        }
        return true;
    }
}

package ai.maths.euler.p41to60;

import ai.maths.euler.Utils;

public class Euler45 {

    public static void main(String[] args) {
        System.out.println(findTriPentHex());
    }

    private static long findTriPentHex() {
        for (long i = 286; ; i++) {
            long x = (i * (i + 1)) / 2;
            long detP = 1 + 24 * x;
            long detH = 1 + 8 * x;
            if (Utils.isPerfectSquare(detP) &&
                    Utils.isPerfectSquare(detH) &&
                    (Math.sqrt(detP) + 1) % 6 == 0 &&
                    (Math.sqrt(detH) + 1) % 4 == 0) {
                return x;
            }

        }
    }
}

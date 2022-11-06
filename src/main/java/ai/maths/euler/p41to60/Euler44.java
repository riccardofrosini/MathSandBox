package ai.maths.euler.p41to60;

import ai.maths.euler.Utils;

public class Euler44 {

    public static void main(String[] args) {
        System.out.println(minimumPentagonalNumber());
    }

    private static long minimumPentagonalNumber() {
        for (long k = 1; ; k++) {
            for (long j = 1; j < k; j++) {
                long Pk = (k * (3 * k - 1)) / 2;
                long Pj = (j * (3 * j - 1)) / 2;
                if ((Pj + Pk) % 2 == 0) {
                    long PnAlpha = (Pk + Pj);
                    long dnAlpha = 1 + 12 * PnAlpha;
                    long Pn = (Pk - Pj);
                    long dn = 1 + 12 * Pn;
                    if (Utils.isPerfectSquare(dnAlpha) && Utils.isPerfectSquare(dn)) {
                        double nAlphaTimes6 = Math.sqrt(dnAlpha) + 1;
                        double nTimes6 = Math.sqrt(dn) + 1;
                        if (nAlphaTimes6 % 6 == 0 && nTimes6 % 6 == 0) {
                            return Pj;
                        }
                    }
                }
            }
        }
    }

}
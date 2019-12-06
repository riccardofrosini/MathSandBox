package ai.maths.euler.p21to40;

import ai.maths.Utils;

public class Euler39 {

    public static void main(String[] args) {
        int perimiter = 2;
        int countSolutions = 0;
        for (int i = 2; i < 1000; i += 2) {
            int prod = i / 2;
            int countSol = 0;
            for (int mk = 1; mk <= prod; mk++)
                if (prod % mk == 0) {
                    int nPm = prod / mk;
                    for (int k = 1; k <= mk; k++)
                        if (mk % k == 0) {
                            int m = mk / k;
                            int n = nPm - m;
                            int a = m * m - n * n;
                            if (n > 0 && (n % 2 == 0 || m % 2 == 0) && Utils.gcd(n, m) == 1 && a > 0) {
                                countSol++;
                            }
                        }
                }
            if (countSol > countSolutions) {
                perimiter = i;
                countSolutions = countSol;
            }
        }
        System.out.println(perimiter);

    }
}

package ai.maths.euler.p21to40;

import ai.maths.euler.Utils;

public class Euler33 {

    public static void main(String[] args) {
        int numerator = 1;
        int denominator = 1;
        for (int a = 1; a < 10; a++) {
            for (int b = 1; b < 10; b++) {
                if (a != b && ((9 * a * b) % (10 * a - b) == 0)) {
                    int x = (9 * a * b) / (10 * a - b);
                    if (x < 10 && x > 0) {
                        numerator = numerator * (10 * a + x);
                        denominator = denominator * (10 * x + b);
                        System.out.println((10 * a + x) + "/" + (10 * x + b));
                    }
                }
            }
        }

        long gcd = denominator / Utils.gcd(numerator, denominator);
        System.out.println(gcd);
    }
}

package ai.maths.euler.p1to20;

public class Euler9 {
    public static void main(String[] args) {
        System.out.println(findProductABC());
    }

    private static long findProductABC() {
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 6; j++) {
                long thousandMinusB = (long) (Math.pow(2, i) * Math.pow(5, j));
                if (500 < thousandMinusB /* so A is positive */ && 1000 > thousandMinusB /* so B is positive */) {
                    long a = 1000 - 500000 / thousandMinusB;
                    long b = 1000 - thousandMinusB;
                    long c = 1000 - a - b;
                    return a * b * c;
                }
            }
        }
        return 0;
    }
}

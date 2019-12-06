package ai.maths.euler.p1to20;

public class Euler12 {

    public static void main(String[] args) {
        System.out.println(triangleNumberWithMostDivisors(500));
    }

    private static int triangleNumberWithMostDivisors(int numOfDivisors) {
        for (int i = 1; ; i++) {
            int a = i;
            int b = i + 1;
            if (a % 2 == 0) { // either a or b has to be even
                a = a / 2;
            } else {
                b = b / 2;
            }
            int triangleNumber = a * b;
            int numOfDivisorsOfAB = 1;
            for (int j = 2; a != 1 || b != 1; j++) {
                int pow = 1;
                while (a % j == 0) {
                    a = a / j;
                    pow++;
                }
                while (b % j == 0) {
                    b = b / j;
                    pow++;
                }
                numOfDivisorsOfAB *= pow;
            }
            if (numOfDivisorsOfAB > numOfDivisors) {
                return triangleNumber;
            }
        }

    }
}
